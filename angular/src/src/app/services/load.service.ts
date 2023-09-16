import { AfterViewInit, Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, Observable, Subject, Subscription, takeUntil} from 'rxjs';
import {Certificate} from '../model/Certificate';
import {Tag} from "../model/Tag";
import {ICategory} from "../interfaces/ICategory";
import {LocalStorageService} from "./local-storage.service";

@Injectable({
  providedIn: 'root',
})
export class LoadService implements AfterViewInit {

  private apiUrl: string = 'https://gift-store.onrender.com/api';
  loading: boolean = false;
  subscription!: Subscription;
  unSubscribers$: Subject<any> = new Subject();

  constructor(
    private storage: LocalStorageService,
    private http: HttpClient) {
  }

  ngAfterViewInit(): void {
    console.log("After View Init")
    this.loadTags();
  }

  getCertificates(page: number, size: number): Observable<Certificate[]> {
    return this.http
    .get(`${this.apiUrl}/certificates?page=${page}&size=${size}`)
    .pipe(
      map((data: any) => data._embedded.certificateDtoList.map(this.mapper))
    );
  }

  getTags(): Observable<string[]> {
    return this.http.get<string[]>(this.apiUrl + '/tags').pipe(
      map((data: any) => data._embedded.tagDtoList.map(this.tagMapper)));
  }

  private mapper(data: any): Certificate {
    return {
      id: data.id,
      name: data.name,
      description: data.description,
      shortDescription: data.shortDescription,
      company: data.company,
      price: data.price,
      duration: data.duration,
      createDate: new Date(data.createDate),
      lastUpdate: new Date(data.lastUpdateDate),
      favorite: false,
      checkout: false,
      path: data.path,
      tags: data.tags.map((tag: any): Tag => ({id: tag.id, name: tag.name})),
    };
  }

  private tagMapper(data: any): ICategory {
    return {
      name: data.name,
      tag: data.name,
      url: `https://source.unsplash.com/featured/200x150/?${data.name}`,
    };
  }


  loadTags(): void {
    if (!this.loading) {
      this.loading = true;
      this.subscription = this.getTags()
      .pipe(takeUntil(this.unSubscribers$))
      .subscribe({
        next: (tags: any): void => {
          if (Array.isArray(tags)) {
            this.storage.saveTagsToLocalStorage(tags);
            this.loading = false;
          }
        },
        error: (error) => {
          console.error('Error loading certificates:', error);
          this.loading = false;
        },
        complete: () => {
          console.log('Certificates loading completed.');
        },
      });
    }
  }
}

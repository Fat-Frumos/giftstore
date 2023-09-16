import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ICategory} from '../../../interfaces/ICategory';
import {CertificateService} from "../../../services/certificate.service";
import {LocalStorageService} from "../../../services/local-storage.service";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class CategoryComponent implements OnInit {

  private intervalId: number = 0;
  public autoplay = 'on';
  public categories: ICategory[] = [];
  public ms: number = 5000;
  tags$: ICategory[] = [];
  index: number = 0;
  size: number = 5;
  private imageCache: { [key: string]: string } = {};

  constructor(
    private storage: LocalStorageService,
    private service: CertificateService,
  ) {
  }

  ngOnInit(): void {
    this.categories = this.storage.getTagsFromLocalStorage();
    this.tags$ = this.categories.slice(0, this.size);
    this.preloadImages();
    this.initInterval();
  }

  search(name: string) {
    this.service.criteria.tag = name;
    this.service.filter();
  }

  public next(): void {
    this.resetInterval();
    this.index++;
    if (this.index > this.categories.length - this.size) {
      this.index = 0;
    }
    this.tags$ = this.categories.slice(this.index, this.size + this.index);
  }

  public prev(): void {
    this.resetInterval();
    this.tags$.pop();
    if (this.index > 0) {
      this.tags$.unshift(this.categories[this.index - 1]);
      this.index--;
    } else {
      this.index = this.categories.length - 1;
      this.tags$.unshift(this.categories[this.index]);
    }
  }

  private preloadImages(): void {
    this.categories.forEach((category) => {
      if (!this.imageCache[category.url]) {
        const img = new Image();
        img.src = category.url;
        img.onload = () => {
          this.imageCache[category.url] = category.url;
        };
        img.onerror = () => {
          console.error(`Error loading image: ${category.url}`);
        };
      }
    });
  }


  private initInterval(): this {
    this.intervalId = setInterval(() => {
      this.next();
    }, this.ms);
    return this;
  }

  private resetInterval(): this {
    if (this.autoplay === 'on') {
      this.clearInterval().initInterval();
    }
    return this;
  }

  private clearInterval(): this {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
    return this;
  }
}

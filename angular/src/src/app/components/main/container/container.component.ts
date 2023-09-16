import {
  AfterViewInit, ChangeDetectorRef,
  Component,
  HostListener, OnDestroy,
  OnInit,
  ViewEncapsulation,
} from '@angular/core';
import {Certificate} from '../../../model/Certificate';
import {Subject, Subscription, takeUntil} from 'rxjs';
import {LocalStorageService} from '../../../services/local-storage.service';
import {ScrollService} from '../../../services/scroll.service';
import {LoadService} from '../../../services/load.service';
import {CertificateService} from "../../../services/certificate.service";
import {ICriteria} from "../../../interfaces/ICriteria";

@Component({
  selector: 'app-container',
  templateUrl: './container.component.html',
  styleUrls: ['./container.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ContainerComponent implements OnInit, OnDestroy, AfterViewInit {
  page: number = 0;
  size: number = 25;
  criteria: ICriteria = {name: '', tag: ''};
  loading: boolean = false;
  subscription!: Subscription;
  unSubscribers$: Subject<any> = new Subject();

  constructor(
    private scroll: ScrollService,
    private storage: LocalStorageService,
    private loadService: LoadService,
    public service: CertificateService,
    private cdr: ChangeDetectorRef
  ) {
  }

  ngAfterViewInit(): void {
    console.log("After View Init")
    this.service.updateLoginLink();
    this.cdr.detectChanges();
  }

  ngOnInit(): void {
    const saved: Certificate[] = this.storage.getCertificatesFromLocalStorage();
    if (saved.length !== 0) {
      this.service.certificates$ = saved;
    } else {
      this.loadMoreCertificates();
    }
    console.log("saved in Storage: " + this.service.certificates$.length);
  }

  ngOnDestroy(): void {
    this.unSubscribers$.next(null);
    this.unSubscribers$.complete();
    this.scroll.saveScrollPosition();
  }

  @HostListener('window:scroll', ['$event'])
  onScroll(): void {
    if (
      !this.loading &&
      window.innerHeight + window.scrollY >= document.body.offsetHeight - 80
    ) {
      this.loadMoreCertificates();
    }
  }

  loadMoreCertificates(): void {
    const spinner = document.getElementById('loading-indicator');
    if (!this.loading && spinner) {
      spinner.style.display = 'block';
      this.loading = true;
      const size: number = this.storage.getCertificatesSize();
      console.log("Storage: " + size);
      if (size !== 0) {
        this.page = size / 25;
      }
      this.subscription = this.loadService
      .getCertificates(this.page, this.size)
      .pipe(takeUntil(this.unSubscribers$))
      .subscribe({
        next: (certificates: any): void => {
          if (Array.isArray(certificates)) {
            this.storage.saveCertificatesToLocalStorage(certificates);
            this.service.certificates$ = this.storage.getCertificatesFromLocalStorage();
            this.loading = false;
            this.cdr.detectChanges();
          }
          console.log("Saved: " + this.service.certificates$.length);
          spinner.style.display = 'none';
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

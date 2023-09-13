import {
  AfterViewInit,
  Component, ElementRef,
  HostListener,
  OnDestroy,
  OnInit,
  Renderer2, ViewChild,
  ViewEncapsulation,
} from '@angular/core';
import { Certificate } from '../../../model/Certificate';
import { Subject, Subscription, takeUntil } from 'rxjs';
import { LocalStorageService } from '../../../services/local-storage.service';
import { ScrollService } from '../../../services/scroll.service';
import { CertificateService } from '../../../services/certificate.service';
import { LoadService } from '../../../services/load.service';

@Component({
  selector: 'app-container',
  templateUrl: './container.component.html',
  styleUrls: ['./container.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ContainerComponent implements OnInit, OnDestroy, AfterViewInit {
  page: number = 0;
  size: number = 25;
  loading: boolean = false;
  certificates$: Certificate[] = [];
  subscription!: Subscription;
  private unSubscribers$:Subject<any> = new Subject();

  @ViewChild('certificatesList', { static: false })
  certificatesList!: ElementRef;

  constructor(
    private service: CertificateService,
    private scroll: ScrollService,
    private storage: LocalStorageService,
    private loadService: LoadService,
    private renderer: Renderer2,
  ) {}

  ngAfterViewInit(): void {
    this.createCards();
  }

  createCards(): void {
    if (this.certificatesList) {
      this.certificates$.forEach((certificate) => {
        const card = this.renderer.createElement('app-card');
        this.renderer.addClass(card, 'certificate-card');
        this.renderer.setAttribute(
          card,
          'tagName',
          JSON.stringify(certificate.tags)
        );
        this.renderer.setAttribute(
          card,
          'certificate',
          JSON.stringify(certificate)
        );
        this.renderer.appendChild(this.certificatesList.nativeElement, card);
      });
    }
  }

  ngOnInit(): void {
    this.loadCertificates();
    this.scroll.restorePosition();
    this.service.updateLoginLink();
    this.service.counter();
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
      window.innerHeight + window.scrollY >= document.body.offsetHeight - 100
    ) {
      this.loadCertificates();
    }
  }

  private loadCertificates(): void {
    if (this.loading) return;
    this.loading = true;

    const saved: Certificate[] =
      this.storage.getCertificatesFromLocalStorage() || [];
    this.page = saved.length / 25;

    this.subscription = this.loadService
      .getCertificates(this.page, this.size)
      .pipe(takeUntil(this.unSubscribers$))
      .subscribe({
        next: (certificates: any): void => {
          if (Array.isArray(certificates)) {
            this.certificates$ = [...this.certificates$, ...certificates];
            this.page++;
            this.loading = false;
            this.scroll.saveScrollPosition();
          }
          console.log(this.certificates$.length);
          console.log(certificates.length);
        },
        error: (error) => {
          console.error('Error loading certificates:', error);
          this.loading = false;
        },
        complete: () => {
          console.log('Certificates loading completed.');
        },
      });

    if (JSON.stringify(this.certificates$).length !== 0) {
      this.storage.saveCertificatesToLocalStorage(this.certificates$);
      this.loadNextPage();
    }
    this.page++;
    this.loading = false;
  }

  private loadNextPage() {
    const saved: Certificate[] =
      this.storage.getCertificatesFromLocalStorage() || [];
    if (saved.length !== 0) {
      this.certificates$ = saved;
      this.createCards();
      // spinner.style.display = "none";
    } else {
      this.loadCertificates();
    }

    const certificatesList =
      document.getElementsByClassName('certificate-card');
    const savedScrollPosition: string =
      localStorage.getItem('scrollPosition') ?? '';
    const timer = parseInt(savedScrollPosition) / 80;
    console.log(saved.length);
    const intervalId = setInterval(() => {
      if (certificatesList.length === saved.length) {
        clearInterval(intervalId);
        this.scroll.restorePosition();
        console.log(certificatesList.length);
        console.log(timer);
        // spinner.style.display = "none";
      }
    }, timer);
  }
}

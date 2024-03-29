import {Injectable, Input, OnDestroy} from '@angular/core';
import {ICriteria} from "../interfaces/ICriteria";
import {ICertificate} from "../model/entity/ICertificate";
import {LocalStorageService} from "./local-storage.service";
import {FilterPipe} from "../pipe/filter.pipe";
import {Observable, of, Subject, Subscription, takeUntil} from "rxjs";
import {LoadService} from "./load.service";
import {IUser} from "../model/entity/IUser";
import {FormGroup,} from "@angular/forms";
import {SpinnerService} from "../components/spinner/spinner.service";
import {ITag} from "../model/entity/ITag";
import {Store} from "@ngrx/store";
import {IState} from "../store/reducers";

@Injectable()
export class CertificateService implements OnDestroy {

  @Input()
  criteria: ICriteria;
  loading: boolean = false;
  certificates$!: ICertificate[];
  subscription!: Subscription;
  unSubscribers$: Subject<any> = new Subject();

  constructor(
    public store: Store<IState>,
    private filterPipe: FilterPipe,
    public readonly load: LoadService,
    private storage: LocalStorageService) {
    this.criteria = {name: '', tag: ''} as ICriteria;
    this.certificates$ = this.storage.getCertificates();
  }

  ngOnDestroy(): void {
    this.unSubscribers$.next(null);
    this.unSubscribers$.complete();
  }

  public filter(tag: string): void {
    this.criteria.tag = tag;
    this.certificates$ = this.filterPipe.transform(
      this.storage.getCertificates(), this.criteria);
    console.log(this.certificates$)
  }

  loadMoreCertificates(page: number): number {
    let p = page;
    if (!this.loading) {
      SpinnerService.toggle()
      this.loading = true;
      const len: number = this.storage.getCertificatesSize();
      if (len !== 0) {
        p = len / 25;
      }
      this.subscription = this.load
      .getCertificates(p)
      .pipe(takeUntil(this.unSubscribers$))
      .subscribe({
        next: (certificates: any): void => {
          if (Array.isArray(certificates)) {
            this.storage.saveCertificates(certificates);
            this.certificates$ = this.storage.getCertificates();
            this.loading = false;
          }
          console.log("Saved: " + certificates.length);
        },
        error: (error) => {
          console.error('Error loading certificates:', error);
          this.loading = false;
        },
        complete: () => {
          console.log(`Certificates loading completed. Total: ${this.certificates$.length}`);
          p++;
        },
      });
    }
    return p;
  }

  findByTagName(name: string) {
    this.load.getCertificatesByTags(100, name)
    .pipe(takeUntil(this.unSubscribers$))
    .subscribe({
      next: (certificates: any): void => {
        if (Array.isArray(certificates)) {
          this.storage.saveCertificates(certificates);
          this.certificates$ = certificates;
          console.log('Certificates loading completed.' + name);
        }
      }
    })
    this.filter(name);
  }

  sendOrders(total: number) {
    const user: IUser = this.storage.getUser();
    user.certificates = this.storage.getCheckoutCertificates();
    SpinnerService.toggle();
    this.load.saveOrders(total, user, (statusCode: number) => {
      if (statusCode === 201) {
        this.load.showByText(20103, user.username);
      } else {
        this.load.showByText(statusCode, `Failed to send orders by ${user.username}`);
      }
    });
  }

  async saveCertificate(form: FormGroup) {
    if (form.value) {
      this.load.saveImage(form.get('file')); //TODO image valid + path name
      this.load.saveForm(form)
      .then((code: number) => {
        this.load.showByStatus(code);
      }).catch((error) => {
        console.log(error)
        this.load.showByStatus(error.status);
      })
    } else {
      console.log("not valid")
      this.load.showByStatus(40002);
    }
  }

  goBack() {
    this.load.back();
  }

  getCertificates(): Observable<ICertificate[]> {
    if (this.certificates$.length == 0) {
      this.loadMoreCertificates(0);
    }
    return of(this.certificates$);
  }

  addCart(certificate: ICertificate): void {
    certificate.checkout = !certificate.checkout;
    certificate.count = 1;
    this.storage.updateCertificate(certificate);
    const message =
      certificate.checkout
        ? ("add to Cart")
        : ("remove from Cart");
    console.log(message);
  }

  getById(id: string): ICertificate {
    return this.storage.getCertificateById(id);
  }

  countCertificatesByTag(name: string): number {
    return [...this.storage.getCertificates()]
    .filter((certificate: ICertificate) => [...certificate.tags]
    .some((tag: ITag) => tag.name === name)).length;
  }

  public trackByFn(_index: number, item: ICertificate): string {
    return item.id;
  }

  getCheckoutCertificates(): ICertificate[] {
    return this.storage.getCertificates()
    .filter(certificate => certificate.checkout);
  }

  updateCard(certificate: ICertificate) {
    this.storage.updateCertificate(certificate);
  }
}

import {Injectable, Input} from '@angular/core';
import {ICriteria} from "../interfaces/ICriteria";
import {Certificate} from "../model/Certificate";
import {LocalStorageService} from "./local-storage.service";
import {FilterPipe} from "../pipe/filter.pipe";

@Injectable({
  providedIn: 'root',
})
export class CertificateService {

  @Input() criteria: ICriteria;
  certificates$: Certificate[] = [];

  constructor(
    private filterPipe: FilterPipe,
    private storage: LocalStorageService) {
    this.criteria = {name: '', tag: ''} as ICriteria;
  }

  public filter(): void {
    this.certificates$ = this.filterPipe.transform(
      this.storage.getCertificatesFromLocalStorage(), this.criteria);
    console.log(this.certificates$)
  }

  goBack() {
    window.history.back();
  }

  public updateLoginLink() {
    const loginLink = document
    .getElementById('login-link');
    if (loginLink) {
      loginLink.textContent =
        localStorage.getItem('userLoggedIn') ===
        'true' ? 'Logout' : 'Login';
    }
  }
}

import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {IUser} from "../model/entity/IUser";
import {LocalStorageService} from "./local-storage.service";
import {LoginState} from "../model/enum/LoginState";
import {ICertificate} from "../model/entity/ICertificate";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private currentUser: BehaviorSubject<IUser | null>;
  loginState: BehaviorSubject<LoginState>;

  constructor(
    private readonly storage: LocalStorageService
  ) {
    this.loginState = new BehaviorSubject<LoginState>(LoginState.LOGGED_OUT);
    this.currentUser = new BehaviorSubject<IUser | null>(null);
    let user: IUser = this.getUser();
    if (user) {
      this.loginState.next(user.state);
    }
  }

  public getUser(): IUser {
    return this.storage.getUser();
  }

  isLoggedIn() {
    return this.storage.getUser().state === LoginState.LOGGED_IN;
  }

  getUsername() {
    return this.storage.getUsername();
  }

  public setUser(user: IUser): void {
    if (user) {
      this.login(user);
    } else {
      this.logout(user);
    }
  }

  private login(user: IUser): void {
    this.currentUser.next(user);
    this.storage.saveUser(user)
    this.loginState.next(user.state);
  }

  public logout(user: IUser): void {
    this.currentUser.next(null);
    user.state = LoginState.LOGGED_OUT;
    this.loginState.next(user.state);
    this.storage.saveUser(user)
  }

  saveUser(user: IUser): void {
    this.storage.saveUser(user);
  }

  saveCertificates(certificates: ICertificate[]): void {
    certificates.forEach((cert: ICertificate) =>
      this.storage.updateCertificate(cert));
  }
}

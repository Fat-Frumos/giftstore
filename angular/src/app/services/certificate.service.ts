import { Injectable, OnInit } from '@angular/core';
import { FavoriteService } from './favorite.service';

@Injectable({
  providedIn: 'root',
})
export class CertificateService implements OnInit {
  constructor(private favorite: FavoriteService) {}

  ngOnInit(): void {
    this.favorite.updateFavoriteIcons();
  }

  goBack() {
    window.history.back();
  }

  counter() {
    this.favorite.counter();
  }

  public updateLoginLink() {
    const loginLink = document.getElementById('login-link');
    if (loginLink) {
      loginLink.textContent =
        localStorage.getItem('userLoggedIn') === 'true' ? 'Logout' : 'Login';
    }
  }

  updateFavoriteIcons() {
    this.favorite.updateFavoriteIcons();
  }
}

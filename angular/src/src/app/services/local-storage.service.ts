import {EventEmitter, Injectable} from '@angular/core';
import {Certificate} from '../model/Certificate';
import {ICategory} from "../interfaces/ICategory";

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {

  cardCounterChanged = new EventEmitter<number>();
  favoriteCounterChanged = new EventEmitter<number>();

  public getFromLocalStorage(key: string): any[] {
    const favoritesJSON = localStorage.getItem(key);
    return favoritesJSON ? JSON.parse(favoritesJSON) : [];
  }

  public saveCertificatesToLocalStorage(certificates: Certificate[]): void {
    const saved = this.getCertificatesFromLocalStorage() || [];
    const unique = this.removeDuplicate(saved, certificates);
    const ids: string[] = certificates.map((certificate) => certificate.id);
    console.log(ids);
    this.sortCertificatesByCreationDate(unique);
    localStorage.setItem('certificates', JSON.stringify(unique));
  }

  private removeDuplicate(saved: Certificate[], loaded: Certificate[]): Certificate[] {
    return [...saved, ...loaded].filter(
      (a: Certificate, index: number, items: Certificate[]): boolean =>
        index === items.findIndex((c: Certificate): boolean => c.id === a.id)
    );
  }

  public getCertificatesFromLocalStorage(): Certificate[] {
    const savedCertificates = localStorage.getItem('certificates');
    return savedCertificates ? JSON.parse(savedCertificates) : [];
  }

  private sortCertificatesByCreationDate(certificates: Certificate[]): void {
    certificates.sort((a: Certificate, b: Certificate) =>
      new Date(b.createDate).getTime() - new Date(a.createDate).getTime()
    );
  }

  public getCertificatesSize(): number {
    return this.getCertificatesFromLocalStorage().length;
  }

  public updateCertificateInLocalStorage(updated: Certificate): void {
    const saved = this.getCertificatesFromLocalStorage();
    const index = saved.findIndex((certificate) =>
      certificate.id === updated.id);
    if (index !== -1) {
      saved[index] = updated;
      localStorage.setItem('certificates', JSON.stringify(saved));
      const cardCounter = saved.filter(card => card.checkout).length;
      const favoriteCounter = saved.filter(favorite => favorite.favorite).length;
      this.cardCounterChanged.emit(cardCounter);
      this.favoriteCounterChanged.emit(favoriteCounter);
    }
  }

  saveTagsToLocalStorage(tags: any) {
    localStorage.setItem('tags', JSON.stringify(tags));
  }

  public getTagsFromLocalStorage(): ICategory[] {
    const tags = localStorage.getItem('tags');
    return tags ? JSON.parse(tags) : [];
  }
}

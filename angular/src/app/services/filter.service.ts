import { Injectable, Input } from '@angular/core';
import { Certificate } from '../model/Certificate';
import { LocalStorageService } from './local-storage.service';
import { Tag } from '../model/Tag';
import { ICategory } from '../interfaces/ICategory';

@Injectable({
  providedIn: 'root',
})
export class FilterService {
  @Input() category: ICategory;
  @Input() filtered: Certificate[];
  @Input() certificates: Certificate[];

  constructor(private storage: LocalStorageService) {
    this.category = { name: '', tag: '' } as ICategory;
    this.filtered = [];
    this.certificates = this.storage.getCertificatesFromLocalStorage();
  }

  public filter(): void {
    console.log(
      'Service: filterBy ' + this.category.name + ' ' + this.category.tag
    );

    this.filtered = this.certificates.filter((certificate: Certificate) => {
      const matchesCategory =
        this.category.tag === 'All Categories' ||
        Array.from(certificate.tags).some(
          (tag: Tag) => tag.name === this.category.tag
        );

      const matchesQuery =
        certificate.name
          .toLowerCase()
          .includes(this.category.name.toLowerCase()) ||
        certificate.description
          .toLowerCase()
          .includes(this.category.name.toLowerCase());

      return matchesCategory && matchesQuery;
    });
  }
}

import {Directive, HostBinding} from '@angular/core';

@Directive({
  selector: '[appDisplay]',
  exportAs: 'displayControl'
})
export class DisplayDirective {

  @HostBinding('style.display')
  public display: 'none' | 'block' = 'block';

  loadOn() {
    console.log('block')
    this.display = 'block';
  }

  loadOff() {
    this.display = 'none';
  }
}

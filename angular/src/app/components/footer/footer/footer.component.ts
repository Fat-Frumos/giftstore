import { Component } from '@angular/core';
import { fromEvent, throttleTime } from 'rxjs';
import { ScrollService } from '../../../services/scroll.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss'],
})
export class FooterComponent {
  constructor(private scroll: ScrollService) {
    const scrollObservable = fromEvent(window, 'scroll').pipe(
      throttleTime(300)
    );

    scrollObservable.subscribe(() => {
      if (
        window.innerHeight + window.scrollY >=
        document.body.offsetHeight - 100
      ) {
        this.scroll.saveScrollPosition();
        // this.load();
      }

      const scrollButton = document.querySelector('.scroll-top');
      if (scrollButton) {
        if (window.scrollY > 300) {
          scrollButton.classList.add('show');
        } else {
          scrollButton.classList.remove('show');
        }
      }
    });
  }

  onScrollTop() {
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  }
}

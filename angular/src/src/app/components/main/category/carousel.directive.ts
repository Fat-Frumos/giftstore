import {
  Directive,
  Input,
  TemplateRef,
  ViewContainerRef,
  OnInit
} from '@angular/core';
import {ICategory} from "../../../interfaces/ICategory";

@Directive({
  selector: '[appCarousel]',
})
export class CarouselDirective implements OnInit {

  @Input('appCarouselInterval')
  public ms: number = 1000;

  @Input('appCarouselFrom')
  public categories: ICategory[] = [];
  @Input('appCarousel') context: any;

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainerRef: ViewContainerRef,
  ) {
  }

  ngOnInit() {
    this.viewContainerRef.createEmbeddedView(this.templateRef, this.context);
    console.log(this.categories.length)
  }
}

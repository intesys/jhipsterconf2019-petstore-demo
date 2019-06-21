import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { JhipetstoreSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [JhipetstoreSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [JhipetstoreSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipetstoreSharedModule {
  static forRoot() {
    return {
      ngModule: JhipetstoreSharedModule
    };
  }
}

import { NgModule } from '@angular/core';

import { JhipetstoreSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [JhipetstoreSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [JhipetstoreSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JhipetstoreSharedCommonModule {}

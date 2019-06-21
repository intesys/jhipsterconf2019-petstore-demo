import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipetstoreSharedModule } from 'app/shared';
import {
  PetComponent,
  PetDetailComponent,
  PetUpdateComponent,
  PetDeletePopupComponent,
  PetDeleteDialogComponent,
  petRoute,
  petPopupRoute
} from './';

const ENTITY_STATES = [...petRoute, ...petPopupRoute];

@NgModule({
  imports: [JhipetstoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PetComponent, PetDetailComponent, PetUpdateComponent, PetDeleteDialogComponent, PetDeletePopupComponent],
  entryComponents: [PetComponent, PetUpdateComponent, PetDeleteDialogComponent, PetDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipetstorePetModule {}

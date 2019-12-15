import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcAppSharedModule } from 'app/shared/shared.module';
import { RequisitoComponent } from './requisito.component';
import { RequisitoDetailComponent } from './requisito-detail.component';
import { RequisitoUpdateComponent } from './requisito-update.component';
import { RequisitoDeletePopupComponent, RequisitoDeleteDialogComponent } from './requisito-delete-dialog.component';
import { requisitoRoute, requisitoPopupRoute } from './requisito.route';

const ENTITY_STATES = [...requisitoRoute, ...requisitoPopupRoute];

@NgModule({
  imports: [SbcAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RequisitoComponent,
    RequisitoDetailComponent,
    RequisitoUpdateComponent,
    RequisitoDeleteDialogComponent,
    RequisitoDeletePopupComponent
  ],
  entryComponents: [RequisitoDeleteDialogComponent]
})
export class SbcAppRequisitoModule {}

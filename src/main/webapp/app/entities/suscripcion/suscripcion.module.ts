import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcAppSharedModule } from 'app/shared/shared.module';
import { SuscripcionComponent } from './suscripcion.component';
import { SuscripcionDetailComponent } from './suscripcion-detail.component';
import { SuscripcionUpdateComponent } from './suscripcion-update.component';
import { SuscripcionDeletePopupComponent, SuscripcionDeleteDialogComponent } from './suscripcion-delete-dialog.component';
import { suscripcionRoute, suscripcionPopupRoute } from './suscripcion.route';

const ENTITY_STATES = [...suscripcionRoute, ...suscripcionPopupRoute];

@NgModule({
  imports: [SbcAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SuscripcionComponent,
    SuscripcionDetailComponent,
    SuscripcionUpdateComponent,
    SuscripcionDeleteDialogComponent,
    SuscripcionDeletePopupComponent
  ],
  entryComponents: [SuscripcionDeleteDialogComponent]
})
export class SbcAppSuscripcionModule {}

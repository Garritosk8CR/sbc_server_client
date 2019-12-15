import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcAppSharedModule } from 'app/shared/shared.module';
import { PuestoDeTrabajoComponent } from './puesto-de-trabajo.component';
import { PuestoDeTrabajoDetailComponent } from './puesto-de-trabajo-detail.component';
import { PuestoDeTrabajoUpdateComponent } from './puesto-de-trabajo-update.component';
import { PuestoDeTrabajoDeletePopupComponent, PuestoDeTrabajoDeleteDialogComponent } from './puesto-de-trabajo-delete-dialog.component';
import { puestoDeTrabajoRoute, puestoDeTrabajoPopupRoute } from './puesto-de-trabajo.route';

const ENTITY_STATES = [...puestoDeTrabajoRoute, ...puestoDeTrabajoPopupRoute];

@NgModule({
  imports: [SbcAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PuestoDeTrabajoComponent,
    PuestoDeTrabajoDetailComponent,
    PuestoDeTrabajoUpdateComponent,
    PuestoDeTrabajoDeleteDialogComponent,
    PuestoDeTrabajoDeletePopupComponent
  ],
  entryComponents: [PuestoDeTrabajoDeleteDialogComponent]
})
export class SbcAppPuestoDeTrabajoModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcAppSharedModule } from 'app/shared/shared.module';
import { NotificacionComponent } from './notificacion.component';
import { NotificacionDetailComponent } from './notificacion-detail.component';
import { NotificacionUpdateComponent } from './notificacion-update.component';
import { NotificacionDeletePopupComponent, NotificacionDeleteDialogComponent } from './notificacion-delete-dialog.component';
import { notificacionRoute, notificacionPopupRoute } from './notificacion.route';

const ENTITY_STATES = [...notificacionRoute, ...notificacionPopupRoute];

@NgModule({
  imports: [SbcAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NotificacionComponent,
    NotificacionDetailComponent,
    NotificacionUpdateComponent,
    NotificacionDeleteDialogComponent,
    NotificacionDeletePopupComponent
  ],
  entryComponents: [NotificacionDeleteDialogComponent]
})
export class SbcAppNotificacionModule {}

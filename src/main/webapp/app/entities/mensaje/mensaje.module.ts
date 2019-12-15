import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcAppSharedModule } from 'app/shared/shared.module';
import { MensajeComponent } from './mensaje.component';
import { MensajeDetailComponent } from './mensaje-detail.component';
import { MensajeUpdateComponent } from './mensaje-update.component';
import { MensajeDeletePopupComponent, MensajeDeleteDialogComponent } from './mensaje-delete-dialog.component';
import { mensajeRoute, mensajePopupRoute } from './mensaje.route';

const ENTITY_STATES = [...mensajeRoute, ...mensajePopupRoute];

@NgModule({
  imports: [SbcAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MensajeComponent,
    MensajeDetailComponent,
    MensajeUpdateComponent,
    MensajeDeleteDialogComponent,
    MensajeDeletePopupComponent
  ],
  entryComponents: [MensajeDeleteDialogComponent]
})
export class SbcAppMensajeModule {}

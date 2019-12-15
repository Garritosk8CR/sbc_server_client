import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcAppSharedModule } from 'app/shared/shared.module';
import { HistoriaUsuarioComponent } from './historia-usuario.component';
import { HistoriaUsuarioDetailComponent } from './historia-usuario-detail.component';
import { HistoriaUsuarioUpdateComponent } from './historia-usuario-update.component';
import { HistoriaUsuarioDeletePopupComponent, HistoriaUsuarioDeleteDialogComponent } from './historia-usuario-delete-dialog.component';
import { historiaUsuarioRoute, historiaUsuarioPopupRoute } from './historia-usuario.route';

const ENTITY_STATES = [...historiaUsuarioRoute, ...historiaUsuarioPopupRoute];

@NgModule({
  imports: [SbcAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    HistoriaUsuarioComponent,
    HistoriaUsuarioDetailComponent,
    HistoriaUsuarioUpdateComponent,
    HistoriaUsuarioDeleteDialogComponent,
    HistoriaUsuarioDeletePopupComponent
  ],
  entryComponents: [HistoriaUsuarioDeleteDialogComponent]
})
export class SbcAppHistoriaUsuarioModule {}

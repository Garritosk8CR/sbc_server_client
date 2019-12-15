import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcAppSharedModule } from 'app/shared/shared.module';
import { PublicacionComponent } from './publicacion.component';
import { PublicacionDetailComponent } from './publicacion-detail.component';
import { PublicacionUpdateComponent } from './publicacion-update.component';
import { PublicacionDeletePopupComponent, PublicacionDeleteDialogComponent } from './publicacion-delete-dialog.component';
import { publicacionRoute, publicacionPopupRoute } from './publicacion.route';

const ENTITY_STATES = [...publicacionRoute, ...publicacionPopupRoute];

@NgModule({
  imports: [SbcAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PublicacionComponent,
    PublicacionDetailComponent,
    PublicacionUpdateComponent,
    PublicacionDeleteDialogComponent,
    PublicacionDeletePopupComponent
  ],
  entryComponents: [PublicacionDeleteDialogComponent]
})
export class SbcAppPublicacionModule {}

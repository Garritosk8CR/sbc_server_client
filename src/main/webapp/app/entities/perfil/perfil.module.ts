import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcAppSharedModule } from 'app/shared/shared.module';
import { PerfilComponent } from './perfil.component';
import { PerfilDetailComponent } from './perfil-detail.component';
import { PerfilUpdateComponent } from './perfil-update.component';
import { PerfilDeletePopupComponent, PerfilDeleteDialogComponent } from './perfil-delete-dialog.component';
import { perfilRoute, perfilPopupRoute } from './perfil.route';

const ENTITY_STATES = [...perfilRoute, ...perfilPopupRoute];

@NgModule({
  imports: [SbcAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PerfilComponent, PerfilDetailComponent, PerfilUpdateComponent, PerfilDeleteDialogComponent, PerfilDeletePopupComponent],
  entryComponents: [PerfilDeleteDialogComponent]
})
export class SbcAppPerfilModule {}

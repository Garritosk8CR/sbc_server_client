import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcAppSharedModule } from 'app/shared/shared.module';
import { ArticuloComponent } from './articulo.component';
import { ArticuloDetailComponent } from './articulo-detail.component';
import { ArticuloUpdateComponent } from './articulo-update.component';
import { ArticuloDeletePopupComponent, ArticuloDeleteDialogComponent } from './articulo-delete-dialog.component';
import { articuloRoute, articuloPopupRoute } from './articulo.route';

const ENTITY_STATES = [...articuloRoute, ...articuloPopupRoute];

@NgModule({
  imports: [SbcAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ArticuloComponent,
    ArticuloDetailComponent,
    ArticuloUpdateComponent,
    ArticuloDeleteDialogComponent,
    ArticuloDeletePopupComponent
  ],
  entryComponents: [ArticuloDeleteDialogComponent]
})
export class SbcAppArticuloModule {}

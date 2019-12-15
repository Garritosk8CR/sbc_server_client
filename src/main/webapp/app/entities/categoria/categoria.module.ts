import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcAppSharedModule } from 'app/shared/shared.module';
import { CategoriaComponent } from './categoria.component';
import { CategoriaDetailComponent } from './categoria-detail.component';
import { CategoriaUpdateComponent } from './categoria-update.component';
import { CategoriaDeletePopupComponent, CategoriaDeleteDialogComponent } from './categoria-delete-dialog.component';
import { categoriaRoute, categoriaPopupRoute } from './categoria.route';

const ENTITY_STATES = [...categoriaRoute, ...categoriaPopupRoute];

@NgModule({
  imports: [SbcAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CategoriaComponent,
    CategoriaDetailComponent,
    CategoriaUpdateComponent,
    CategoriaDeleteDialogComponent,
    CategoriaDeletePopupComponent
  ],
  entryComponents: [CategoriaDeleteDialogComponent]
})
export class SbcAppCategoriaModule {}

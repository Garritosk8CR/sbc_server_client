import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcAppSharedModule } from 'app/shared/shared.module';
import { ComentarioComponent } from './comentario.component';
import { ComentarioDetailComponent } from './comentario-detail.component';
import { ComentarioUpdateComponent } from './comentario-update.component';
import { ComentarioDeletePopupComponent, ComentarioDeleteDialogComponent } from './comentario-delete-dialog.component';
import { comentarioRoute, comentarioPopupRoute } from './comentario.route';

const ENTITY_STATES = [...comentarioRoute, ...comentarioPopupRoute];

@NgModule({
  imports: [SbcAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ComentarioComponent,
    ComentarioDetailComponent,
    ComentarioUpdateComponent,
    ComentarioDeleteDialogComponent,
    ComentarioDeletePopupComponent
  ],
  entryComponents: [ComentarioDeleteDialogComponent]
})
export class SbcAppComentarioModule {}

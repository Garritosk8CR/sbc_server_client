import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcAppSharedModule } from 'app/shared/shared.module';
import { TareaComponent } from './tarea.component';
import { TareaDetailComponent } from './tarea-detail.component';
import { TareaUpdateComponent } from './tarea-update.component';
import { TareaDeletePopupComponent, TareaDeleteDialogComponent } from './tarea-delete-dialog.component';
import { tareaRoute, tareaPopupRoute } from './tarea.route';

const ENTITY_STATES = [...tareaRoute, ...tareaPopupRoute];

@NgModule({
  imports: [SbcAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [TareaComponent, TareaDetailComponent, TareaUpdateComponent, TareaDeleteDialogComponent, TareaDeletePopupComponent],
  entryComponents: [TareaDeleteDialogComponent]
})
export class SbcAppTareaModule {}

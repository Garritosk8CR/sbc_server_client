import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcAppSharedModule } from 'app/shared/shared.module';
import { CarreraProfesionalComponent } from './carrera-profesional.component';
import { CarreraProfesionalDetailComponent } from './carrera-profesional-detail.component';
import { CarreraProfesionalUpdateComponent } from './carrera-profesional-update.component';
import {
  CarreraProfesionalDeletePopupComponent,
  CarreraProfesionalDeleteDialogComponent
} from './carrera-profesional-delete-dialog.component';
import { carreraProfesionalRoute, carreraProfesionalPopupRoute } from './carrera-profesional.route';

const ENTITY_STATES = [...carreraProfesionalRoute, ...carreraProfesionalPopupRoute];

@NgModule({
  imports: [SbcAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CarreraProfesionalComponent,
    CarreraProfesionalDetailComponent,
    CarreraProfesionalUpdateComponent,
    CarreraProfesionalDeleteDialogComponent,
    CarreraProfesionalDeletePopupComponent
  ],
  entryComponents: [CarreraProfesionalDeleteDialogComponent]
})
export class SbcAppCarreraProfesionalModule {}

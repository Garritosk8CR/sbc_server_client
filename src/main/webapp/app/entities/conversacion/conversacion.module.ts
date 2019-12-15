import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcAppSharedModule } from 'app/shared/shared.module';
import { ConversacionComponent } from './conversacion.component';
import { ConversacionDetailComponent } from './conversacion-detail.component';
import { ConversacionUpdateComponent } from './conversacion-update.component';
import { ConversacionDeletePopupComponent, ConversacionDeleteDialogComponent } from './conversacion-delete-dialog.component';
import { conversacionRoute, conversacionPopupRoute } from './conversacion.route';

const ENTITY_STATES = [...conversacionRoute, ...conversacionPopupRoute];

@NgModule({
  imports: [SbcAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ConversacionComponent,
    ConversacionDetailComponent,
    ConversacionUpdateComponent,
    ConversacionDeleteDialogComponent,
    ConversacionDeletePopupComponent
  ],
  entryComponents: [ConversacionDeleteDialogComponent]
})
export class SbcAppConversacionModule {}

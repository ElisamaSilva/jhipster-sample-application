import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SistemaDeliverySharedModule } from 'app/shared/shared.module';
import { EntregadorComponent } from './entregador.component';
import { EntregadorDetailComponent } from './entregador-detail.component';
import { EntregadorUpdateComponent } from './entregador-update.component';
import { EntregadorDeleteDialogComponent } from './entregador-delete-dialog.component';
import { entregadorRoute } from './entregador.route';

@NgModule({
  imports: [SistemaDeliverySharedModule, RouterModule.forChild(entregadorRoute)],
  declarations: [EntregadorComponent, EntregadorDetailComponent, EntregadorUpdateComponent, EntregadorDeleteDialogComponent],
  entryComponents: [EntregadorDeleteDialogComponent],
})
export class SistemaDeliveryEntregadorModule {}

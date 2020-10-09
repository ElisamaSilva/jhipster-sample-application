import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntregador } from 'app/shared/model/entregador.model';
import { EntregadorService } from './entregador.service';

@Component({
  templateUrl: './entregador-delete-dialog.component.html',
})
export class EntregadorDeleteDialogComponent {
  entregador?: IEntregador;

  constructor(
    protected entregadorService: EntregadorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.entregadorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('entregadorListModification');
      this.activeModal.close();
    });
  }
}

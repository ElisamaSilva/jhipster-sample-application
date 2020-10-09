import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEntregador } from 'app/shared/model/entregador.model';
import { EntregadorService } from './entregador.service';
import { EntregadorDeleteDialogComponent } from './entregador-delete-dialog.component';

@Component({
  selector: 'jhi-entregador',
  templateUrl: './entregador.component.html',
})
export class EntregadorComponent implements OnInit, OnDestroy {
  entregadors?: IEntregador[];
  eventSubscriber?: Subscription;

  constructor(protected entregadorService: EntregadorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.entregadorService.query().subscribe((res: HttpResponse<IEntregador[]>) => (this.entregadors = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEntregadors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEntregador): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEntregadors(): void {
    this.eventSubscriber = this.eventManager.subscribe('entregadorListModification', () => this.loadAll());
  }

  delete(entregador: IEntregador): void {
    const modalRef = this.modalService.open(EntregadorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.entregador = entregador;
  }
}

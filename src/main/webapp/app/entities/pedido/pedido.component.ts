import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPedido } from 'app/shared/model/pedido.model';
import { PedidoService } from './pedido.service';
import { PedidoDeleteDialogComponent } from './pedido-delete-dialog.component';

@Component({
  selector: 'jhi-pedido',
  templateUrl: './pedido.component.html',
})
export class PedidoComponent implements OnInit, OnDestroy {
  pedidos?: IPedido[];
  eventSubscriber?: Subscription;

  constructor(protected pedidoService: PedidoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.pedidoService.query().subscribe((res: HttpResponse<IPedido[]>) => (this.pedidos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPedidos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPedido): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPedidos(): void {
    this.eventSubscriber = this.eventManager.subscribe('pedidoListModification', () => this.loadAll());
  }

  delete(pedido: IPedido): void {
    const modalRef = this.modalService.open(PedidoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pedido = pedido;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPedido, Pedido } from 'app/shared/model/pedido.model';
import { PedidoService } from './pedido.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';
import { IEntregador } from 'app/shared/model/entregador.model';
import { EntregadorService } from 'app/entities/entregador/entregador.service';

type SelectableEntity = ICliente | IEntregador;

@Component({
  selector: 'jhi-pedido-update',
  templateUrl: './pedido-update.component.html',
})
export class PedidoUpdateComponent implements OnInit {
  isSaving = false;
  clientes: ICliente[] = [];
  entregadors: IEntregador[] = [];
  dataDoCadastroDp: any;

  editForm = this.fb.group({
    id: [],
    statusPedido: [null, [Validators.required]],
    pagamento: [null, [Validators.required]],
    dataDoCadastro: [],
    listaDeProdutos: [],
    precoTotal: [],
    valorParaTroco: [],
    entregadorAlocado: [],
    clientePediuPedido: [],
    observacoesDeEntrega: [],
    cliente: [],
    entregador: [],
  });

  constructor(
    protected pedidoService: PedidoService,
    protected clienteService: ClienteService,
    protected entregadorService: EntregadorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pedido }) => {
      this.updateForm(pedido);

      this.clienteService
        .query({ filter: 'pedido-is-null' })
        .pipe(
          map((res: HttpResponse<ICliente[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICliente[]) => {
          if (!pedido.cliente || !pedido.cliente.id) {
            this.clientes = resBody;
          } else {
            this.clienteService
              .find(pedido.cliente.id)
              .pipe(
                map((subRes: HttpResponse<ICliente>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICliente[]) => (this.clientes = concatRes));
          }
        });

      this.entregadorService.query().subscribe((res: HttpResponse<IEntregador[]>) => (this.entregadors = res.body || []));
    });
  }

  updateForm(pedido: IPedido): void {
    this.editForm.patchValue({
      id: pedido.id,
      statusPedido: pedido.statusPedido,
      pagamento: pedido.pagamento,
      dataDoCadastro: pedido.dataDoCadastro,
      listaDeProdutos: pedido.listaDeProdutos,
      precoTotal: pedido.precoTotal,
      valorParaTroco: pedido.valorParaTroco,
      entregadorAlocado: pedido.entregadorAlocado,
      clientePediuPedido: pedido.clientePediuPedido,
      observacoesDeEntrega: pedido.observacoesDeEntrega,
      cliente: pedido.cliente,
      entregador: pedido.entregador,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pedido = this.createFromForm();
    if (pedido.id !== undefined) {
      this.subscribeToSaveResponse(this.pedidoService.update(pedido));
    } else {
      this.subscribeToSaveResponse(this.pedidoService.create(pedido));
    }
  }

  private createFromForm(): IPedido {
    return {
      ...new Pedido(),
      id: this.editForm.get(['id'])!.value,
      statusPedido: this.editForm.get(['statusPedido'])!.value,
      pagamento: this.editForm.get(['pagamento'])!.value,
      dataDoCadastro: this.editForm.get(['dataDoCadastro'])!.value,
      listaDeProdutos: this.editForm.get(['listaDeProdutos'])!.value,
      precoTotal: this.editForm.get(['precoTotal'])!.value,
      valorParaTroco: this.editForm.get(['valorParaTroco'])!.value,
      entregadorAlocado: this.editForm.get(['entregadorAlocado'])!.value,
      clientePediuPedido: this.editForm.get(['clientePediuPedido'])!.value,
      observacoesDeEntrega: this.editForm.get(['observacoesDeEntrega'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
      entregador: this.editForm.get(['entregador'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPedido>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}

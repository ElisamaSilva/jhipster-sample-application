import { Moment } from 'moment';
import { ICliente } from 'app/shared/model/cliente.model';
import { IEntregador } from 'app/shared/model/entregador.model';
import { StatusdoPedido } from 'app/shared/model/enumerations/statusdo-pedido.model';
import { FormadePagamento } from 'app/shared/model/enumerations/formade-pagamento.model';

export interface IPedido {
  id?: number;
  statusPedido?: StatusdoPedido;
  pagamento?: FormadePagamento;
  dataDoCadastro?: Moment;
  listaDeProdutos?: string;
  precoTotal?: number;
  valorParaTroco?: number;
  entregadorAlocado?: boolean;
  clientePediuPedido?: boolean;
  observacoesDeEntrega?: string;
  cliente?: ICliente;
  entregador?: IEntregador;
}

export class Pedido implements IPedido {
  constructor(
    public id?: number,
    public statusPedido?: StatusdoPedido,
    public pagamento?: FormadePagamento,
    public dataDoCadastro?: Moment,
    public listaDeProdutos?: string,
    public precoTotal?: number,
    public valorParaTroco?: number,
    public entregadorAlocado?: boolean,
    public clientePediuPedido?: boolean,
    public observacoesDeEntrega?: string,
    public cliente?: ICliente,
    public entregador?: IEntregador
  ) {
    this.entregadorAlocado = this.entregadorAlocado || false;
    this.clientePediuPedido = this.clientePediuPedido || false;
  }
}

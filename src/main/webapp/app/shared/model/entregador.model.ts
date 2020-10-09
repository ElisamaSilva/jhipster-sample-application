import { IPedido } from 'app/shared/model/pedido.model';
import { ICliente } from 'app/shared/model/cliente.model';

export interface IEntregador {
  id?: number;
  nome?: string;
  placaDaMoto?: string;
  latitudeAtual?: number;
  longitudeAtual?: number;
  cpf?: number;
  linkParaFoto?: string;
  pedidos?: IPedido[];
  clientes?: ICliente[];
}

export class Entregador implements IEntregador {
  constructor(
    public id?: number,
    public nome?: string,
    public placaDaMoto?: string,
    public latitudeAtual?: number,
    public longitudeAtual?: number,
    public cpf?: number,
    public linkParaFoto?: string,
    public pedidos?: IPedido[],
    public clientes?: ICliente[]
  ) {}
}

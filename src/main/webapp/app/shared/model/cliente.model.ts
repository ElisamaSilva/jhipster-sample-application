import { IProduto } from 'app/shared/model/produto.model';
import { IEntregador } from 'app/shared/model/entregador.model';

export interface ICliente {
  id?: number;
  nome?: string;
  telefone?: string;
  email?: string;
  login?: string;
  senha?: string;
  cepDeEndereco?: number;
  logradouro?: string;
  bairro?: string;
  cidade?: string;
  produtos?: IProduto[];
  entregadores?: IEntregador[];
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public nome?: string,
    public telefone?: string,
    public email?: string,
    public login?: string,
    public senha?: string,
    public cepDeEndereco?: number,
    public logradouro?: string,
    public bairro?: string,
    public cidade?: string,
    public produtos?: IProduto[],
    public entregadores?: IEntregador[]
  ) {}
}

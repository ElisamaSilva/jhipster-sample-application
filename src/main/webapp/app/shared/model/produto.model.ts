import { Moment } from 'moment';
import { ICliente } from 'app/shared/model/cliente.model';
import { EstadoProduto } from 'app/shared/model/enumerations/estado-produto.model';

export interface IProduto {
  id?: number;
  produto?: EstadoProduto;
  nome?: string;
  descricao?: string;
  preco?: number;
  linkParaFoto?: string;
  dataDeCadastro?: Moment;
  totalEmEstoque?: number;
  cliente?: ICliente;
}

export class Produto implements IProduto {
  constructor(
    public id?: number,
    public produto?: EstadoProduto,
    public nome?: string,
    public descricao?: string,
    public preco?: number,
    public linkParaFoto?: string,
    public dataDeCadastro?: Moment,
    public totalEmEstoque?: number,
    public cliente?: ICliente
  ) {}
}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'produto',
        loadChildren: () => import('./produto/produto.module').then(m => m.SistemaDeliveryProdutoModule),
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.SistemaDeliveryClienteModule),
      },
      {
        path: 'pedido',
        loadChildren: () => import('./pedido/pedido.module').then(m => m.SistemaDeliveryPedidoModule),
      },
      {
        path: 'entregador',
        loadChildren: () => import('./entregador/entregador.module').then(m => m.SistemaDeliveryEntregadorModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SistemaDeliveryEntityModule {}

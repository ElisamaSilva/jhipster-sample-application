import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntregador } from 'app/shared/model/entregador.model';

@Component({
  selector: 'jhi-entregador-detail',
  templateUrl: './entregador-detail.component.html',
})
export class EntregadorDetailComponent implements OnInit {
  entregador: IEntregador | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entregador }) => (this.entregador = entregador));
  }

  previousState(): void {
    window.history.back();
  }
}

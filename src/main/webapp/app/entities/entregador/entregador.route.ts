import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEntregador, Entregador } from 'app/shared/model/entregador.model';
import { EntregadorService } from './entregador.service';
import { EntregadorComponent } from './entregador.component';
import { EntregadorDetailComponent } from './entregador-detail.component';
import { EntregadorUpdateComponent } from './entregador-update.component';

@Injectable({ providedIn: 'root' })
export class EntregadorResolve implements Resolve<IEntregador> {
  constructor(private service: EntregadorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEntregador> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((entregador: HttpResponse<Entregador>) => {
          if (entregador.body) {
            return of(entregador.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Entregador());
  }
}

export const entregadorRoute: Routes = [
  {
    path: '',
    component: EntregadorComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sistemaDeliveryApp.entregador.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EntregadorDetailComponent,
    resolve: {
      entregador: EntregadorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sistemaDeliveryApp.entregador.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EntregadorUpdateComponent,
    resolve: {
      entregador: EntregadorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sistemaDeliveryApp.entregador.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EntregadorUpdateComponent,
    resolve: {
      entregador: EntregadorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sistemaDeliveryApp.entregador.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

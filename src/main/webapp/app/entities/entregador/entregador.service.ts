import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEntregador } from 'app/shared/model/entregador.model';

type EntityResponseType = HttpResponse<IEntregador>;
type EntityArrayResponseType = HttpResponse<IEntregador[]>;

@Injectable({ providedIn: 'root' })
export class EntregadorService {
  public resourceUrl = SERVER_API_URL + 'api/entregadors';

  constructor(protected http: HttpClient) {}

  create(entregador: IEntregador): Observable<EntityResponseType> {
    return this.http.post<IEntregador>(this.resourceUrl, entregador, { observe: 'response' });
  }

  update(entregador: IEntregador): Observable<EntityResponseType> {
    return this.http.put<IEntregador>(this.resourceUrl, entregador, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEntregador>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEntregador[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

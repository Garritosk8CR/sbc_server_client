import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPuestoDeTrabajo } from 'app/shared/model/puesto-de-trabajo.model';

type EntityResponseType = HttpResponse<IPuestoDeTrabajo>;
type EntityArrayResponseType = HttpResponse<IPuestoDeTrabajo[]>;

@Injectable({ providedIn: 'root' })
export class PuestoDeTrabajoService {
  public resourceUrl = SERVER_API_URL + 'api/puesto-de-trabajos';

  constructor(protected http: HttpClient) {}

  create(puestoDeTrabajo: IPuestoDeTrabajo): Observable<EntityResponseType> {
    return this.http.post<IPuestoDeTrabajo>(this.resourceUrl, puestoDeTrabajo, { observe: 'response' });
  }

  update(puestoDeTrabajo: IPuestoDeTrabajo): Observable<EntityResponseType> {
    return this.http.put<IPuestoDeTrabajo>(this.resourceUrl, puestoDeTrabajo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPuestoDeTrabajo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPuestoDeTrabajo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

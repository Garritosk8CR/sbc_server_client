import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMensaje } from 'app/shared/model/mensaje.model';

type EntityResponseType = HttpResponse<IMensaje>;
type EntityArrayResponseType = HttpResponse<IMensaje[]>;

@Injectable({ providedIn: 'root' })
export class MensajeService {
  public resourceUrl = SERVER_API_URL + 'api/mensajes';

  constructor(protected http: HttpClient) {}

  create(mensaje: IMensaje): Observable<EntityResponseType> {
    return this.http.post<IMensaje>(this.resourceUrl, mensaje, { observe: 'response' });
  }

  update(mensaje: IMensaje): Observable<EntityResponseType> {
    return this.http.put<IMensaje>(this.resourceUrl, mensaje, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMensaje>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMensaje[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

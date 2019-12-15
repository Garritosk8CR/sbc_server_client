import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISuscripcion } from 'app/shared/model/suscripcion.model';

type EntityResponseType = HttpResponse<ISuscripcion>;
type EntityArrayResponseType = HttpResponse<ISuscripcion[]>;

@Injectable({ providedIn: 'root' })
export class SuscripcionService {
  public resourceUrl = SERVER_API_URL + 'api/suscripcions';

  constructor(protected http: HttpClient) {}

  create(suscripcion: ISuscripcion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(suscripcion);
    return this.http
      .post<ISuscripcion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(suscripcion: ISuscripcion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(suscripcion);
    return this.http
      .put<ISuscripcion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISuscripcion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISuscripcion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(suscripcion: ISuscripcion): ISuscripcion {
    const copy: ISuscripcion = Object.assign({}, suscripcion, {
      fechaSuscripcion:
        suscripcion.fechaSuscripcion != null && suscripcion.fechaSuscripcion.isValid()
          ? suscripcion.fechaSuscripcion.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaSuscripcion = res.body.fechaSuscripcion != null ? moment(res.body.fechaSuscripcion) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((suscripcion: ISuscripcion) => {
        suscripcion.fechaSuscripcion = suscripcion.fechaSuscripcion != null ? moment(suscripcion.fechaSuscripcion) : null;
      });
    }
    return res;
  }
}

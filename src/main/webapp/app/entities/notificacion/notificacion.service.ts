import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INotificacion } from 'app/shared/model/notificacion.model';

type EntityResponseType = HttpResponse<INotificacion>;
type EntityArrayResponseType = HttpResponse<INotificacion[]>;

@Injectable({ providedIn: 'root' })
export class NotificacionService {
  public resourceUrl = SERVER_API_URL + 'api/notificacions';

  constructor(protected http: HttpClient) {}

  create(notificacion: INotificacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notificacion);
    return this.http
      .post<INotificacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(notificacion: INotificacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notificacion);
    return this.http
      .put<INotificacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INotificacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INotificacion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(notificacion: INotificacion): INotificacion {
    const copy: INotificacion = Object.assign({}, notificacion, {
      fechaEmision:
        notificacion.fechaEmision != null && notificacion.fechaEmision.isValid() ? notificacion.fechaEmision.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaEmision = res.body.fechaEmision != null ? moment(res.body.fechaEmision) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((notificacion: INotificacion) => {
        notificacion.fechaEmision = notificacion.fechaEmision != null ? moment(notificacion.fechaEmision) : null;
      });
    }
    return res;
  }
}

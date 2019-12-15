import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConversacion } from 'app/shared/model/conversacion.model';

type EntityResponseType = HttpResponse<IConversacion>;
type EntityArrayResponseType = HttpResponse<IConversacion[]>;

@Injectable({ providedIn: 'root' })
export class ConversacionService {
  public resourceUrl = SERVER_API_URL + 'api/conversacions';

  constructor(protected http: HttpClient) {}

  create(conversacion: IConversacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conversacion);
    return this.http
      .post<IConversacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(conversacion: IConversacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conversacion);
    return this.http
      .put<IConversacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConversacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConversacion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(conversacion: IConversacion): IConversacion {
    const copy: IConversacion = Object.assign({}, conversacion, {
      fechaDeConversacion:
        conversacion.fechaDeConversacion != null && conversacion.fechaDeConversacion.isValid()
          ? conversacion.fechaDeConversacion.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaDeConversacion = res.body.fechaDeConversacion != null ? moment(res.body.fechaDeConversacion) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((conversacion: IConversacion) => {
        conversacion.fechaDeConversacion = conversacion.fechaDeConversacion != null ? moment(conversacion.fechaDeConversacion) : null;
      });
    }
    return res;
  }
}

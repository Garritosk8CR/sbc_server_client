import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';

type EntityResponseType = HttpResponse<IHistoriaUsuario>;
type EntityArrayResponseType = HttpResponse<IHistoriaUsuario[]>;

@Injectable({ providedIn: 'root' })
export class HistoriaUsuarioService {
  public resourceUrl = SERVER_API_URL + 'api/historia-usuarios';

  constructor(protected http: HttpClient) {}

  create(historiaUsuario: IHistoriaUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historiaUsuario);
    return this.http
      .post<IHistoriaUsuario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(historiaUsuario: IHistoriaUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historiaUsuario);
    return this.http
      .put<IHistoriaUsuario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHistoriaUsuario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHistoriaUsuario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(historiaUsuario: IHistoriaUsuario): IHistoriaUsuario {
    const copy: IHistoriaUsuario = Object.assign({}, historiaUsuario, {
      fechaCreacion:
        historiaUsuario.fechaCreacion != null && historiaUsuario.fechaCreacion.isValid()
          ? historiaUsuario.fechaCreacion.format(DATE_FORMAT)
          : null,
      fechaConclucion:
        historiaUsuario.fechaConclucion != null && historiaUsuario.fechaConclucion.isValid()
          ? historiaUsuario.fechaConclucion.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaCreacion = res.body.fechaCreacion != null ? moment(res.body.fechaCreacion) : null;
      res.body.fechaConclucion = res.body.fechaConclucion != null ? moment(res.body.fechaConclucion) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((historiaUsuario: IHistoriaUsuario) => {
        historiaUsuario.fechaCreacion = historiaUsuario.fechaCreacion != null ? moment(historiaUsuario.fechaCreacion) : null;
        historiaUsuario.fechaConclucion = historiaUsuario.fechaConclucion != null ? moment(historiaUsuario.fechaConclucion) : null;
      });
    }
    return res;
  }
}

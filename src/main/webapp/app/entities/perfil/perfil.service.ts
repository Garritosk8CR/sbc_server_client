import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPerfil } from 'app/shared/model/perfil.model';

type EntityResponseType = HttpResponse<IPerfil>;
type EntityArrayResponseType = HttpResponse<IPerfil[]>;

@Injectable({ providedIn: 'root' })
export class PerfilService {
  public resourceUrl = SERVER_API_URL + 'api/perfils';

  constructor(protected http: HttpClient) {}

  create(perfil: IPerfil): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(perfil);
    return this.http
      .post<IPerfil>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(perfil: IPerfil): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(perfil);
    return this.http
      .put<IPerfil>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPerfil>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPerfil[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(perfil: IPerfil): IPerfil {
    const copy: IPerfil = Object.assign({}, perfil, {
      fechaIngreso: perfil.fechaIngreso != null && perfil.fechaIngreso.isValid() ? perfil.fechaIngreso.format(DATE_FORMAT) : null,
      fechaSalida: perfil.fechaSalida != null && perfil.fechaSalida.isValid() ? perfil.fechaSalida.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaIngreso = res.body.fechaIngreso != null ? moment(res.body.fechaIngreso) : null;
      res.body.fechaSalida = res.body.fechaSalida != null ? moment(res.body.fechaSalida) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((perfil: IPerfil) => {
        perfil.fechaIngreso = perfil.fechaIngreso != null ? moment(perfil.fechaIngreso) : null;
        perfil.fechaSalida = perfil.fechaSalida != null ? moment(perfil.fechaSalida) : null;
      });
    }
    return res;
  }
}

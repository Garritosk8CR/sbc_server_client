import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IArticulo } from 'app/shared/model/articulo.model';

type EntityResponseType = HttpResponse<IArticulo>;
type EntityArrayResponseType = HttpResponse<IArticulo[]>;

@Injectable({ providedIn: 'root' })
export class ArticuloService {
  public resourceUrl = SERVER_API_URL + 'api/articulos';

  constructor(protected http: HttpClient) {}

  create(articulo: IArticulo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(articulo);
    return this.http
      .post<IArticulo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(articulo: IArticulo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(articulo);
    return this.http
      .put<IArticulo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IArticulo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IArticulo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(articulo: IArticulo): IArticulo {
    const copy: IArticulo = Object.assign({}, articulo, {
      fechaCreacion: articulo.fechaCreacion != null && articulo.fechaCreacion.isValid() ? articulo.fechaCreacion.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaCreacion = res.body.fechaCreacion != null ? moment(res.body.fechaCreacion) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((articulo: IArticulo) => {
        articulo.fechaCreacion = articulo.fechaCreacion != null ? moment(articulo.fechaCreacion) : null;
      });
    }
    return res;
  }
}

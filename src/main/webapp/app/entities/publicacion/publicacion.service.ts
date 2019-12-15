import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPublicacion } from 'app/shared/model/publicacion.model';

type EntityResponseType = HttpResponse<IPublicacion>;
type EntityArrayResponseType = HttpResponse<IPublicacion[]>;

@Injectable({ providedIn: 'root' })
export class PublicacionService {
  public resourceUrl = SERVER_API_URL + 'api/publicacions';

  constructor(protected http: HttpClient) {}

  create(publicacion: IPublicacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(publicacion);
    return this.http
      .post<IPublicacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(publicacion: IPublicacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(publicacion);
    return this.http
      .put<IPublicacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPublicacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPublicacion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(publicacion: IPublicacion): IPublicacion {
    const copy: IPublicacion = Object.assign({}, publicacion, {
      fechaPublicacion:
        publicacion.fechaPublicacion != null && publicacion.fechaPublicacion.isValid()
          ? publicacion.fechaPublicacion.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaPublicacion = res.body.fechaPublicacion != null ? moment(res.body.fechaPublicacion) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((publicacion: IPublicacion) => {
        publicacion.fechaPublicacion = publicacion.fechaPublicacion != null ? moment(publicacion.fechaPublicacion) : null;
      });
    }
    return res;
  }
}

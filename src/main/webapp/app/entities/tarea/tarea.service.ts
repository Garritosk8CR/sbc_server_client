import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITarea } from 'app/shared/model/tarea.model';

type EntityResponseType = HttpResponse<ITarea>;
type EntityArrayResponseType = HttpResponse<ITarea[]>;

@Injectable({ providedIn: 'root' })
export class TareaService {
  public resourceUrl = SERVER_API_URL + 'api/tareas';

  constructor(protected http: HttpClient) {}

  create(tarea: ITarea): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarea);
    return this.http
      .post<ITarea>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tarea: ITarea): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarea);
    return this.http
      .put<ITarea>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITarea>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITarea[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tarea: ITarea): ITarea {
    const copy: ITarea = Object.assign({}, tarea, {
      fechaCreacion: tarea.fechaCreacion != null && tarea.fechaCreacion.isValid() ? tarea.fechaCreacion.format(DATE_FORMAT) : null,
      fechaConclucion: tarea.fechaConclucion != null && tarea.fechaConclucion.isValid() ? tarea.fechaConclucion.format(DATE_FORMAT) : null
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
      res.body.forEach((tarea: ITarea) => {
        tarea.fechaCreacion = tarea.fechaCreacion != null ? moment(tarea.fechaCreacion) : null;
        tarea.fechaConclucion = tarea.fechaConclucion != null ? moment(tarea.fechaConclucion) : null;
      });
    }
    return res;
  }
}

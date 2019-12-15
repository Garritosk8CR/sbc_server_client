import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICarreraProfesional } from 'app/shared/model/carrera-profesional.model';

type EntityResponseType = HttpResponse<ICarreraProfesional>;
type EntityArrayResponseType = HttpResponse<ICarreraProfesional[]>;

@Injectable({ providedIn: 'root' })
export class CarreraProfesionalService {
  public resourceUrl = SERVER_API_URL + 'api/carrera-profesionals';

  constructor(protected http: HttpClient) {}

  create(carreraProfesional: ICarreraProfesional): Observable<EntityResponseType> {
    return this.http.post<ICarreraProfesional>(this.resourceUrl, carreraProfesional, { observe: 'response' });
  }

  update(carreraProfesional: ICarreraProfesional): Observable<EntityResponseType> {
    return this.http.put<ICarreraProfesional>(this.resourceUrl, carreraProfesional, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICarreraProfesional>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICarreraProfesional[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

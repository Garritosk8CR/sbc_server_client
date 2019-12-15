import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRequisito } from 'app/shared/model/requisito.model';

type EntityResponseType = HttpResponse<IRequisito>;
type EntityArrayResponseType = HttpResponse<IRequisito[]>;

@Injectable({ providedIn: 'root' })
export class RequisitoService {
  public resourceUrl = SERVER_API_URL + 'api/requisitos';

  constructor(protected http: HttpClient) {}

  create(requisito: IRequisito): Observable<EntityResponseType> {
    return this.http.post<IRequisito>(this.resourceUrl, requisito, { observe: 'response' });
  }

  update(requisito: IRequisito): Observable<EntityResponseType> {
    return this.http.put<IRequisito>(this.resourceUrl, requisito, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRequisito>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRequisito[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Requisito } from 'app/shared/model/requisito.model';
import { RequisitoService } from './requisito.service';
import { RequisitoComponent } from './requisito.component';
import { RequisitoDetailComponent } from './requisito-detail.component';
import { RequisitoUpdateComponent } from './requisito-update.component';
import { RequisitoDeletePopupComponent } from './requisito-delete-dialog.component';
import { IRequisito } from 'app/shared/model/requisito.model';

@Injectable({ providedIn: 'root' })
export class RequisitoResolve implements Resolve<IRequisito> {
  constructor(private service: RequisitoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRequisito> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Requisito>) => response.ok),
        map((requisito: HttpResponse<Requisito>) => requisito.body)
      );
    }
    return of(new Requisito());
  }
}

export const requisitoRoute: Routes = [
  {
    path: '',
    component: RequisitoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.requisito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RequisitoDetailComponent,
    resolve: {
      requisito: RequisitoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.requisito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RequisitoUpdateComponent,
    resolve: {
      requisito: RequisitoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.requisito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RequisitoUpdateComponent,
    resolve: {
      requisito: RequisitoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.requisito.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const requisitoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RequisitoDeletePopupComponent,
    resolve: {
      requisito: RequisitoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.requisito.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

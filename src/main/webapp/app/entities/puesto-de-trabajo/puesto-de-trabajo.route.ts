import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PuestoDeTrabajo } from 'app/shared/model/puesto-de-trabajo.model';
import { PuestoDeTrabajoService } from './puesto-de-trabajo.service';
import { PuestoDeTrabajoComponent } from './puesto-de-trabajo.component';
import { PuestoDeTrabajoDetailComponent } from './puesto-de-trabajo-detail.component';
import { PuestoDeTrabajoUpdateComponent } from './puesto-de-trabajo-update.component';
import { PuestoDeTrabajoDeletePopupComponent } from './puesto-de-trabajo-delete-dialog.component';
import { IPuestoDeTrabajo } from 'app/shared/model/puesto-de-trabajo.model';

@Injectable({ providedIn: 'root' })
export class PuestoDeTrabajoResolve implements Resolve<IPuestoDeTrabajo> {
  constructor(private service: PuestoDeTrabajoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPuestoDeTrabajo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PuestoDeTrabajo>) => response.ok),
        map((puestoDeTrabajo: HttpResponse<PuestoDeTrabajo>) => puestoDeTrabajo.body)
      );
    }
    return of(new PuestoDeTrabajo());
  }
}

export const puestoDeTrabajoRoute: Routes = [
  {
    path: '',
    component: PuestoDeTrabajoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.puestoDeTrabajo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PuestoDeTrabajoDetailComponent,
    resolve: {
      puestoDeTrabajo: PuestoDeTrabajoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.puestoDeTrabajo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PuestoDeTrabajoUpdateComponent,
    resolve: {
      puestoDeTrabajo: PuestoDeTrabajoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.puestoDeTrabajo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PuestoDeTrabajoUpdateComponent,
    resolve: {
      puestoDeTrabajo: PuestoDeTrabajoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.puestoDeTrabajo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const puestoDeTrabajoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PuestoDeTrabajoDeletePopupComponent,
    resolve: {
      puestoDeTrabajo: PuestoDeTrabajoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.puestoDeTrabajo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

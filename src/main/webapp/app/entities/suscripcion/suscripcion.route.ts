import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Suscripcion } from 'app/shared/model/suscripcion.model';
import { SuscripcionService } from './suscripcion.service';
import { SuscripcionComponent } from './suscripcion.component';
import { SuscripcionDetailComponent } from './suscripcion-detail.component';
import { SuscripcionUpdateComponent } from './suscripcion-update.component';
import { SuscripcionDeletePopupComponent } from './suscripcion-delete-dialog.component';
import { ISuscripcion } from 'app/shared/model/suscripcion.model';

@Injectable({ providedIn: 'root' })
export class SuscripcionResolve implements Resolve<ISuscripcion> {
  constructor(private service: SuscripcionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISuscripcion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Suscripcion>) => response.ok),
        map((suscripcion: HttpResponse<Suscripcion>) => suscripcion.body)
      );
    }
    return of(new Suscripcion());
  }
}

export const suscripcionRoute: Routes = [
  {
    path: '',
    component: SuscripcionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.suscripcion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SuscripcionDetailComponent,
    resolve: {
      suscripcion: SuscripcionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.suscripcion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SuscripcionUpdateComponent,
    resolve: {
      suscripcion: SuscripcionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.suscripcion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SuscripcionUpdateComponent,
    resolve: {
      suscripcion: SuscripcionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.suscripcion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const suscripcionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SuscripcionDeletePopupComponent,
    resolve: {
      suscripcion: SuscripcionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.suscripcion.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

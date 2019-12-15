import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Notificacion } from 'app/shared/model/notificacion.model';
import { NotificacionService } from './notificacion.service';
import { NotificacionComponent } from './notificacion.component';
import { NotificacionDetailComponent } from './notificacion-detail.component';
import { NotificacionUpdateComponent } from './notificacion-update.component';
import { NotificacionDeletePopupComponent } from './notificacion-delete-dialog.component';
import { INotificacion } from 'app/shared/model/notificacion.model';

@Injectable({ providedIn: 'root' })
export class NotificacionResolve implements Resolve<INotificacion> {
  constructor(private service: NotificacionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INotificacion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Notificacion>) => response.ok),
        map((notificacion: HttpResponse<Notificacion>) => notificacion.body)
      );
    }
    return of(new Notificacion());
  }
}

export const notificacionRoute: Routes = [
  {
    path: '',
    component: NotificacionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.notificacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NotificacionDetailComponent,
    resolve: {
      notificacion: NotificacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.notificacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NotificacionUpdateComponent,
    resolve: {
      notificacion: NotificacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.notificacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NotificacionUpdateComponent,
    resolve: {
      notificacion: NotificacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.notificacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const notificacionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NotificacionDeletePopupComponent,
    resolve: {
      notificacion: NotificacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.notificacion.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

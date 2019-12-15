import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Mensaje } from 'app/shared/model/mensaje.model';
import { MensajeService } from './mensaje.service';
import { MensajeComponent } from './mensaje.component';
import { MensajeDetailComponent } from './mensaje-detail.component';
import { MensajeUpdateComponent } from './mensaje-update.component';
import { MensajeDeletePopupComponent } from './mensaje-delete-dialog.component';
import { IMensaje } from 'app/shared/model/mensaje.model';

@Injectable({ providedIn: 'root' })
export class MensajeResolve implements Resolve<IMensaje> {
  constructor(private service: MensajeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMensaje> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Mensaje>) => response.ok),
        map((mensaje: HttpResponse<Mensaje>) => mensaje.body)
      );
    }
    return of(new Mensaje());
  }
}

export const mensajeRoute: Routes = [
  {
    path: '',
    component: MensajeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.mensaje.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MensajeDetailComponent,
    resolve: {
      mensaje: MensajeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.mensaje.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MensajeUpdateComponent,
    resolve: {
      mensaje: MensajeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.mensaje.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MensajeUpdateComponent,
    resolve: {
      mensaje: MensajeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.mensaje.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const mensajePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MensajeDeletePopupComponent,
    resolve: {
      mensaje: MensajeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.mensaje.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HistoriaUsuario } from 'app/shared/model/historia-usuario.model';
import { HistoriaUsuarioService } from './historia-usuario.service';
import { HistoriaUsuarioComponent } from './historia-usuario.component';
import { HistoriaUsuarioDetailComponent } from './historia-usuario-detail.component';
import { HistoriaUsuarioUpdateComponent } from './historia-usuario-update.component';
import { HistoriaUsuarioDeletePopupComponent } from './historia-usuario-delete-dialog.component';
import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';

@Injectable({ providedIn: 'root' })
export class HistoriaUsuarioResolve implements Resolve<IHistoriaUsuario> {
  constructor(private service: HistoriaUsuarioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHistoriaUsuario> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<HistoriaUsuario>) => response.ok),
        map((historiaUsuario: HttpResponse<HistoriaUsuario>) => historiaUsuario.body)
      );
    }
    return of(new HistoriaUsuario());
  }
}

export const historiaUsuarioRoute: Routes = [
  {
    path: '',
    component: HistoriaUsuarioComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.historiaUsuario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HistoriaUsuarioDetailComponent,
    resolve: {
      historiaUsuario: HistoriaUsuarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.historiaUsuario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HistoriaUsuarioUpdateComponent,
    resolve: {
      historiaUsuario: HistoriaUsuarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.historiaUsuario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HistoriaUsuarioUpdateComponent,
    resolve: {
      historiaUsuario: HistoriaUsuarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.historiaUsuario.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const historiaUsuarioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: HistoriaUsuarioDeletePopupComponent,
    resolve: {
      historiaUsuario: HistoriaUsuarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.historiaUsuario.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

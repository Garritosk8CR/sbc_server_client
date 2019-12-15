import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Perfil } from 'app/shared/model/perfil.model';
import { PerfilService } from './perfil.service';
import { PerfilComponent } from './perfil.component';
import { PerfilDetailComponent } from './perfil-detail.component';
import { PerfilUpdateComponent } from './perfil-update.component';
import { PerfilDeletePopupComponent } from './perfil-delete-dialog.component';
import { IPerfil } from 'app/shared/model/perfil.model';

@Injectable({ providedIn: 'root' })
export class PerfilResolve implements Resolve<IPerfil> {
  constructor(private service: PerfilService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPerfil> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Perfil>) => response.ok),
        map((perfil: HttpResponse<Perfil>) => perfil.body)
      );
    }
    return of(new Perfil());
  }
}

export const perfilRoute: Routes = [
  {
    path: '',
    component: PerfilComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.perfil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PerfilDetailComponent,
    resolve: {
      perfil: PerfilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.perfil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PerfilUpdateComponent,
    resolve: {
      perfil: PerfilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.perfil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PerfilUpdateComponent,
    resolve: {
      perfil: PerfilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.perfil.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const perfilPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PerfilDeletePopupComponent,
    resolve: {
      perfil: PerfilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.perfil.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

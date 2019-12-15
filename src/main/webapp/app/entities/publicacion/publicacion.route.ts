import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Publicacion } from 'app/shared/model/publicacion.model';
import { PublicacionService } from './publicacion.service';
import { PublicacionComponent } from './publicacion.component';
import { PublicacionDetailComponent } from './publicacion-detail.component';
import { PublicacionUpdateComponent } from './publicacion-update.component';
import { PublicacionDeletePopupComponent } from './publicacion-delete-dialog.component';
import { IPublicacion } from 'app/shared/model/publicacion.model';

@Injectable({ providedIn: 'root' })
export class PublicacionResolve implements Resolve<IPublicacion> {
  constructor(private service: PublicacionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPublicacion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Publicacion>) => response.ok),
        map((publicacion: HttpResponse<Publicacion>) => publicacion.body)
      );
    }
    return of(new Publicacion());
  }
}

export const publicacionRoute: Routes = [
  {
    path: '',
    component: PublicacionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.publicacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PublicacionDetailComponent,
    resolve: {
      publicacion: PublicacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.publicacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PublicacionUpdateComponent,
    resolve: {
      publicacion: PublicacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.publicacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PublicacionUpdateComponent,
    resolve: {
      publicacion: PublicacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.publicacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const publicacionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PublicacionDeletePopupComponent,
    resolve: {
      publicacion: PublicacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.publicacion.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

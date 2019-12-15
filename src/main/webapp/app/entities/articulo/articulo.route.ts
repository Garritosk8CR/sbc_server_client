import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Articulo } from 'app/shared/model/articulo.model';
import { ArticuloService } from './articulo.service';
import { ArticuloComponent } from './articulo.component';
import { ArticuloDetailComponent } from './articulo-detail.component';
import { ArticuloUpdateComponent } from './articulo-update.component';
import { ArticuloDeletePopupComponent } from './articulo-delete-dialog.component';
import { IArticulo } from 'app/shared/model/articulo.model';

@Injectable({ providedIn: 'root' })
export class ArticuloResolve implements Resolve<IArticulo> {
  constructor(private service: ArticuloService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IArticulo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Articulo>) => response.ok),
        map((articulo: HttpResponse<Articulo>) => articulo.body)
      );
    }
    return of(new Articulo());
  }
}

export const articuloRoute: Routes = [
  {
    path: '',
    component: ArticuloComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.articulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ArticuloDetailComponent,
    resolve: {
      articulo: ArticuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.articulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ArticuloUpdateComponent,
    resolve: {
      articulo: ArticuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.articulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ArticuloUpdateComponent,
    resolve: {
      articulo: ArticuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.articulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const articuloPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ArticuloDeletePopupComponent,
    resolve: {
      articulo: ArticuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.articulo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

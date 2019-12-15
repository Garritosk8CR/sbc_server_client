import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Categoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from './categoria.service';
import { CategoriaComponent } from './categoria.component';
import { CategoriaDetailComponent } from './categoria-detail.component';
import { CategoriaUpdateComponent } from './categoria-update.component';
import { CategoriaDeletePopupComponent } from './categoria-delete-dialog.component';
import { ICategoria } from 'app/shared/model/categoria.model';

@Injectable({ providedIn: 'root' })
export class CategoriaResolve implements Resolve<ICategoria> {
  constructor(private service: CategoriaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICategoria> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Categoria>) => response.ok),
        map((categoria: HttpResponse<Categoria>) => categoria.body)
      );
    }
    return of(new Categoria());
  }
}

export const categoriaRoute: Routes = [
  {
    path: '',
    component: CategoriaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.categoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategoriaDetailComponent,
    resolve: {
      categoria: CategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.categoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategoriaUpdateComponent,
    resolve: {
      categoria: CategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.categoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategoriaUpdateComponent,
    resolve: {
      categoria: CategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.categoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const categoriaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CategoriaDeletePopupComponent,
    resolve: {
      categoria: CategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.categoria.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

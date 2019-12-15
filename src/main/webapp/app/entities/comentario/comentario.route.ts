import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Comentario } from 'app/shared/model/comentario.model';
import { ComentarioService } from './comentario.service';
import { ComentarioComponent } from './comentario.component';
import { ComentarioDetailComponent } from './comentario-detail.component';
import { ComentarioUpdateComponent } from './comentario-update.component';
import { ComentarioDeletePopupComponent } from './comentario-delete-dialog.component';
import { IComentario } from 'app/shared/model/comentario.model';

@Injectable({ providedIn: 'root' })
export class ComentarioResolve implements Resolve<IComentario> {
  constructor(private service: ComentarioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IComentario> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Comentario>) => response.ok),
        map((comentario: HttpResponse<Comentario>) => comentario.body)
      );
    }
    return of(new Comentario());
  }
}

export const comentarioRoute: Routes = [
  {
    path: '',
    component: ComentarioComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.comentario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ComentarioDetailComponent,
    resolve: {
      comentario: ComentarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.comentario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ComentarioUpdateComponent,
    resolve: {
      comentario: ComentarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.comentario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ComentarioUpdateComponent,
    resolve: {
      comentario: ComentarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.comentario.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const comentarioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ComentarioDeletePopupComponent,
    resolve: {
      comentario: ComentarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.comentario.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

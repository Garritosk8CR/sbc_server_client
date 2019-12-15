import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Conversacion } from 'app/shared/model/conversacion.model';
import { ConversacionService } from './conversacion.service';
import { ConversacionComponent } from './conversacion.component';
import { ConversacionDetailComponent } from './conversacion-detail.component';
import { ConversacionUpdateComponent } from './conversacion-update.component';
import { ConversacionDeletePopupComponent } from './conversacion-delete-dialog.component';
import { IConversacion } from 'app/shared/model/conversacion.model';

@Injectable({ providedIn: 'root' })
export class ConversacionResolve implements Resolve<IConversacion> {
  constructor(private service: ConversacionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IConversacion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Conversacion>) => response.ok),
        map((conversacion: HttpResponse<Conversacion>) => conversacion.body)
      );
    }
    return of(new Conversacion());
  }
}

export const conversacionRoute: Routes = [
  {
    path: '',
    component: ConversacionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.conversacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ConversacionDetailComponent,
    resolve: {
      conversacion: ConversacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.conversacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ConversacionUpdateComponent,
    resolve: {
      conversacion: ConversacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.conversacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ConversacionUpdateComponent,
    resolve: {
      conversacion: ConversacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.conversacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const conversacionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ConversacionDeletePopupComponent,
    resolve: {
      conversacion: ConversacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.conversacion.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

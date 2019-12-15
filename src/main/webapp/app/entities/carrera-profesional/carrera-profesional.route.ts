import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CarreraProfesional } from 'app/shared/model/carrera-profesional.model';
import { CarreraProfesionalService } from './carrera-profesional.service';
import { CarreraProfesionalComponent } from './carrera-profesional.component';
import { CarreraProfesionalDetailComponent } from './carrera-profesional-detail.component';
import { CarreraProfesionalUpdateComponent } from './carrera-profesional-update.component';
import { CarreraProfesionalDeletePopupComponent } from './carrera-profesional-delete-dialog.component';
import { ICarreraProfesional } from 'app/shared/model/carrera-profesional.model';

@Injectable({ providedIn: 'root' })
export class CarreraProfesionalResolve implements Resolve<ICarreraProfesional> {
  constructor(private service: CarreraProfesionalService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICarreraProfesional> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CarreraProfesional>) => response.ok),
        map((carreraProfesional: HttpResponse<CarreraProfesional>) => carreraProfesional.body)
      );
    }
    return of(new CarreraProfesional());
  }
}

export const carreraProfesionalRoute: Routes = [
  {
    path: '',
    component: CarreraProfesionalComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.carreraProfesional.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CarreraProfesionalDetailComponent,
    resolve: {
      carreraProfesional: CarreraProfesionalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.carreraProfesional.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CarreraProfesionalUpdateComponent,
    resolve: {
      carreraProfesional: CarreraProfesionalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.carreraProfesional.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CarreraProfesionalUpdateComponent,
    resolve: {
      carreraProfesional: CarreraProfesionalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.carreraProfesional.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const carreraProfesionalPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CarreraProfesionalDeletePopupComponent,
    resolve: {
      carreraProfesional: CarreraProfesionalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbcAppApp.carreraProfesional.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

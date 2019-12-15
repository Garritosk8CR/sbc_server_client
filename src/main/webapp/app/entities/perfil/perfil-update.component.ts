import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPerfil, Perfil } from 'app/shared/model/perfil.model';
import { PerfilService } from './perfil.service';
import { ICarreraProfesional } from 'app/shared/model/carrera-profesional.model';
import { CarreraProfesionalService } from 'app/entities/carrera-profesional/carrera-profesional.service';
import { ISuscripcion } from 'app/shared/model/suscripcion.model';
import { SuscripcionService } from 'app/entities/suscripcion/suscripcion.service';

@Component({
  selector: 'jhi-perfil-update',
  templateUrl: './perfil-update.component.html'
})
export class PerfilUpdateComponent implements OnInit {
  isSaving: boolean;

  carreraprofesionals: ICarreraProfesional[];

  suscripcions: ISuscripcion[];
  fechaIngresoDp: any;
  fechaSalidaDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    primerApellido: [null, [Validators.required]],
    segundoApellido: [null, [Validators.required]],
    edad: [null, [Validators.required]],
    estadoCivil: [null, [Validators.required]],
    sexo: [null, [Validators.required]],
    telefonoCelular: [null, [Validators.required]],
    telefonoFijo: [null, [Validators.required]],
    correoElectronico: [null, [Validators.required]],
    direccion: [null, [Validators.required]],
    cedula: [null, [Validators.required]],
    fechaIngreso: [null, [Validators.required]],
    fechaSalida: [],
    foto: [null, [Validators.required]],
    carreraProfesionalId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected perfilService: PerfilService,
    protected carreraProfesionalService: CarreraProfesionalService,
    protected suscripcionService: SuscripcionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ perfil }) => {
      this.updateForm(perfil);
    });
    this.carreraProfesionalService
      .query({ filter: 'perfil-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICarreraProfesional[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICarreraProfesional[]>) => response.body)
      )
      .subscribe(
        (res: ICarreraProfesional[]) => {
          if (!this.editForm.get('carreraProfesionalId').value) {
            this.carreraprofesionals = res;
          } else {
            this.carreraProfesionalService
              .find(this.editForm.get('carreraProfesionalId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ICarreraProfesional>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ICarreraProfesional>) => subResponse.body)
              )
              .subscribe(
                (subRes: ICarreraProfesional) => (this.carreraprofesionals = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.suscripcionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISuscripcion[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISuscripcion[]>) => response.body)
      )
      .subscribe((res: ISuscripcion[]) => (this.suscripcions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(perfil: IPerfil) {
    this.editForm.patchValue({
      id: perfil.id,
      nombre: perfil.nombre,
      primerApellido: perfil.primerApellido,
      segundoApellido: perfil.segundoApellido,
      edad: perfil.edad,
      estadoCivil: perfil.estadoCivil,
      sexo: perfil.sexo,
      telefonoCelular: perfil.telefonoCelular,
      telefonoFijo: perfil.telefonoFijo,
      correoElectronico: perfil.correoElectronico,
      direccion: perfil.direccion,
      cedula: perfil.cedula,
      fechaIngreso: perfil.fechaIngreso,
      fechaSalida: perfil.fechaSalida,
      foto: perfil.foto,
      carreraProfesionalId: perfil.carreraProfesionalId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const perfil = this.createFromForm();
    if (perfil.id !== undefined) {
      this.subscribeToSaveResponse(this.perfilService.update(perfil));
    } else {
      this.subscribeToSaveResponse(this.perfilService.create(perfil));
    }
  }

  private createFromForm(): IPerfil {
    return {
      ...new Perfil(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      primerApellido: this.editForm.get(['primerApellido']).value,
      segundoApellido: this.editForm.get(['segundoApellido']).value,
      edad: this.editForm.get(['edad']).value,
      estadoCivil: this.editForm.get(['estadoCivil']).value,
      sexo: this.editForm.get(['sexo']).value,
      telefonoCelular: this.editForm.get(['telefonoCelular']).value,
      telefonoFijo: this.editForm.get(['telefonoFijo']).value,
      correoElectronico: this.editForm.get(['correoElectronico']).value,
      direccion: this.editForm.get(['direccion']).value,
      cedula: this.editForm.get(['cedula']).value,
      fechaIngreso: this.editForm.get(['fechaIngreso']).value,
      fechaSalida: this.editForm.get(['fechaSalida']).value,
      foto: this.editForm.get(['foto']).value,
      carreraProfesionalId: this.editForm.get(['carreraProfesionalId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfil>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCarreraProfesionalById(index: number, item: ICarreraProfesional) {
    return item.id;
  }

  trackSuscripcionById(index: number, item: ISuscripcion) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}

import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPuestoDeTrabajo, PuestoDeTrabajo } from 'app/shared/model/puesto-de-trabajo.model';
import { PuestoDeTrabajoService } from './puesto-de-trabajo.service';
import { ICarreraProfesional } from 'app/shared/model/carrera-profesional.model';
import { CarreraProfesionalService } from 'app/entities/carrera-profesional/carrera-profesional.service';
import { IRequisito } from 'app/shared/model/requisito.model';
import { RequisitoService } from 'app/entities/requisito/requisito.service';

@Component({
  selector: 'jhi-puesto-de-trabajo-update',
  templateUrl: './puesto-de-trabajo-update.component.html'
})
export class PuestoDeTrabajoUpdateComponent implements OnInit {
  isSaving: boolean;

  carreraprofesionals: ICarreraProfesional[];

  requisitos: IRequisito[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    carreraProfesionalId: [],
    requerimientos: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected puestoDeTrabajoService: PuestoDeTrabajoService,
    protected carreraProfesionalService: CarreraProfesionalService,
    protected requisitoService: RequisitoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ puestoDeTrabajo }) => {
      this.updateForm(puestoDeTrabajo);
    });
    this.carreraProfesionalService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICarreraProfesional[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICarreraProfesional[]>) => response.body)
      )
      .subscribe((res: ICarreraProfesional[]) => (this.carreraprofesionals = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.requisitoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRequisito[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRequisito[]>) => response.body)
      )
      .subscribe((res: IRequisito[]) => (this.requisitos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(puestoDeTrabajo: IPuestoDeTrabajo) {
    this.editForm.patchValue({
      id: puestoDeTrabajo.id,
      nombre: puestoDeTrabajo.nombre,
      descripcion: puestoDeTrabajo.descripcion,
      carreraProfesionalId: puestoDeTrabajo.carreraProfesionalId,
      requerimientos: puestoDeTrabajo.requerimientos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const puestoDeTrabajo = this.createFromForm();
    if (puestoDeTrabajo.id !== undefined) {
      this.subscribeToSaveResponse(this.puestoDeTrabajoService.update(puestoDeTrabajo));
    } else {
      this.subscribeToSaveResponse(this.puestoDeTrabajoService.create(puestoDeTrabajo));
    }
  }

  private createFromForm(): IPuestoDeTrabajo {
    return {
      ...new PuestoDeTrabajo(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      carreraProfesionalId: this.editForm.get(['carreraProfesionalId']).value,
      requerimientos: this.editForm.get(['requerimientos']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPuestoDeTrabajo>>) {
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

  trackRequisitoById(index: number, item: IRequisito) {
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

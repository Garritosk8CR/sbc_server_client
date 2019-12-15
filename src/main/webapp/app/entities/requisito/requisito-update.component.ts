import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRequisito, Requisito } from 'app/shared/model/requisito.model';
import { RequisitoService } from './requisito.service';
import { IPuestoDeTrabajo } from 'app/shared/model/puesto-de-trabajo.model';
import { PuestoDeTrabajoService } from 'app/entities/puesto-de-trabajo/puesto-de-trabajo.service';

@Component({
  selector: 'jhi-requisito-update',
  templateUrl: './requisito-update.component.html'
})
export class RequisitoUpdateComponent implements OnInit {
  isSaving: boolean;

  puestodetrabajos: IPuestoDeTrabajo[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    tipo: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected requisitoService: RequisitoService,
    protected puestoDeTrabajoService: PuestoDeTrabajoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ requisito }) => {
      this.updateForm(requisito);
    });
    this.puestoDeTrabajoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPuestoDeTrabajo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPuestoDeTrabajo[]>) => response.body)
      )
      .subscribe((res: IPuestoDeTrabajo[]) => (this.puestodetrabajos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(requisito: IRequisito) {
    this.editForm.patchValue({
      id: requisito.id,
      nombre: requisito.nombre,
      descripcion: requisito.descripcion,
      tipo: requisito.tipo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const requisito = this.createFromForm();
    if (requisito.id !== undefined) {
      this.subscribeToSaveResponse(this.requisitoService.update(requisito));
    } else {
      this.subscribeToSaveResponse(this.requisitoService.create(requisito));
    }
  }

  private createFromForm(): IRequisito {
    return {
      ...new Requisito(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      tipo: this.editForm.get(['tipo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequisito>>) {
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

  trackPuestoDeTrabajoById(index: number, item: IPuestoDeTrabajo) {
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

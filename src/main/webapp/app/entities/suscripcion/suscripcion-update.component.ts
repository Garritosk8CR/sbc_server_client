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
import { ISuscripcion, Suscripcion } from 'app/shared/model/suscripcion.model';
import { SuscripcionService } from './suscripcion.service';
import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from 'app/entities/perfil/perfil.service';

@Component({
  selector: 'jhi-suscripcion-update',
  templateUrl: './suscripcion-update.component.html'
})
export class SuscripcionUpdateComponent implements OnInit {
  isSaving: boolean;

  perfils: IPerfil[];
  fechaSuscripcionDp: any;

  editForm = this.fb.group({
    id: [],
    fechaSuscripcion: [],
    estadoSuscripcion: [],
    suscriptors: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected suscripcionService: SuscripcionService,
    protected perfilService: PerfilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ suscripcion }) => {
      this.updateForm(suscripcion);
    });
    this.perfilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerfil[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerfil[]>) => response.body)
      )
      .subscribe((res: IPerfil[]) => (this.perfils = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(suscripcion: ISuscripcion) {
    this.editForm.patchValue({
      id: suscripcion.id,
      fechaSuscripcion: suscripcion.fechaSuscripcion,
      estadoSuscripcion: suscripcion.estadoSuscripcion,
      suscriptors: suscripcion.suscriptors
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const suscripcion = this.createFromForm();
    if (suscripcion.id !== undefined) {
      this.subscribeToSaveResponse(this.suscripcionService.update(suscripcion));
    } else {
      this.subscribeToSaveResponse(this.suscripcionService.create(suscripcion));
    }
  }

  private createFromForm(): ISuscripcion {
    return {
      ...new Suscripcion(),
      id: this.editForm.get(['id']).value,
      fechaSuscripcion: this.editForm.get(['fechaSuscripcion']).value,
      estadoSuscripcion: this.editForm.get(['estadoSuscripcion']).value,
      suscriptors: this.editForm.get(['suscriptors']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISuscripcion>>) {
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

  trackPerfilById(index: number, item: IPerfil) {
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

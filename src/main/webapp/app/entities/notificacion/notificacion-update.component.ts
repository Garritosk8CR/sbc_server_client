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
import { INotificacion, Notificacion } from 'app/shared/model/notificacion.model';
import { NotificacionService } from './notificacion.service';
import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from 'app/entities/perfil/perfil.service';

@Component({
  selector: 'jhi-notificacion-update',
  templateUrl: './notificacion-update.component.html'
})
export class NotificacionUpdateComponent implements OnInit {
  isSaving: boolean;

  perfils: IPerfil[];
  fechaEmisionDp: any;

  editForm = this.fb.group({
    id: [],
    origen: [],
    destino: [],
    tipo: [],
    fechaEmision: [],
    estadoMensaje: [],
    perfilId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected notificacionService: NotificacionService,
    protected perfilService: PerfilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ notificacion }) => {
      this.updateForm(notificacion);
    });
    this.perfilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerfil[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerfil[]>) => response.body)
      )
      .subscribe((res: IPerfil[]) => (this.perfils = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(notificacion: INotificacion) {
    this.editForm.patchValue({
      id: notificacion.id,
      origen: notificacion.origen,
      destino: notificacion.destino,
      tipo: notificacion.tipo,
      fechaEmision: notificacion.fechaEmision,
      estadoMensaje: notificacion.estadoMensaje,
      perfilId: notificacion.perfilId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const notificacion = this.createFromForm();
    if (notificacion.id !== undefined) {
      this.subscribeToSaveResponse(this.notificacionService.update(notificacion));
    } else {
      this.subscribeToSaveResponse(this.notificacionService.create(notificacion));
    }
  }

  private createFromForm(): INotificacion {
    return {
      ...new Notificacion(),
      id: this.editForm.get(['id']).value,
      origen: this.editForm.get(['origen']).value,
      destino: this.editForm.get(['destino']).value,
      tipo: this.editForm.get(['tipo']).value,
      fechaEmision: this.editForm.get(['fechaEmision']).value,
      estadoMensaje: this.editForm.get(['estadoMensaje']).value,
      perfilId: this.editForm.get(['perfilId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotificacion>>) {
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
}

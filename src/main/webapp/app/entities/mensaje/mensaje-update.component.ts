import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMensaje, Mensaje } from 'app/shared/model/mensaje.model';
import { MensajeService } from './mensaje.service';
import { IConversacion } from 'app/shared/model/conversacion.model';
import { ConversacionService } from 'app/entities/conversacion/conversacion.service';

@Component({
  selector: 'jhi-mensaje-update',
  templateUrl: './mensaje-update.component.html'
})
export class MensajeUpdateComponent implements OnInit {
  isSaving: boolean;

  conversacions: IConversacion[];

  editForm = this.fb.group({
    id: [],
    mensaje: [null, [Validators.required]],
    fechaEmision: [null, [Validators.required]],
    estadoMensaje: [],
    conversacionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected mensajeService: MensajeService,
    protected conversacionService: ConversacionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ mensaje }) => {
      this.updateForm(mensaje);
    });
    this.conversacionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IConversacion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IConversacion[]>) => response.body)
      )
      .subscribe((res: IConversacion[]) => (this.conversacions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(mensaje: IMensaje) {
    this.editForm.patchValue({
      id: mensaje.id,
      mensaje: mensaje.mensaje,
      fechaEmision: mensaje.fechaEmision,
      estadoMensaje: mensaje.estadoMensaje,
      conversacionId: mensaje.conversacionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const mensaje = this.createFromForm();
    if (mensaje.id !== undefined) {
      this.subscribeToSaveResponse(this.mensajeService.update(mensaje));
    } else {
      this.subscribeToSaveResponse(this.mensajeService.create(mensaje));
    }
  }

  private createFromForm(): IMensaje {
    return {
      ...new Mensaje(),
      id: this.editForm.get(['id']).value,
      mensaje: this.editForm.get(['mensaje']).value,
      fechaEmision: this.editForm.get(['fechaEmision']).value,
      estadoMensaje: this.editForm.get(['estadoMensaje']).value,
      conversacionId: this.editForm.get(['conversacionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMensaje>>) {
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

  trackConversacionById(index: number, item: IConversacion) {
    return item.id;
  }
}

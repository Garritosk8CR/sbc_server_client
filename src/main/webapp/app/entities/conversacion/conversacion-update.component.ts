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
import { IConversacion, Conversacion } from 'app/shared/model/conversacion.model';
import { ConversacionService } from './conversacion.service';
import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from 'app/entities/perfil/perfil.service';

@Component({
  selector: 'jhi-conversacion-update',
  templateUrl: './conversacion-update.component.html'
})
export class ConversacionUpdateComponent implements OnInit {
  isSaving: boolean;

  perfils: IPerfil[];
  fechaDeConversacionDp: any;

  editForm = this.fb.group({
    id: [],
    usuario1: [null, [Validators.required]],
    usuario2: [null, [Validators.required]],
    fechaDeConversacion: [null, [Validators.required]],
    perfilId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected conversacionService: ConversacionService,
    protected perfilService: PerfilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ conversacion }) => {
      this.updateForm(conversacion);
    });
    this.perfilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerfil[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerfil[]>) => response.body)
      )
      .subscribe((res: IPerfil[]) => (this.perfils = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(conversacion: IConversacion) {
    this.editForm.patchValue({
      id: conversacion.id,
      usuario1: conversacion.usuario1,
      usuario2: conversacion.usuario2,
      fechaDeConversacion: conversacion.fechaDeConversacion,
      perfilId: conversacion.perfilId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const conversacion = this.createFromForm();
    if (conversacion.id !== undefined) {
      this.subscribeToSaveResponse(this.conversacionService.update(conversacion));
    } else {
      this.subscribeToSaveResponse(this.conversacionService.create(conversacion));
    }
  }

  private createFromForm(): IConversacion {
    return {
      ...new Conversacion(),
      id: this.editForm.get(['id']).value,
      usuario1: this.editForm.get(['usuario1']).value,
      usuario2: this.editForm.get(['usuario2']).value,
      fechaDeConversacion: this.editForm.get(['fechaDeConversacion']).value,
      perfilId: this.editForm.get(['perfilId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConversacion>>) {
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

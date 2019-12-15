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
import { ITarea, Tarea } from 'app/shared/model/tarea.model';
import { TareaService } from './tarea.service';
import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';
import { HistoriaUsuarioService } from 'app/entities/historia-usuario/historia-usuario.service';

@Component({
  selector: 'jhi-tarea-update',
  templateUrl: './tarea-update.component.html'
})
export class TareaUpdateComponent implements OnInit {
  isSaving: boolean;

  historiausuarios: IHistoriaUsuario[];
  fechaCreacionDp: any;
  fechaConclucionDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    fechaCreacion: [null, [Validators.required]],
    fechaConclucion: [],
    estadoTarea: [],
    historiaUsuarioId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tareaService: TareaService,
    protected historiaUsuarioService: HistoriaUsuarioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tarea }) => {
      this.updateForm(tarea);
    });
    this.historiaUsuarioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IHistoriaUsuario[]>) => mayBeOk.ok),
        map((response: HttpResponse<IHistoriaUsuario[]>) => response.body)
      )
      .subscribe((res: IHistoriaUsuario[]) => (this.historiausuarios = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tarea: ITarea) {
    this.editForm.patchValue({
      id: tarea.id,
      nombre: tarea.nombre,
      descripcion: tarea.descripcion,
      fechaCreacion: tarea.fechaCreacion,
      fechaConclucion: tarea.fechaConclucion,
      estadoTarea: tarea.estadoTarea,
      historiaUsuarioId: tarea.historiaUsuarioId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tarea = this.createFromForm();
    if (tarea.id !== undefined) {
      this.subscribeToSaveResponse(this.tareaService.update(tarea));
    } else {
      this.subscribeToSaveResponse(this.tareaService.create(tarea));
    }
  }

  private createFromForm(): ITarea {
    return {
      ...new Tarea(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      fechaCreacion: this.editForm.get(['fechaCreacion']).value,
      fechaConclucion: this.editForm.get(['fechaConclucion']).value,
      estadoTarea: this.editForm.get(['estadoTarea']).value,
      historiaUsuarioId: this.editForm.get(['historiaUsuarioId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarea>>) {
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

  trackHistoriaUsuarioById(index: number, item: IHistoriaUsuario) {
    return item.id;
  }
}

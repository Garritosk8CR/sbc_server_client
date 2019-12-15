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
import { IHistoriaUsuario, HistoriaUsuario } from 'app/shared/model/historia-usuario.model';
import { HistoriaUsuarioService } from './historia-usuario.service';
import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from 'app/entities/perfil/perfil.service';

@Component({
  selector: 'jhi-historia-usuario-update',
  templateUrl: './historia-usuario-update.component.html'
})
export class HistoriaUsuarioUpdateComponent implements OnInit {
  isSaving: boolean;

  perfils: IPerfil[];
  fechaCreacionDp: any;
  fechaConclucionDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    fechaCreacion: [null, [Validators.required]],
    fechaConclucion: [],
    sprint: [],
    isEpic: [],
    perfilId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected historiaUsuarioService: HistoriaUsuarioService,
    protected perfilService: PerfilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ historiaUsuario }) => {
      this.updateForm(historiaUsuario);
    });
    this.perfilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerfil[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerfil[]>) => response.body)
      )
      .subscribe((res: IPerfil[]) => (this.perfils = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(historiaUsuario: IHistoriaUsuario) {
    this.editForm.patchValue({
      id: historiaUsuario.id,
      nombre: historiaUsuario.nombre,
      descripcion: historiaUsuario.descripcion,
      fechaCreacion: historiaUsuario.fechaCreacion,
      fechaConclucion: historiaUsuario.fechaConclucion,
      sprint: historiaUsuario.sprint,
      isEpic: historiaUsuario.isEpic,
      perfilId: historiaUsuario.perfilId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const historiaUsuario = this.createFromForm();
    if (historiaUsuario.id !== undefined) {
      this.subscribeToSaveResponse(this.historiaUsuarioService.update(historiaUsuario));
    } else {
      this.subscribeToSaveResponse(this.historiaUsuarioService.create(historiaUsuario));
    }
  }

  private createFromForm(): IHistoriaUsuario {
    return {
      ...new HistoriaUsuario(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      fechaCreacion: this.editForm.get(['fechaCreacion']).value,
      fechaConclucion: this.editForm.get(['fechaConclucion']).value,
      sprint: this.editForm.get(['sprint']).value,
      isEpic: this.editForm.get(['isEpic']).value,
      perfilId: this.editForm.get(['perfilId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistoriaUsuario>>) {
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

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
import { IComentario, Comentario } from 'app/shared/model/comentario.model';
import { ComentarioService } from './comentario.service';
import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from 'app/entities/perfil/perfil.service';
import { ITarea } from 'app/shared/model/tarea.model';
import { TareaService } from 'app/entities/tarea/tarea.service';
import { IArticulo } from 'app/shared/model/articulo.model';
import { ArticuloService } from 'app/entities/articulo/articulo.service';
import { IPublicacion } from 'app/shared/model/publicacion.model';
import { PublicacionService } from 'app/entities/publicacion/publicacion.service';

@Component({
  selector: 'jhi-comentario-update',
  templateUrl: './comentario-update.component.html'
})
export class ComentarioUpdateComponent implements OnInit {
  isSaving: boolean;

  perfils: IPerfil[];

  tareas: ITarea[];

  articulos: IArticulo[];

  publicacions: IPublicacion[];
  fechaCreacionDp: any;

  editForm = this.fb.group({
    id: [],
    autor: [null, [Validators.required]],
    avatar: [null, [Validators.required]],
    fechaCreacion: [null, [Validators.required]],
    contenido: [null, [Validators.required]],
    perfilId: [],
    tareaId: [],
    articuloId: [],
    publicacionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected comentarioService: ComentarioService,
    protected perfilService: PerfilService,
    protected tareaService: TareaService,
    protected articuloService: ArticuloService,
    protected publicacionService: PublicacionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ comentario }) => {
      this.updateForm(comentario);
    });
    this.perfilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerfil[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerfil[]>) => response.body)
      )
      .subscribe((res: IPerfil[]) => (this.perfils = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tareaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITarea[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITarea[]>) => response.body)
      )
      .subscribe((res: ITarea[]) => (this.tareas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.articuloService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IArticulo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IArticulo[]>) => response.body)
      )
      .subscribe((res: IArticulo[]) => (this.articulos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.publicacionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPublicacion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPublicacion[]>) => response.body)
      )
      .subscribe((res: IPublicacion[]) => (this.publicacions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(comentario: IComentario) {
    this.editForm.patchValue({
      id: comentario.id,
      autor: comentario.autor,
      avatar: comentario.avatar,
      fechaCreacion: comentario.fechaCreacion,
      contenido: comentario.contenido,
      perfilId: comentario.perfilId,
      tareaId: comentario.tareaId,
      articuloId: comentario.articuloId,
      publicacionId: comentario.publicacionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const comentario = this.createFromForm();
    if (comentario.id !== undefined) {
      this.subscribeToSaveResponse(this.comentarioService.update(comentario));
    } else {
      this.subscribeToSaveResponse(this.comentarioService.create(comentario));
    }
  }

  private createFromForm(): IComentario {
    return {
      ...new Comentario(),
      id: this.editForm.get(['id']).value,
      autor: this.editForm.get(['autor']).value,
      avatar: this.editForm.get(['avatar']).value,
      fechaCreacion: this.editForm.get(['fechaCreacion']).value,
      contenido: this.editForm.get(['contenido']).value,
      perfilId: this.editForm.get(['perfilId']).value,
      tareaId: this.editForm.get(['tareaId']).value,
      articuloId: this.editForm.get(['articuloId']).value,
      publicacionId: this.editForm.get(['publicacionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComentario>>) {
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

  trackTareaById(index: number, item: ITarea) {
    return item.id;
  }

  trackArticuloById(index: number, item: IArticulo) {
    return item.id;
  }

  trackPublicacionById(index: number, item: IPublicacion) {
    return item.id;
  }
}

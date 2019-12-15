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
import { IArticulo, Articulo } from 'app/shared/model/articulo.model';
import { ArticuloService } from './articulo.service';
import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from 'app/entities/perfil/perfil.service';
import { ICurso } from 'app/shared/model/curso.model';
import { CursoService } from 'app/entities/curso/curso.service';

@Component({
  selector: 'jhi-articulo-update',
  templateUrl: './articulo-update.component.html'
})
export class ArticuloUpdateComponent implements OnInit {
  isSaving: boolean;

  perfils: IPerfil[];

  cursos: ICurso[];
  fechaCreacionDp: any;

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required]],
    contenido: [null, [Validators.required]],
    fechaCreacion: [null, [Validators.required]],
    perfilId: [],
    cursoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected articuloService: ArticuloService,
    protected perfilService: PerfilService,
    protected cursoService: CursoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ articulo }) => {
      this.updateForm(articulo);
    });
    this.perfilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerfil[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerfil[]>) => response.body)
      )
      .subscribe((res: IPerfil[]) => (this.perfils = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.cursoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICurso[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICurso[]>) => response.body)
      )
      .subscribe((res: ICurso[]) => (this.cursos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(articulo: IArticulo) {
    this.editForm.patchValue({
      id: articulo.id,
      titulo: articulo.titulo,
      contenido: articulo.contenido,
      fechaCreacion: articulo.fechaCreacion,
      perfilId: articulo.perfilId,
      cursoId: articulo.cursoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const articulo = this.createFromForm();
    if (articulo.id !== undefined) {
      this.subscribeToSaveResponse(this.articuloService.update(articulo));
    } else {
      this.subscribeToSaveResponse(this.articuloService.create(articulo));
    }
  }

  private createFromForm(): IArticulo {
    return {
      ...new Articulo(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      contenido: this.editForm.get(['contenido']).value,
      fechaCreacion: this.editForm.get(['fechaCreacion']).value,
      perfilId: this.editForm.get(['perfilId']).value,
      cursoId: this.editForm.get(['cursoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArticulo>>) {
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

  trackCursoById(index: number, item: ICurso) {
    return item.id;
  }
}

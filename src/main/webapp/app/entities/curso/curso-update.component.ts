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
import { ICurso, Curso } from 'app/shared/model/curso.model';
import { CursoService } from './curso.service';
import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from 'app/entities/perfil/perfil.service';

@Component({
  selector: 'jhi-curso-update',
  templateUrl: './curso-update.component.html'
})
export class CursoUpdateComponent implements OnInit {
  isSaving: boolean;

  perfils: IPerfil[];
  fechaActualizacionDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    slug: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    categoria: [null, [Validators.required]],
    duracion: [null, [Validators.required]],
    totalDeArticulos: [null, [Validators.required]],
    fechaActualizacion: [null, [Validators.required]],
    perfilId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cursoService: CursoService,
    protected perfilService: PerfilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ curso }) => {
      this.updateForm(curso);
    });
    this.perfilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerfil[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerfil[]>) => response.body)
      )
      .subscribe((res: IPerfil[]) => (this.perfils = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(curso: ICurso) {
    this.editForm.patchValue({
      id: curso.id,
      nombre: curso.nombre,
      slug: curso.slug,
      descripcion: curso.descripcion,
      categoria: curso.categoria,
      duracion: curso.duracion,
      totalDeArticulos: curso.totalDeArticulos,
      fechaActualizacion: curso.fechaActualizacion,
      perfilId: curso.perfilId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const curso = this.createFromForm();
    if (curso.id !== undefined) {
      this.subscribeToSaveResponse(this.cursoService.update(curso));
    } else {
      this.subscribeToSaveResponse(this.cursoService.create(curso));
    }
  }

  private createFromForm(): ICurso {
    return {
      ...new Curso(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      slug: this.editForm.get(['slug']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      categoria: this.editForm.get(['categoria']).value,
      duracion: this.editForm.get(['duracion']).value,
      totalDeArticulos: this.editForm.get(['totalDeArticulos']).value,
      fechaActualizacion: this.editForm.get(['fechaActualizacion']).value,
      perfilId: this.editForm.get(['perfilId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurso>>) {
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

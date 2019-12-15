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
import { IPublicacion, Publicacion } from 'app/shared/model/publicacion.model';
import { PublicacionService } from './publicacion.service';
import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from 'app/entities/perfil/perfil.service';

@Component({
  selector: 'jhi-publicacion-update',
  templateUrl: './publicacion-update.component.html'
})
export class PublicacionUpdateComponent implements OnInit {
  isSaving: boolean;

  perfils: IPerfil[];
  fechaPublicacionDp: any;

  editForm = this.fb.group({
    id: [],
    fechaPublicacion: [],
    contenido: [null, [Validators.required]],
    tipoPublicacion: [],
    perfilId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected publicacionService: PublicacionService,
    protected perfilService: PerfilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ publicacion }) => {
      this.updateForm(publicacion);
    });
    this.perfilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerfil[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerfil[]>) => response.body)
      )
      .subscribe((res: IPerfil[]) => (this.perfils = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(publicacion: IPublicacion) {
    this.editForm.patchValue({
      id: publicacion.id,
      fechaPublicacion: publicacion.fechaPublicacion,
      contenido: publicacion.contenido,
      tipoPublicacion: publicacion.tipoPublicacion,
      perfilId: publicacion.perfilId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const publicacion = this.createFromForm();
    if (publicacion.id !== undefined) {
      this.subscribeToSaveResponse(this.publicacionService.update(publicacion));
    } else {
      this.subscribeToSaveResponse(this.publicacionService.create(publicacion));
    }
  }

  private createFromForm(): IPublicacion {
    return {
      ...new Publicacion(),
      id: this.editForm.get(['id']).value,
      fechaPublicacion: this.editForm.get(['fechaPublicacion']).value,
      contenido: this.editForm.get(['contenido']).value,
      tipoPublicacion: this.editForm.get(['tipoPublicacion']).value,
      perfilId: this.editForm.get(['perfilId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPublicacion>>) {
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

import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICarreraProfesional, CarreraProfesional } from 'app/shared/model/carrera-profesional.model';
import { CarreraProfesionalService } from './carrera-profesional.service';

@Component({
  selector: 'jhi-carrera-profesional-update',
  templateUrl: './carrera-profesional-update.component.html'
})
export class CarreraProfesionalUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    descripcion: [null, [Validators.required]]
  });

  constructor(
    protected carreraProfesionalService: CarreraProfesionalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ carreraProfesional }) => {
      this.updateForm(carreraProfesional);
    });
  }

  updateForm(carreraProfesional: ICarreraProfesional) {
    this.editForm.patchValue({
      id: carreraProfesional.id,
      nombre: carreraProfesional.nombre,
      descripcion: carreraProfesional.descripcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const carreraProfesional = this.createFromForm();
    if (carreraProfesional.id !== undefined) {
      this.subscribeToSaveResponse(this.carreraProfesionalService.update(carreraProfesional));
    } else {
      this.subscribeToSaveResponse(this.carreraProfesionalService.create(carreraProfesional));
    }
  }

  private createFromForm(): ICarreraProfesional {
    return {
      ...new CarreraProfesional(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarreraProfesional>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

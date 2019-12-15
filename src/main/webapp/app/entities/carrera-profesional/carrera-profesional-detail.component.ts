import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarreraProfesional } from 'app/shared/model/carrera-profesional.model';

@Component({
  selector: 'jhi-carrera-profesional-detail',
  templateUrl: './carrera-profesional-detail.component.html'
})
export class CarreraProfesionalDetailComponent implements OnInit {
  carreraProfesional: ICarreraProfesional;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ carreraProfesional }) => {
      this.carreraProfesional = carreraProfesional;
    });
  }

  previousState() {
    window.history.back();
  }
}

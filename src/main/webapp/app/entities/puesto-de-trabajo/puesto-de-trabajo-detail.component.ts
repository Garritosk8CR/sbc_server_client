import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPuestoDeTrabajo } from 'app/shared/model/puesto-de-trabajo.model';

@Component({
  selector: 'jhi-puesto-de-trabajo-detail',
  templateUrl: './puesto-de-trabajo-detail.component.html'
})
export class PuestoDeTrabajoDetailComponent implements OnInit {
  puestoDeTrabajo: IPuestoDeTrabajo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ puestoDeTrabajo }) => {
      this.puestoDeTrabajo = puestoDeTrabajo;
    });
  }

  previousState() {
    window.history.back();
  }
}

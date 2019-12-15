import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISuscripcion } from 'app/shared/model/suscripcion.model';

@Component({
  selector: 'jhi-suscripcion-detail',
  templateUrl: './suscripcion-detail.component.html'
})
export class SuscripcionDetailComponent implements OnInit {
  suscripcion: ISuscripcion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ suscripcion }) => {
      this.suscripcion = suscripcion;
    });
  }

  previousState() {
    window.history.back();
  }
}

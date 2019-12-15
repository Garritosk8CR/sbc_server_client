import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMensaje } from 'app/shared/model/mensaje.model';

@Component({
  selector: 'jhi-mensaje-detail',
  templateUrl: './mensaje-detail.component.html'
})
export class MensajeDetailComponent implements OnInit {
  mensaje: IMensaje;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ mensaje }) => {
      this.mensaje = mensaje;
    });
  }

  previousState() {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INotificacion } from 'app/shared/model/notificacion.model';

@Component({
  selector: 'jhi-notificacion-detail',
  templateUrl: './notificacion-detail.component.html'
})
export class NotificacionDetailComponent implements OnInit {
  notificacion: INotificacion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ notificacion }) => {
      this.notificacion = notificacion;
    });
  }

  previousState() {
    window.history.back();
  }
}

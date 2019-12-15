import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConversacion } from 'app/shared/model/conversacion.model';

@Component({
  selector: 'jhi-conversacion-detail',
  templateUrl: './conversacion-detail.component.html'
})
export class ConversacionDetailComponent implements OnInit {
  conversacion: IConversacion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ conversacion }) => {
      this.conversacion = conversacion;
    });
  }

  previousState() {
    window.history.back();
  }
}

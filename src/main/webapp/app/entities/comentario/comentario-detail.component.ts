import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComentario } from 'app/shared/model/comentario.model';

@Component({
  selector: 'jhi-comentario-detail',
  templateUrl: './comentario-detail.component.html'
})
export class ComentarioDetailComponent implements OnInit {
  comentario: IComentario;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ comentario }) => {
      this.comentario = comentario;
    });
  }

  previousState() {
    window.history.back();
  }
}

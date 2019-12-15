import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPublicacion } from 'app/shared/model/publicacion.model';

@Component({
  selector: 'jhi-publicacion-detail',
  templateUrl: './publicacion-detail.component.html'
})
export class PublicacionDetailComponent implements OnInit {
  publicacion: IPublicacion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ publicacion }) => {
      this.publicacion = publicacion;
    });
  }

  previousState() {
    window.history.back();
  }
}

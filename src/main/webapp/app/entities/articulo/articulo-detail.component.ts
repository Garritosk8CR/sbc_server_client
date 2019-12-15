import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArticulo } from 'app/shared/model/articulo.model';

@Component({
  selector: 'jhi-articulo-detail',
  templateUrl: './articulo-detail.component.html'
})
export class ArticuloDetailComponent implements OnInit {
  articulo: IArticulo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ articulo }) => {
      this.articulo = articulo;
    });
  }

  previousState() {
    window.history.back();
  }
}

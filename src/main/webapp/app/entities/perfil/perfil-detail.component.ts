import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPerfil } from 'app/shared/model/perfil.model';

@Component({
  selector: 'jhi-perfil-detail',
  templateUrl: './perfil-detail.component.html'
})
export class PerfilDetailComponent implements OnInit {
  perfil: IPerfil;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ perfil }) => {
      this.perfil = perfil;
    });
  }

  previousState() {
    window.history.back();
  }
}

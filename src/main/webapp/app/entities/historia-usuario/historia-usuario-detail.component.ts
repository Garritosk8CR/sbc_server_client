import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';

@Component({
  selector: 'jhi-historia-usuario-detail',
  templateUrl: './historia-usuario-detail.component.html'
})
export class HistoriaUsuarioDetailComponent implements OnInit {
  historiaUsuario: IHistoriaUsuario;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ historiaUsuario }) => {
      this.historiaUsuario = historiaUsuario;
    });
  }

  previousState() {
    window.history.back();
  }
}

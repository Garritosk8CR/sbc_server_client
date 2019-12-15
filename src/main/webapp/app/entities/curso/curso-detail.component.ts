import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICurso } from 'app/shared/model/curso.model';

@Component({
  selector: 'jhi-curso-detail',
  templateUrl: './curso-detail.component.html'
})
export class CursoDetailComponent implements OnInit {
  curso: ICurso;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ curso }) => {
      this.curso = curso;
    });
  }

  previousState() {
    window.history.back();
  }
}

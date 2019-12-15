import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequisito } from 'app/shared/model/requisito.model';

@Component({
  selector: 'jhi-requisito-detail',
  templateUrl: './requisito-detail.component.html'
})
export class RequisitoDetailComponent implements OnInit {
  requisito: IRequisito;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ requisito }) => {
      this.requisito = requisito;
    });
  }

  previousState() {
    window.history.back();
  }
}

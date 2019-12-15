import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITarea } from 'app/shared/model/tarea.model';

@Component({
  selector: 'jhi-tarea-detail',
  templateUrl: './tarea-detail.component.html'
})
export class TareaDetailComponent implements OnInit {
  tarea: ITarea;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tarea }) => {
      this.tarea = tarea;
    });
  }

  previousState() {
    window.history.back();
  }
}

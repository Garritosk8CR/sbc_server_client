import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICurso } from 'app/shared/model/curso.model';
import { AccountService } from 'app/core/auth/account.service';
import { CursoService } from './curso.service';

@Component({
  selector: 'jhi-curso',
  templateUrl: './curso.component.html'
})
export class CursoComponent implements OnInit, OnDestroy {
  cursos: ICurso[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected cursoService: CursoService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.cursoService
      .query()
      .pipe(
        filter((res: HttpResponse<ICurso[]>) => res.ok),
        map((res: HttpResponse<ICurso[]>) => res.body)
      )
      .subscribe((res: ICurso[]) => {
        this.cursos = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCursos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICurso) {
    return item.id;
  }

  registerChangeInCursos() {
    this.eventSubscriber = this.eventManager.subscribe('cursoListModification', response => this.loadAll());
  }
}

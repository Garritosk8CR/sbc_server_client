import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPuestoDeTrabajo } from 'app/shared/model/puesto-de-trabajo.model';
import { AccountService } from 'app/core/auth/account.service';
import { PuestoDeTrabajoService } from './puesto-de-trabajo.service';

@Component({
  selector: 'jhi-puesto-de-trabajo',
  templateUrl: './puesto-de-trabajo.component.html'
})
export class PuestoDeTrabajoComponent implements OnInit, OnDestroy {
  puestoDeTrabajos: IPuestoDeTrabajo[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected puestoDeTrabajoService: PuestoDeTrabajoService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.puestoDeTrabajoService
      .query()
      .pipe(
        filter((res: HttpResponse<IPuestoDeTrabajo[]>) => res.ok),
        map((res: HttpResponse<IPuestoDeTrabajo[]>) => res.body)
      )
      .subscribe((res: IPuestoDeTrabajo[]) => {
        this.puestoDeTrabajos = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPuestoDeTrabajos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPuestoDeTrabajo) {
    return item.id;
  }

  registerChangeInPuestoDeTrabajos() {
    this.eventSubscriber = this.eventManager.subscribe('puestoDeTrabajoListModification', response => this.loadAll());
  }
}

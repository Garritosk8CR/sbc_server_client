import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ISuscripcion } from 'app/shared/model/suscripcion.model';
import { AccountService } from 'app/core/auth/account.service';
import { SuscripcionService } from './suscripcion.service';

@Component({
  selector: 'jhi-suscripcion',
  templateUrl: './suscripcion.component.html'
})
export class SuscripcionComponent implements OnInit, OnDestroy {
  suscripcions: ISuscripcion[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected suscripcionService: SuscripcionService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.suscripcionService
      .query()
      .pipe(
        filter((res: HttpResponse<ISuscripcion[]>) => res.ok),
        map((res: HttpResponse<ISuscripcion[]>) => res.body)
      )
      .subscribe((res: ISuscripcion[]) => {
        this.suscripcions = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSuscripcions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISuscripcion) {
    return item.id;
  }

  registerChangeInSuscripcions() {
    this.eventSubscriber = this.eventManager.subscribe('suscripcionListModification', response => this.loadAll());
  }
}

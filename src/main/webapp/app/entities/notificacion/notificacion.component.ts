import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { INotificacion } from 'app/shared/model/notificacion.model';
import { AccountService } from 'app/core/auth/account.service';
import { NotificacionService } from './notificacion.service';

@Component({
  selector: 'jhi-notificacion',
  templateUrl: './notificacion.component.html'
})
export class NotificacionComponent implements OnInit, OnDestroy {
  notificacions: INotificacion[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected notificacionService: NotificacionService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.notificacionService
      .query()
      .pipe(
        filter((res: HttpResponse<INotificacion[]>) => res.ok),
        map((res: HttpResponse<INotificacion[]>) => res.body)
      )
      .subscribe((res: INotificacion[]) => {
        this.notificacions = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInNotificacions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: INotificacion) {
    return item.id;
  }

  registerChangeInNotificacions() {
    this.eventSubscriber = this.eventManager.subscribe('notificacionListModification', response => this.loadAll());
  }
}

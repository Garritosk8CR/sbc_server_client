import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IConversacion } from 'app/shared/model/conversacion.model';
import { AccountService } from 'app/core/auth/account.service';
import { ConversacionService } from './conversacion.service';

@Component({
  selector: 'jhi-conversacion',
  templateUrl: './conversacion.component.html'
})
export class ConversacionComponent implements OnInit, OnDestroy {
  conversacions: IConversacion[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected conversacionService: ConversacionService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.conversacionService
      .query()
      .pipe(
        filter((res: HttpResponse<IConversacion[]>) => res.ok),
        map((res: HttpResponse<IConversacion[]>) => res.body)
      )
      .subscribe((res: IConversacion[]) => {
        this.conversacions = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInConversacions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IConversacion) {
    return item.id;
  }

  registerChangeInConversacions() {
    this.eventSubscriber = this.eventManager.subscribe('conversacionListModification', response => this.loadAll());
  }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IRequisito } from 'app/shared/model/requisito.model';
import { AccountService } from 'app/core/auth/account.service';
import { RequisitoService } from './requisito.service';

@Component({
  selector: 'jhi-requisito',
  templateUrl: './requisito.component.html'
})
export class RequisitoComponent implements OnInit, OnDestroy {
  requisitos: IRequisito[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected requisitoService: RequisitoService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.requisitoService
      .query()
      .pipe(
        filter((res: HttpResponse<IRequisito[]>) => res.ok),
        map((res: HttpResponse<IRequisito[]>) => res.body)
      )
      .subscribe((res: IRequisito[]) => {
        this.requisitos = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRequisitos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRequisito) {
    return item.id;
  }

  registerChangeInRequisitos() {
    this.eventSubscriber = this.eventManager.subscribe('requisitoListModification', response => this.loadAll());
  }
}

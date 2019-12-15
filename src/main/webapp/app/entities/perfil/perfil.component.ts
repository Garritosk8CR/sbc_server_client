import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPerfil } from 'app/shared/model/perfil.model';
import { AccountService } from 'app/core/auth/account.service';
import { PerfilService } from './perfil.service';

@Component({
  selector: 'jhi-perfil',
  templateUrl: './perfil.component.html'
})
export class PerfilComponent implements OnInit, OnDestroy {
  perfils: IPerfil[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected perfilService: PerfilService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.perfilService
      .query()
      .pipe(
        filter((res: HttpResponse<IPerfil[]>) => res.ok),
        map((res: HttpResponse<IPerfil[]>) => res.body)
      )
      .subscribe((res: IPerfil[]) => {
        this.perfils = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPerfils();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPerfil) {
    return item.id;
  }

  registerChangeInPerfils() {
    this.eventSubscriber = this.eventManager.subscribe('perfilListModification', response => this.loadAll());
  }
}

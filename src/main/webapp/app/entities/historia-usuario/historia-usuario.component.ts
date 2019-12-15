import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';
import { AccountService } from 'app/core/auth/account.service';
import { HistoriaUsuarioService } from './historia-usuario.service';

@Component({
  selector: 'jhi-historia-usuario',
  templateUrl: './historia-usuario.component.html'
})
export class HistoriaUsuarioComponent implements OnInit, OnDestroy {
  historiaUsuarios: IHistoriaUsuario[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected historiaUsuarioService: HistoriaUsuarioService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.historiaUsuarioService
      .query()
      .pipe(
        filter((res: HttpResponse<IHistoriaUsuario[]>) => res.ok),
        map((res: HttpResponse<IHistoriaUsuario[]>) => res.body)
      )
      .subscribe((res: IHistoriaUsuario[]) => {
        this.historiaUsuarios = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInHistoriaUsuarios();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IHistoriaUsuario) {
    return item.id;
  }

  registerChangeInHistoriaUsuarios() {
    this.eventSubscriber = this.eventManager.subscribe('historiaUsuarioListModification', response => this.loadAll());
  }
}

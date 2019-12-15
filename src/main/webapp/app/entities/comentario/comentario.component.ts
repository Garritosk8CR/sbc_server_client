import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IComentario } from 'app/shared/model/comentario.model';
import { AccountService } from 'app/core/auth/account.service';
import { ComentarioService } from './comentario.service';

@Component({
  selector: 'jhi-comentario',
  templateUrl: './comentario.component.html'
})
export class ComentarioComponent implements OnInit, OnDestroy {
  comentarios: IComentario[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected comentarioService: ComentarioService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.comentarioService
      .query()
      .pipe(
        filter((res: HttpResponse<IComentario[]>) => res.ok),
        map((res: HttpResponse<IComentario[]>) => res.body)
      )
      .subscribe((res: IComentario[]) => {
        this.comentarios = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInComentarios();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IComentario) {
    return item.id;
  }

  registerChangeInComentarios() {
    this.eventSubscriber = this.eventManager.subscribe('comentarioListModification', response => this.loadAll());
  }
}

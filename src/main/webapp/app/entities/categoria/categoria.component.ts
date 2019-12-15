import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoria } from 'app/shared/model/categoria.model';
import { AccountService } from 'app/core/auth/account.service';
import { CategoriaService } from './categoria.service';

@Component({
  selector: 'jhi-categoria',
  templateUrl: './categoria.component.html'
})
export class CategoriaComponent implements OnInit, OnDestroy {
  categorias: ICategoria[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected categoriaService: CategoriaService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.categoriaService
      .query()
      .pipe(
        filter((res: HttpResponse<ICategoria[]>) => res.ok),
        map((res: HttpResponse<ICategoria[]>) => res.body)
      )
      .subscribe((res: ICategoria[]) => {
        this.categorias = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCategorias();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICategoria) {
    return item.id;
  }

  registerChangeInCategorias() {
    this.eventSubscriber = this.eventManager.subscribe('categoriaListModification', response => this.loadAll());
  }
}

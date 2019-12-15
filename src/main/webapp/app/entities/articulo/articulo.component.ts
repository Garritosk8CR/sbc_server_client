import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IArticulo } from 'app/shared/model/articulo.model';
import { AccountService } from 'app/core/auth/account.service';
import { ArticuloService } from './articulo.service';

@Component({
  selector: 'jhi-articulo',
  templateUrl: './articulo.component.html'
})
export class ArticuloComponent implements OnInit, OnDestroy {
  articulos: IArticulo[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected articuloService: ArticuloService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.articuloService
      .query()
      .pipe(
        filter((res: HttpResponse<IArticulo[]>) => res.ok),
        map((res: HttpResponse<IArticulo[]>) => res.body)
      )
      .subscribe((res: IArticulo[]) => {
        this.articulos = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInArticulos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IArticulo) {
    return item.id;
  }

  registerChangeInArticulos() {
    this.eventSubscriber = this.eventManager.subscribe('articuloListModification', response => this.loadAll());
  }
}

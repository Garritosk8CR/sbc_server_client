import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICarreraProfesional } from 'app/shared/model/carrera-profesional.model';
import { AccountService } from 'app/core/auth/account.service';
import { CarreraProfesionalService } from './carrera-profesional.service';

@Component({
  selector: 'jhi-carrera-profesional',
  templateUrl: './carrera-profesional.component.html'
})
export class CarreraProfesionalComponent implements OnInit, OnDestroy {
  carreraProfesionals: ICarreraProfesional[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected carreraProfesionalService: CarreraProfesionalService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.carreraProfesionalService
      .query()
      .pipe(
        filter((res: HttpResponse<ICarreraProfesional[]>) => res.ok),
        map((res: HttpResponse<ICarreraProfesional[]>) => res.body)
      )
      .subscribe((res: ICarreraProfesional[]) => {
        this.carreraProfesionals = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCarreraProfesionals();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICarreraProfesional) {
    return item.id;
  }

  registerChangeInCarreraProfesionals() {
    this.eventSubscriber = this.eventManager.subscribe('carreraProfesionalListModification', response => this.loadAll());
  }
}

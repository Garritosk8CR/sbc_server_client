import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICarreraProfesional } from 'app/shared/model/carrera-profesional.model';
import { CarreraProfesionalService } from './carrera-profesional.service';

@Component({
  selector: 'jhi-carrera-profesional-delete-dialog',
  templateUrl: './carrera-profesional-delete-dialog.component.html'
})
export class CarreraProfesionalDeleteDialogComponent {
  carreraProfesional: ICarreraProfesional;

  constructor(
    protected carreraProfesionalService: CarreraProfesionalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.carreraProfesionalService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'carreraProfesionalListModification',
        content: 'Deleted an carreraProfesional'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-carrera-profesional-delete-popup',
  template: ''
})
export class CarreraProfesionalDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ carreraProfesional }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CarreraProfesionalDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.carreraProfesional = carreraProfesional;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/carrera-profesional', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/carrera-profesional', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}

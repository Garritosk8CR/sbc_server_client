import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPuestoDeTrabajo } from 'app/shared/model/puesto-de-trabajo.model';
import { PuestoDeTrabajoService } from './puesto-de-trabajo.service';

@Component({
  selector: 'jhi-puesto-de-trabajo-delete-dialog',
  templateUrl: './puesto-de-trabajo-delete-dialog.component.html'
})
export class PuestoDeTrabajoDeleteDialogComponent {
  puestoDeTrabajo: IPuestoDeTrabajo;

  constructor(
    protected puestoDeTrabajoService: PuestoDeTrabajoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.puestoDeTrabajoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'puestoDeTrabajoListModification',
        content: 'Deleted an puestoDeTrabajo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-puesto-de-trabajo-delete-popup',
  template: ''
})
export class PuestoDeTrabajoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ puestoDeTrabajo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PuestoDeTrabajoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.puestoDeTrabajo = puestoDeTrabajo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/puesto-de-trabajo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/puesto-de-trabajo', { outlets: { popup: null } }]);
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

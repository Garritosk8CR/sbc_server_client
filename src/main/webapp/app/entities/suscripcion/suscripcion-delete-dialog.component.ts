import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISuscripcion } from 'app/shared/model/suscripcion.model';
import { SuscripcionService } from './suscripcion.service';

@Component({
  selector: 'jhi-suscripcion-delete-dialog',
  templateUrl: './suscripcion-delete-dialog.component.html'
})
export class SuscripcionDeleteDialogComponent {
  suscripcion: ISuscripcion;

  constructor(
    protected suscripcionService: SuscripcionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.suscripcionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'suscripcionListModification',
        content: 'Deleted an suscripcion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-suscripcion-delete-popup',
  template: ''
})
export class SuscripcionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ suscripcion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SuscripcionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.suscripcion = suscripcion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/suscripcion', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/suscripcion', { outlets: { popup: null } }]);
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

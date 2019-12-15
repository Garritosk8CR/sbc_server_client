import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMensaje } from 'app/shared/model/mensaje.model';
import { MensajeService } from './mensaje.service';

@Component({
  selector: 'jhi-mensaje-delete-dialog',
  templateUrl: './mensaje-delete-dialog.component.html'
})
export class MensajeDeleteDialogComponent {
  mensaje: IMensaje;

  constructor(protected mensajeService: MensajeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.mensajeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'mensajeListModification',
        content: 'Deleted an mensaje'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-mensaje-delete-popup',
  template: ''
})
export class MensajeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ mensaje }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MensajeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.mensaje = mensaje;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/mensaje', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/mensaje', { outlets: { popup: null } }]);
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

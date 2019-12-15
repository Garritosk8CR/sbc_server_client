import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INotificacion } from 'app/shared/model/notificacion.model';
import { NotificacionService } from './notificacion.service';

@Component({
  selector: 'jhi-notificacion-delete-dialog',
  templateUrl: './notificacion-delete-dialog.component.html'
})
export class NotificacionDeleteDialogComponent {
  notificacion: INotificacion;

  constructor(
    protected notificacionService: NotificacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.notificacionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'notificacionListModification',
        content: 'Deleted an notificacion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-notificacion-delete-popup',
  template: ''
})
export class NotificacionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ notificacion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NotificacionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.notificacion = notificacion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/notificacion', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/notificacion', { outlets: { popup: null } }]);
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

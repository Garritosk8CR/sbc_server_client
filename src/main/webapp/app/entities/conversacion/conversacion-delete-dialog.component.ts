import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConversacion } from 'app/shared/model/conversacion.model';
import { ConversacionService } from './conversacion.service';

@Component({
  selector: 'jhi-conversacion-delete-dialog',
  templateUrl: './conversacion-delete-dialog.component.html'
})
export class ConversacionDeleteDialogComponent {
  conversacion: IConversacion;

  constructor(
    protected conversacionService: ConversacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.conversacionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'conversacionListModification',
        content: 'Deleted an conversacion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-conversacion-delete-popup',
  template: ''
})
export class ConversacionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ conversacion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ConversacionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.conversacion = conversacion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/conversacion', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/conversacion', { outlets: { popup: null } }]);
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

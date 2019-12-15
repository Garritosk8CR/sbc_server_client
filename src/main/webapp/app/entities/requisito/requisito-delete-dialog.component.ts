import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRequisito } from 'app/shared/model/requisito.model';
import { RequisitoService } from './requisito.service';

@Component({
  selector: 'jhi-requisito-delete-dialog',
  templateUrl: './requisito-delete-dialog.component.html'
})
export class RequisitoDeleteDialogComponent {
  requisito: IRequisito;

  constructor(protected requisitoService: RequisitoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.requisitoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'requisitoListModification',
        content: 'Deleted an requisito'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-requisito-delete-popup',
  template: ''
})
export class RequisitoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ requisito }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RequisitoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.requisito = requisito;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/requisito', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/requisito', { outlets: { popup: null } }]);
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

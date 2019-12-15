import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPublicacion } from 'app/shared/model/publicacion.model';
import { PublicacionService } from './publicacion.service';

@Component({
  selector: 'jhi-publicacion-delete-dialog',
  templateUrl: './publicacion-delete-dialog.component.html'
})
export class PublicacionDeleteDialogComponent {
  publicacion: IPublicacion;

  constructor(
    protected publicacionService: PublicacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.publicacionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'publicacionListModification',
        content: 'Deleted an publicacion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-publicacion-delete-popup',
  template: ''
})
export class PublicacionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ publicacion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PublicacionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.publicacion = publicacion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/publicacion', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/publicacion', { outlets: { popup: null } }]);
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

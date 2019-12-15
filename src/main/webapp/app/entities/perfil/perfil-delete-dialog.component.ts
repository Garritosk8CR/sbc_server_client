import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from './perfil.service';

@Component({
  selector: 'jhi-perfil-delete-dialog',
  templateUrl: './perfil-delete-dialog.component.html'
})
export class PerfilDeleteDialogComponent {
  perfil: IPerfil;

  constructor(protected perfilService: PerfilService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.perfilService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'perfilListModification',
        content: 'Deleted an perfil'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-perfil-delete-popup',
  template: ''
})
export class PerfilDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ perfil }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PerfilDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.perfil = perfil;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/perfil', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/perfil', { outlets: { popup: null } }]);
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

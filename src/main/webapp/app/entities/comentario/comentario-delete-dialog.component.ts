import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComentario } from 'app/shared/model/comentario.model';
import { ComentarioService } from './comentario.service';

@Component({
  selector: 'jhi-comentario-delete-dialog',
  templateUrl: './comentario-delete-dialog.component.html'
})
export class ComentarioDeleteDialogComponent {
  comentario: IComentario;

  constructor(
    protected comentarioService: ComentarioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.comentarioService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'comentarioListModification',
        content: 'Deleted an comentario'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-comentario-delete-popup',
  template: ''
})
export class ComentarioDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ comentario }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ComentarioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.comentario = comentario;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/comentario', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/comentario', { outlets: { popup: null } }]);
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

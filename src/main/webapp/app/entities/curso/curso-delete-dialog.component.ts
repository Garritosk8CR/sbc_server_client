import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICurso } from 'app/shared/model/curso.model';
import { CursoService } from './curso.service';

@Component({
  selector: 'jhi-curso-delete-dialog',
  templateUrl: './curso-delete-dialog.component.html'
})
export class CursoDeleteDialogComponent {
  curso: ICurso;

  constructor(protected cursoService: CursoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cursoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cursoListModification',
        content: 'Deleted an curso'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-curso-delete-popup',
  template: ''
})
export class CursoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ curso }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CursoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.curso = curso;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/curso', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/curso', { outlets: { popup: null } }]);
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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITarea } from 'app/shared/model/tarea.model';
import { TareaService } from './tarea.service';

@Component({
  selector: 'jhi-tarea-delete-dialog',
  templateUrl: './tarea-delete-dialog.component.html'
})
export class TareaDeleteDialogComponent {
  tarea: ITarea;

  constructor(protected tareaService: TareaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tareaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tareaListModification',
        content: 'Deleted an tarea'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tarea-delete-popup',
  template: ''
})
export class TareaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tarea }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TareaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tarea = tarea;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tarea', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tarea', { outlets: { popup: null } }]);
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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';
import { HistoriaUsuarioService } from './historia-usuario.service';

@Component({
  selector: 'jhi-historia-usuario-delete-dialog',
  templateUrl: './historia-usuario-delete-dialog.component.html'
})
export class HistoriaUsuarioDeleteDialogComponent {
  historiaUsuario: IHistoriaUsuario;

  constructor(
    protected historiaUsuarioService: HistoriaUsuarioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.historiaUsuarioService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'historiaUsuarioListModification',
        content: 'Deleted an historiaUsuario'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-historia-usuario-delete-popup',
  template: ''
})
export class HistoriaUsuarioDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ historiaUsuario }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(HistoriaUsuarioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.historiaUsuario = historiaUsuario;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/historia-usuario', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/historia-usuario', { outlets: { popup: null } }]);
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

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SbcAppTestModule } from '../../../test.module';
import { PuestoDeTrabajoDeleteDialogComponent } from 'app/entities/puesto-de-trabajo/puesto-de-trabajo-delete-dialog.component';
import { PuestoDeTrabajoService } from 'app/entities/puesto-de-trabajo/puesto-de-trabajo.service';

describe('Component Tests', () => {
  describe('PuestoDeTrabajo Management Delete Component', () => {
    let comp: PuestoDeTrabajoDeleteDialogComponent;
    let fixture: ComponentFixture<PuestoDeTrabajoDeleteDialogComponent>;
    let service: PuestoDeTrabajoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [PuestoDeTrabajoDeleteDialogComponent]
      })
        .overrideTemplate(PuestoDeTrabajoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PuestoDeTrabajoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PuestoDeTrabajoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SbcAppTestModule } from '../../../test.module';
import { SuscripcionDeleteDialogComponent } from 'app/entities/suscripcion/suscripcion-delete-dialog.component';
import { SuscripcionService } from 'app/entities/suscripcion/suscripcion.service';

describe('Component Tests', () => {
  describe('Suscripcion Management Delete Component', () => {
    let comp: SuscripcionDeleteDialogComponent;
    let fixture: ComponentFixture<SuscripcionDeleteDialogComponent>;
    let service: SuscripcionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [SuscripcionDeleteDialogComponent]
      })
        .overrideTemplate(SuscripcionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SuscripcionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SuscripcionService);
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

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SbcAppTestModule } from '../../../test.module';
import { MensajeDeleteDialogComponent } from 'app/entities/mensaje/mensaje-delete-dialog.component';
import { MensajeService } from 'app/entities/mensaje/mensaje.service';

describe('Component Tests', () => {
  describe('Mensaje Management Delete Component', () => {
    let comp: MensajeDeleteDialogComponent;
    let fixture: ComponentFixture<MensajeDeleteDialogComponent>;
    let service: MensajeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [MensajeDeleteDialogComponent]
      })
        .overrideTemplate(MensajeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MensajeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MensajeService);
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

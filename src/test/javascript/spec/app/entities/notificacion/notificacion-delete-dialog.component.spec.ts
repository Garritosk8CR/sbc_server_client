import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SbcAppTestModule } from '../../../test.module';
import { NotificacionDeleteDialogComponent } from 'app/entities/notificacion/notificacion-delete-dialog.component';
import { NotificacionService } from 'app/entities/notificacion/notificacion.service';

describe('Component Tests', () => {
  describe('Notificacion Management Delete Component', () => {
    let comp: NotificacionDeleteDialogComponent;
    let fixture: ComponentFixture<NotificacionDeleteDialogComponent>;
    let service: NotificacionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [NotificacionDeleteDialogComponent]
      })
        .overrideTemplate(NotificacionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NotificacionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotificacionService);
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

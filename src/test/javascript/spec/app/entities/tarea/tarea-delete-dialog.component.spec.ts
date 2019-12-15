import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SbcAppTestModule } from '../../../test.module';
import { TareaDeleteDialogComponent } from 'app/entities/tarea/tarea-delete-dialog.component';
import { TareaService } from 'app/entities/tarea/tarea.service';

describe('Component Tests', () => {
  describe('Tarea Management Delete Component', () => {
    let comp: TareaDeleteDialogComponent;
    let fixture: ComponentFixture<TareaDeleteDialogComponent>;
    let service: TareaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [TareaDeleteDialogComponent]
      })
        .overrideTemplate(TareaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TareaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TareaService);
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

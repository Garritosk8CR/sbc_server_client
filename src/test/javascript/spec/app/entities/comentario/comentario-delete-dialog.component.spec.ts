import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SbcAppTestModule } from '../../../test.module';
import { ComentarioDeleteDialogComponent } from 'app/entities/comentario/comentario-delete-dialog.component';
import { ComentarioService } from 'app/entities/comentario/comentario.service';

describe('Component Tests', () => {
  describe('Comentario Management Delete Component', () => {
    let comp: ComentarioDeleteDialogComponent;
    let fixture: ComponentFixture<ComentarioDeleteDialogComponent>;
    let service: ComentarioService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [ComentarioDeleteDialogComponent]
      })
        .overrideTemplate(ComentarioDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComentarioDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComentarioService);
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

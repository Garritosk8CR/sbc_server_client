import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SbcAppTestModule } from '../../../test.module';
import { CursoDeleteDialogComponent } from 'app/entities/curso/curso-delete-dialog.component';
import { CursoService } from 'app/entities/curso/curso.service';

describe('Component Tests', () => {
  describe('Curso Management Delete Component', () => {
    let comp: CursoDeleteDialogComponent;
    let fixture: ComponentFixture<CursoDeleteDialogComponent>;
    let service: CursoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [CursoDeleteDialogComponent]
      })
        .overrideTemplate(CursoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CursoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CursoService);
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

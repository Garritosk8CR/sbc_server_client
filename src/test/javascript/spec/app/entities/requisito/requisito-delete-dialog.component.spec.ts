import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SbcAppTestModule } from '../../../test.module';
import { RequisitoDeleteDialogComponent } from 'app/entities/requisito/requisito-delete-dialog.component';
import { RequisitoService } from 'app/entities/requisito/requisito.service';

describe('Component Tests', () => {
  describe('Requisito Management Delete Component', () => {
    let comp: RequisitoDeleteDialogComponent;
    let fixture: ComponentFixture<RequisitoDeleteDialogComponent>;
    let service: RequisitoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [RequisitoDeleteDialogComponent]
      })
        .overrideTemplate(RequisitoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RequisitoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RequisitoService);
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

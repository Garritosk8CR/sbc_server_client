import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SbcAppTestModule } from '../../../test.module';
import { ArticuloDeleteDialogComponent } from 'app/entities/articulo/articulo-delete-dialog.component';
import { ArticuloService } from 'app/entities/articulo/articulo.service';

describe('Component Tests', () => {
  describe('Articulo Management Delete Component', () => {
    let comp: ArticuloDeleteDialogComponent;
    let fixture: ComponentFixture<ArticuloDeleteDialogComponent>;
    let service: ArticuloService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [ArticuloDeleteDialogComponent]
      })
        .overrideTemplate(ArticuloDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArticuloDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArticuloService);
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

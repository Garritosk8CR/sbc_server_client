import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SbcAppTestModule } from '../../../test.module';
import { HistoriaUsuarioDeleteDialogComponent } from 'app/entities/historia-usuario/historia-usuario-delete-dialog.component';
import { HistoriaUsuarioService } from 'app/entities/historia-usuario/historia-usuario.service';

describe('Component Tests', () => {
  describe('HistoriaUsuario Management Delete Component', () => {
    let comp: HistoriaUsuarioDeleteDialogComponent;
    let fixture: ComponentFixture<HistoriaUsuarioDeleteDialogComponent>;
    let service: HistoriaUsuarioService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [HistoriaUsuarioDeleteDialogComponent]
      })
        .overrideTemplate(HistoriaUsuarioDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HistoriaUsuarioDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistoriaUsuarioService);
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

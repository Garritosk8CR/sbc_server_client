import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { NotificacionUpdateComponent } from 'app/entities/notificacion/notificacion-update.component';
import { NotificacionService } from 'app/entities/notificacion/notificacion.service';
import { Notificacion } from 'app/shared/model/notificacion.model';

describe('Component Tests', () => {
  describe('Notificacion Management Update Component', () => {
    let comp: NotificacionUpdateComponent;
    let fixture: ComponentFixture<NotificacionUpdateComponent>;
    let service: NotificacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [NotificacionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NotificacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NotificacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotificacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Notificacion(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Notificacion();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { SuscripcionUpdateComponent } from 'app/entities/suscripcion/suscripcion-update.component';
import { SuscripcionService } from 'app/entities/suscripcion/suscripcion.service';
import { Suscripcion } from 'app/shared/model/suscripcion.model';

describe('Component Tests', () => {
  describe('Suscripcion Management Update Component', () => {
    let comp: SuscripcionUpdateComponent;
    let fixture: ComponentFixture<SuscripcionUpdateComponent>;
    let service: SuscripcionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [SuscripcionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SuscripcionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SuscripcionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SuscripcionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Suscripcion(123);
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
        const entity = new Suscripcion();
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

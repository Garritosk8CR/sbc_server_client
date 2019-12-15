import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { CarreraProfesionalUpdateComponent } from 'app/entities/carrera-profesional/carrera-profesional-update.component';
import { CarreraProfesionalService } from 'app/entities/carrera-profesional/carrera-profesional.service';
import { CarreraProfesional } from 'app/shared/model/carrera-profesional.model';

describe('Component Tests', () => {
  describe('CarreraProfesional Management Update Component', () => {
    let comp: CarreraProfesionalUpdateComponent;
    let fixture: ComponentFixture<CarreraProfesionalUpdateComponent>;
    let service: CarreraProfesionalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [CarreraProfesionalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CarreraProfesionalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarreraProfesionalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarreraProfesionalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CarreraProfesional(123);
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
        const entity = new CarreraProfesional();
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

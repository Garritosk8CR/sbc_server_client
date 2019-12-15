import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { TareaUpdateComponent } from 'app/entities/tarea/tarea-update.component';
import { TareaService } from 'app/entities/tarea/tarea.service';
import { Tarea } from 'app/shared/model/tarea.model';

describe('Component Tests', () => {
  describe('Tarea Management Update Component', () => {
    let comp: TareaUpdateComponent;
    let fixture: ComponentFixture<TareaUpdateComponent>;
    let service: TareaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [TareaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TareaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TareaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TareaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tarea(123);
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
        const entity = new Tarea();
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

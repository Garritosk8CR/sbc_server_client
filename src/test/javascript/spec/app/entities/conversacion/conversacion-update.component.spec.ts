import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { ConversacionUpdateComponent } from 'app/entities/conversacion/conversacion-update.component';
import { ConversacionService } from 'app/entities/conversacion/conversacion.service';
import { Conversacion } from 'app/shared/model/conversacion.model';

describe('Component Tests', () => {
  describe('Conversacion Management Update Component', () => {
    let comp: ConversacionUpdateComponent;
    let fixture: ComponentFixture<ConversacionUpdateComponent>;
    let service: ConversacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [ConversacionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ConversacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConversacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConversacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Conversacion(123);
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
        const entity = new Conversacion();
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

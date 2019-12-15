import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { MensajeUpdateComponent } from 'app/entities/mensaje/mensaje-update.component';
import { MensajeService } from 'app/entities/mensaje/mensaje.service';
import { Mensaje } from 'app/shared/model/mensaje.model';

describe('Component Tests', () => {
  describe('Mensaje Management Update Component', () => {
    let comp: MensajeUpdateComponent;
    let fixture: ComponentFixture<MensajeUpdateComponent>;
    let service: MensajeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [MensajeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MensajeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MensajeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MensajeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Mensaje(123);
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
        const entity = new Mensaje();
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

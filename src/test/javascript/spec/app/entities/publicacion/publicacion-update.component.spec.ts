import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { PublicacionUpdateComponent } from 'app/entities/publicacion/publicacion-update.component';
import { PublicacionService } from 'app/entities/publicacion/publicacion.service';
import { Publicacion } from 'app/shared/model/publicacion.model';

describe('Component Tests', () => {
  describe('Publicacion Management Update Component', () => {
    let comp: PublicacionUpdateComponent;
    let fixture: ComponentFixture<PublicacionUpdateComponent>;
    let service: PublicacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [PublicacionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PublicacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PublicacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PublicacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Publicacion(123);
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
        const entity = new Publicacion();
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

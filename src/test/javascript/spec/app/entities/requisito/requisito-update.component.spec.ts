import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { RequisitoUpdateComponent } from 'app/entities/requisito/requisito-update.component';
import { RequisitoService } from 'app/entities/requisito/requisito.service';
import { Requisito } from 'app/shared/model/requisito.model';

describe('Component Tests', () => {
  describe('Requisito Management Update Component', () => {
    let comp: RequisitoUpdateComponent;
    let fixture: ComponentFixture<RequisitoUpdateComponent>;
    let service: RequisitoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [RequisitoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RequisitoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RequisitoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RequisitoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Requisito(123);
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
        const entity = new Requisito();
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

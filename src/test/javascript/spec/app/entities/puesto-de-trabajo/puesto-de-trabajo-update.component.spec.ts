import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { PuestoDeTrabajoUpdateComponent } from 'app/entities/puesto-de-trabajo/puesto-de-trabajo-update.component';
import { PuestoDeTrabajoService } from 'app/entities/puesto-de-trabajo/puesto-de-trabajo.service';
import { PuestoDeTrabajo } from 'app/shared/model/puesto-de-trabajo.model';

describe('Component Tests', () => {
  describe('PuestoDeTrabajo Management Update Component', () => {
    let comp: PuestoDeTrabajoUpdateComponent;
    let fixture: ComponentFixture<PuestoDeTrabajoUpdateComponent>;
    let service: PuestoDeTrabajoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [PuestoDeTrabajoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PuestoDeTrabajoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PuestoDeTrabajoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PuestoDeTrabajoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PuestoDeTrabajo(123);
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
        const entity = new PuestoDeTrabajo();
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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { PerfilUpdateComponent } from 'app/entities/perfil/perfil-update.component';
import { PerfilService } from 'app/entities/perfil/perfil.service';
import { Perfil } from 'app/shared/model/perfil.model';

describe('Component Tests', () => {
  describe('Perfil Management Update Component', () => {
    let comp: PerfilUpdateComponent;
    let fixture: ComponentFixture<PerfilUpdateComponent>;
    let service: PerfilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [PerfilUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PerfilUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfilUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfilService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Perfil(123);
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
        const entity = new Perfil();
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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { ArticuloUpdateComponent } from 'app/entities/articulo/articulo-update.component';
import { ArticuloService } from 'app/entities/articulo/articulo.service';
import { Articulo } from 'app/shared/model/articulo.model';

describe('Component Tests', () => {
  describe('Articulo Management Update Component', () => {
    let comp: ArticuloUpdateComponent;
    let fixture: ComponentFixture<ArticuloUpdateComponent>;
    let service: ArticuloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [ArticuloUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ArticuloUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArticuloUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArticuloService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Articulo(123);
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
        const entity = new Articulo();
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

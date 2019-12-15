import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { ComentarioUpdateComponent } from 'app/entities/comentario/comentario-update.component';
import { ComentarioService } from 'app/entities/comentario/comentario.service';
import { Comentario } from 'app/shared/model/comentario.model';

describe('Component Tests', () => {
  describe('Comentario Management Update Component', () => {
    let comp: ComentarioUpdateComponent;
    let fixture: ComponentFixture<ComentarioUpdateComponent>;
    let service: ComentarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [ComentarioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ComentarioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComentarioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComentarioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Comentario(123);
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
        const entity = new Comentario();
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

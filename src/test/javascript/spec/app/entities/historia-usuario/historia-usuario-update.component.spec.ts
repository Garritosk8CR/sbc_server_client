import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { HistoriaUsuarioUpdateComponent } from 'app/entities/historia-usuario/historia-usuario-update.component';
import { HistoriaUsuarioService } from 'app/entities/historia-usuario/historia-usuario.service';
import { HistoriaUsuario } from 'app/shared/model/historia-usuario.model';

describe('Component Tests', () => {
  describe('HistoriaUsuario Management Update Component', () => {
    let comp: HistoriaUsuarioUpdateComponent;
    let fixture: ComponentFixture<HistoriaUsuarioUpdateComponent>;
    let service: HistoriaUsuarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [HistoriaUsuarioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HistoriaUsuarioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HistoriaUsuarioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistoriaUsuarioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HistoriaUsuario(123);
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
        const entity = new HistoriaUsuario();
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

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbcAppTestModule } from '../../../test.module';
import { HistoriaUsuarioComponent } from 'app/entities/historia-usuario/historia-usuario.component';
import { HistoriaUsuarioService } from 'app/entities/historia-usuario/historia-usuario.service';
import { HistoriaUsuario } from 'app/shared/model/historia-usuario.model';

describe('Component Tests', () => {
  describe('HistoriaUsuario Management Component', () => {
    let comp: HistoriaUsuarioComponent;
    let fixture: ComponentFixture<HistoriaUsuarioComponent>;
    let service: HistoriaUsuarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [HistoriaUsuarioComponent],
        providers: []
      })
        .overrideTemplate(HistoriaUsuarioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HistoriaUsuarioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistoriaUsuarioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HistoriaUsuario(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.historiaUsuarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

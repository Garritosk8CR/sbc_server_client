import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbcAppTestModule } from '../../../test.module';
import { PerfilComponent } from 'app/entities/perfil/perfil.component';
import { PerfilService } from 'app/entities/perfil/perfil.service';
import { Perfil } from 'app/shared/model/perfil.model';

describe('Component Tests', () => {
  describe('Perfil Management Component', () => {
    let comp: PerfilComponent;
    let fixture: ComponentFixture<PerfilComponent>;
    let service: PerfilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [PerfilComponent],
        providers: []
      })
        .overrideTemplate(PerfilComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfilComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfilService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Perfil(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.perfils[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

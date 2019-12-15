import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbcAppTestModule } from '../../../test.module';
import { CursoComponent } from 'app/entities/curso/curso.component';
import { CursoService } from 'app/entities/curso/curso.service';
import { Curso } from 'app/shared/model/curso.model';

describe('Component Tests', () => {
  describe('Curso Management Component', () => {
    let comp: CursoComponent;
    let fixture: ComponentFixture<CursoComponent>;
    let service: CursoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [CursoComponent],
        providers: []
      })
        .overrideTemplate(CursoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CursoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CursoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Curso(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cursos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

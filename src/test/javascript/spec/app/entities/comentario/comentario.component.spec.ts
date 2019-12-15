import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbcAppTestModule } from '../../../test.module';
import { ComentarioComponent } from 'app/entities/comentario/comentario.component';
import { ComentarioService } from 'app/entities/comentario/comentario.service';
import { Comentario } from 'app/shared/model/comentario.model';

describe('Component Tests', () => {
  describe('Comentario Management Component', () => {
    let comp: ComentarioComponent;
    let fixture: ComponentFixture<ComentarioComponent>;
    let service: ComentarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [ComentarioComponent],
        providers: []
      })
        .overrideTemplate(ComentarioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComentarioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComentarioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Comentario(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.comentarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

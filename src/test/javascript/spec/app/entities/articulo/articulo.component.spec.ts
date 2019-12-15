import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbcAppTestModule } from '../../../test.module';
import { ArticuloComponent } from 'app/entities/articulo/articulo.component';
import { ArticuloService } from 'app/entities/articulo/articulo.service';
import { Articulo } from 'app/shared/model/articulo.model';

describe('Component Tests', () => {
  describe('Articulo Management Component', () => {
    let comp: ArticuloComponent;
    let fixture: ComponentFixture<ArticuloComponent>;
    let service: ArticuloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [ArticuloComponent],
        providers: []
      })
        .overrideTemplate(ArticuloComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArticuloComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArticuloService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Articulo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.articulos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

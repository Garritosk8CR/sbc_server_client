import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbcAppTestModule } from '../../../test.module';
import { SuscripcionComponent } from 'app/entities/suscripcion/suscripcion.component';
import { SuscripcionService } from 'app/entities/suscripcion/suscripcion.service';
import { Suscripcion } from 'app/shared/model/suscripcion.model';

describe('Component Tests', () => {
  describe('Suscripcion Management Component', () => {
    let comp: SuscripcionComponent;
    let fixture: ComponentFixture<SuscripcionComponent>;
    let service: SuscripcionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [SuscripcionComponent],
        providers: []
      })
        .overrideTemplate(SuscripcionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SuscripcionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SuscripcionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Suscripcion(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.suscripcions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

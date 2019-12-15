import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbcAppTestModule } from '../../../test.module';
import { PuestoDeTrabajoComponent } from 'app/entities/puesto-de-trabajo/puesto-de-trabajo.component';
import { PuestoDeTrabajoService } from 'app/entities/puesto-de-trabajo/puesto-de-trabajo.service';
import { PuestoDeTrabajo } from 'app/shared/model/puesto-de-trabajo.model';

describe('Component Tests', () => {
  describe('PuestoDeTrabajo Management Component', () => {
    let comp: PuestoDeTrabajoComponent;
    let fixture: ComponentFixture<PuestoDeTrabajoComponent>;
    let service: PuestoDeTrabajoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [PuestoDeTrabajoComponent],
        providers: []
      })
        .overrideTemplate(PuestoDeTrabajoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PuestoDeTrabajoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PuestoDeTrabajoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PuestoDeTrabajo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.puestoDeTrabajos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

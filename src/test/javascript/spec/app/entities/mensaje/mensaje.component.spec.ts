import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbcAppTestModule } from '../../../test.module';
import { MensajeComponent } from 'app/entities/mensaje/mensaje.component';
import { MensajeService } from 'app/entities/mensaje/mensaje.service';
import { Mensaje } from 'app/shared/model/mensaje.model';

describe('Component Tests', () => {
  describe('Mensaje Management Component', () => {
    let comp: MensajeComponent;
    let fixture: ComponentFixture<MensajeComponent>;
    let service: MensajeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [MensajeComponent],
        providers: []
      })
        .overrideTemplate(MensajeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MensajeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MensajeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Mensaje(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.mensajes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

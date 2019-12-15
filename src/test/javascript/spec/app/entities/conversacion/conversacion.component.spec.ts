import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbcAppTestModule } from '../../../test.module';
import { ConversacionComponent } from 'app/entities/conversacion/conversacion.component';
import { ConversacionService } from 'app/entities/conversacion/conversacion.service';
import { Conversacion } from 'app/shared/model/conversacion.model';

describe('Component Tests', () => {
  describe('Conversacion Management Component', () => {
    let comp: ConversacionComponent;
    let fixture: ComponentFixture<ConversacionComponent>;
    let service: ConversacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [ConversacionComponent],
        providers: []
      })
        .overrideTemplate(ConversacionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConversacionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConversacionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Conversacion(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conversacions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

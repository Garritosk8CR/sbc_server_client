import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbcAppTestModule } from '../../../test.module';
import { RequisitoComponent } from 'app/entities/requisito/requisito.component';
import { RequisitoService } from 'app/entities/requisito/requisito.service';
import { Requisito } from 'app/shared/model/requisito.model';

describe('Component Tests', () => {
  describe('Requisito Management Component', () => {
    let comp: RequisitoComponent;
    let fixture: ComponentFixture<RequisitoComponent>;
    let service: RequisitoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [RequisitoComponent],
        providers: []
      })
        .overrideTemplate(RequisitoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RequisitoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RequisitoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Requisito(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.requisitos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

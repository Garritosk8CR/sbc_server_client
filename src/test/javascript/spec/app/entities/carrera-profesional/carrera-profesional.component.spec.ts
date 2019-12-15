import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbcAppTestModule } from '../../../test.module';
import { CarreraProfesionalComponent } from 'app/entities/carrera-profesional/carrera-profesional.component';
import { CarreraProfesionalService } from 'app/entities/carrera-profesional/carrera-profesional.service';
import { CarreraProfesional } from 'app/shared/model/carrera-profesional.model';

describe('Component Tests', () => {
  describe('CarreraProfesional Management Component', () => {
    let comp: CarreraProfesionalComponent;
    let fixture: ComponentFixture<CarreraProfesionalComponent>;
    let service: CarreraProfesionalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [CarreraProfesionalComponent],
        providers: []
      })
        .overrideTemplate(CarreraProfesionalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarreraProfesionalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarreraProfesionalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CarreraProfesional(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.carreraProfesionals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

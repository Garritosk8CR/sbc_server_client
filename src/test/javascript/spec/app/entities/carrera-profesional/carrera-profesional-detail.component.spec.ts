import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { CarreraProfesionalDetailComponent } from 'app/entities/carrera-profesional/carrera-profesional-detail.component';
import { CarreraProfesional } from 'app/shared/model/carrera-profesional.model';

describe('Component Tests', () => {
  describe('CarreraProfesional Management Detail Component', () => {
    let comp: CarreraProfesionalDetailComponent;
    let fixture: ComponentFixture<CarreraProfesionalDetailComponent>;
    const route = ({ data: of({ carreraProfesional: new CarreraProfesional(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [CarreraProfesionalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CarreraProfesionalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarreraProfesionalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.carreraProfesional).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

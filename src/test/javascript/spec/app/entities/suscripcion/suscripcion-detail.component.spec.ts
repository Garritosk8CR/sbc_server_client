import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { SuscripcionDetailComponent } from 'app/entities/suscripcion/suscripcion-detail.component';
import { Suscripcion } from 'app/shared/model/suscripcion.model';

describe('Component Tests', () => {
  describe('Suscripcion Management Detail Component', () => {
    let comp: SuscripcionDetailComponent;
    let fixture: ComponentFixture<SuscripcionDetailComponent>;
    const route = ({ data: of({ suscripcion: new Suscripcion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [SuscripcionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SuscripcionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SuscripcionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.suscripcion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { PuestoDeTrabajoDetailComponent } from 'app/entities/puesto-de-trabajo/puesto-de-trabajo-detail.component';
import { PuestoDeTrabajo } from 'app/shared/model/puesto-de-trabajo.model';

describe('Component Tests', () => {
  describe('PuestoDeTrabajo Management Detail Component', () => {
    let comp: PuestoDeTrabajoDetailComponent;
    let fixture: ComponentFixture<PuestoDeTrabajoDetailComponent>;
    const route = ({ data: of({ puestoDeTrabajo: new PuestoDeTrabajo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [PuestoDeTrabajoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PuestoDeTrabajoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PuestoDeTrabajoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.puestoDeTrabajo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

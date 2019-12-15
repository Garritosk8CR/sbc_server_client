import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { RequisitoDetailComponent } from 'app/entities/requisito/requisito-detail.component';
import { Requisito } from 'app/shared/model/requisito.model';

describe('Component Tests', () => {
  describe('Requisito Management Detail Component', () => {
    let comp: RequisitoDetailComponent;
    let fixture: ComponentFixture<RequisitoDetailComponent>;
    const route = ({ data: of({ requisito: new Requisito(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [RequisitoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RequisitoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RequisitoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.requisito).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { MensajeDetailComponent } from 'app/entities/mensaje/mensaje-detail.component';
import { Mensaje } from 'app/shared/model/mensaje.model';

describe('Component Tests', () => {
  describe('Mensaje Management Detail Component', () => {
    let comp: MensajeDetailComponent;
    let fixture: ComponentFixture<MensajeDetailComponent>;
    const route = ({ data: of({ mensaje: new Mensaje(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [MensajeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MensajeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MensajeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mensaje).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

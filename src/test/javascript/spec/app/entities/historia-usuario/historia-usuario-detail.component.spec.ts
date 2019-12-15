import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { HistoriaUsuarioDetailComponent } from 'app/entities/historia-usuario/historia-usuario-detail.component';
import { HistoriaUsuario } from 'app/shared/model/historia-usuario.model';

describe('Component Tests', () => {
  describe('HistoriaUsuario Management Detail Component', () => {
    let comp: HistoriaUsuarioDetailComponent;
    let fixture: ComponentFixture<HistoriaUsuarioDetailComponent>;
    const route = ({ data: of({ historiaUsuario: new HistoriaUsuario(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [HistoriaUsuarioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HistoriaUsuarioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HistoriaUsuarioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.historiaUsuario).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

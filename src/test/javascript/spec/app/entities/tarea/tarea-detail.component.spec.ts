import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SbcAppTestModule } from '../../../test.module';
import { TareaDetailComponent } from 'app/entities/tarea/tarea-detail.component';
import { Tarea } from 'app/shared/model/tarea.model';

describe('Component Tests', () => {
  describe('Tarea Management Detail Component', () => {
    let comp: TareaDetailComponent;
    let fixture: ComponentFixture<TareaDetailComponent>;
    const route = ({ data: of({ tarea: new Tarea(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SbcAppTestModule],
        declarations: [TareaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TareaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TareaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tarea).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

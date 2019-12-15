import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CursoService } from 'app/entities/curso/curso.service';
import { ICurso, Curso } from 'app/shared/model/curso.model';
import { Categoria } from 'app/shared/model/enumerations/categoria.model';

describe('Service Tests', () => {
  describe('Curso Service', () => {
    let injector: TestBed;
    let service: CursoService;
    let httpMock: HttpTestingController;
    let elemDefault: ICurso;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CursoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Curso(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', Categoria.FRONT_END, 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaActualizacion: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Curso', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaActualizacion: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaActualizacion: currentDate
          },
          returnedFromService
        );
        service
          .create(new Curso(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Curso', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            slug: 'BBBBBB',
            descripcion: 'BBBBBB',
            categoria: 'BBBBBB',
            duracion: 'BBBBBB',
            totalDeArticulos: 'BBBBBB',
            fechaActualizacion: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaActualizacion: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Curso', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            slug: 'BBBBBB',
            descripcion: 'BBBBBB',
            categoria: 'BBBBBB',
            duracion: 'BBBBBB',
            totalDeArticulos: 'BBBBBB',
            fechaActualizacion: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaActualizacion: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Curso', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

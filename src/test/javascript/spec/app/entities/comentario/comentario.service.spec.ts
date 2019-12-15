import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ComentarioService } from 'app/entities/comentario/comentario.service';
import { IComentario, Comentario } from 'app/shared/model/comentario.model';

describe('Service Tests', () => {
  describe('Comentario Service', () => {
    let injector: TestBed;
    let service: ComentarioService;
    let httpMock: HttpTestingController;
    let elemDefault: IComentario;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ComentarioService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Comentario(0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaCreacion: currentDate.format(DATE_FORMAT)
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

      it('should create a Comentario', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaCreacion: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaCreacion: currentDate
          },
          returnedFromService
        );
        service
          .create(new Comentario(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Comentario', () => {
        const returnedFromService = Object.assign(
          {
            autor: 'BBBBBB',
            avatar: 'BBBBBB',
            fechaCreacion: currentDate.format(DATE_FORMAT),
            contenido: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaCreacion: currentDate
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

      it('should return a list of Comentario', () => {
        const returnedFromService = Object.assign(
          {
            autor: 'BBBBBB',
            avatar: 'BBBBBB',
            fechaCreacion: currentDate.format(DATE_FORMAT),
            contenido: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaCreacion: currentDate
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

      it('should delete a Comentario', () => {
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

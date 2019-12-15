import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PublicacionService } from 'app/entities/publicacion/publicacion.service';
import { IPublicacion, Publicacion } from 'app/shared/model/publicacion.model';
import { TipoPublicacion } from 'app/shared/model/enumerations/tipo-publicacion.model';

describe('Service Tests', () => {
  describe('Publicacion Service', () => {
    let injector: TestBed;
    let service: PublicacionService;
    let httpMock: HttpTestingController;
    let elemDefault: IPublicacion;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PublicacionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Publicacion(0, currentDate, 'AAAAAAA', TipoPublicacion.ESPECIAL);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaPublicacion: currentDate.format(DATE_FORMAT)
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

      it('should create a Publicacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaPublicacion: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaPublicacion: currentDate
          },
          returnedFromService
        );
        service
          .create(new Publicacion(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Publicacion', () => {
        const returnedFromService = Object.assign(
          {
            fechaPublicacion: currentDate.format(DATE_FORMAT),
            contenido: 'BBBBBB',
            tipoPublicacion: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaPublicacion: currentDate
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

      it('should return a list of Publicacion', () => {
        const returnedFromService = Object.assign(
          {
            fechaPublicacion: currentDate.format(DATE_FORMAT),
            contenido: 'BBBBBB',
            tipoPublicacion: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaPublicacion: currentDate
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

      it('should delete a Publicacion', () => {
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

import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SuscripcionService } from 'app/entities/suscripcion/suscripcion.service';
import { ISuscripcion, Suscripcion } from 'app/shared/model/suscripcion.model';
import { EstadoSuscripcion } from 'app/shared/model/enumerations/estado-suscripcion.model';

describe('Service Tests', () => {
  describe('Suscripcion Service', () => {
    let injector: TestBed;
    let service: SuscripcionService;
    let httpMock: HttpTestingController;
    let elemDefault: ISuscripcion;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(SuscripcionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Suscripcion(0, currentDate, EstadoSuscripcion.ACTIVO);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaSuscripcion: currentDate.format(DATE_FORMAT)
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

      it('should create a Suscripcion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaSuscripcion: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaSuscripcion: currentDate
          },
          returnedFromService
        );
        service
          .create(new Suscripcion(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Suscripcion', () => {
        const returnedFromService = Object.assign(
          {
            fechaSuscripcion: currentDate.format(DATE_FORMAT),
            estadoSuscripcion: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaSuscripcion: currentDate
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

      it('should return a list of Suscripcion', () => {
        const returnedFromService = Object.assign(
          {
            fechaSuscripcion: currentDate.format(DATE_FORMAT),
            estadoSuscripcion: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaSuscripcion: currentDate
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

      it('should delete a Suscripcion', () => {
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

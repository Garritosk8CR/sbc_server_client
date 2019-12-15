import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { NotificacionService } from 'app/entities/notificacion/notificacion.service';
import { INotificacion, Notificacion } from 'app/shared/model/notificacion.model';
import { EstadoMensaje } from 'app/shared/model/enumerations/estado-mensaje.model';

describe('Service Tests', () => {
  describe('Notificacion Service', () => {
    let injector: TestBed;
    let service: NotificacionService;
    let httpMock: HttpTestingController;
    let elemDefault: INotificacion;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(NotificacionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Notificacion(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, EstadoMensaje.LEIDO);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaEmision: currentDate.format(DATE_FORMAT)
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

      it('should create a Notificacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaEmision: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaEmision: currentDate
          },
          returnedFromService
        );
        service
          .create(new Notificacion(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Notificacion', () => {
        const returnedFromService = Object.assign(
          {
            origen: 'BBBBBB',
            destino: 'BBBBBB',
            tipo: 'BBBBBB',
            fechaEmision: currentDate.format(DATE_FORMAT),
            estadoMensaje: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaEmision: currentDate
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

      it('should return a list of Notificacion', () => {
        const returnedFromService = Object.assign(
          {
            origen: 'BBBBBB',
            destino: 'BBBBBB',
            tipo: 'BBBBBB',
            fechaEmision: currentDate.format(DATE_FORMAT),
            estadoMensaje: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaEmision: currentDate
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

      it('should delete a Notificacion', () => {
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

import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PerfilService } from 'app/entities/perfil/perfil.service';
import { IPerfil, Perfil } from 'app/shared/model/perfil.model';

describe('Service Tests', () => {
  describe('Perfil Service', () => {
    let injector: TestBed;
    let service: PerfilService;
    let httpMock: HttpTestingController;
    let elemDefault: IPerfil;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PerfilService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Perfil(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaIngreso: currentDate.format(DATE_FORMAT),
            fechaSalida: currentDate.format(DATE_FORMAT)
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

      it('should create a Perfil', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaIngreso: currentDate.format(DATE_FORMAT),
            fechaSalida: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaIngreso: currentDate,
            fechaSalida: currentDate
          },
          returnedFromService
        );
        service
          .create(new Perfil(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Perfil', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            primerApellido: 'BBBBBB',
            segundoApellido: 'BBBBBB',
            edad: 'BBBBBB',
            estadoCivil: 'BBBBBB',
            sexo: 'BBBBBB',
            telefonoCelular: 'BBBBBB',
            telefonoFijo: 'BBBBBB',
            correoElectronico: 'BBBBBB',
            direccion: 'BBBBBB',
            cedula: 'BBBBBB',
            fechaIngreso: currentDate.format(DATE_FORMAT),
            fechaSalida: currentDate.format(DATE_FORMAT),
            foto: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaIngreso: currentDate,
            fechaSalida: currentDate
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

      it('should return a list of Perfil', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            primerApellido: 'BBBBBB',
            segundoApellido: 'BBBBBB',
            edad: 'BBBBBB',
            estadoCivil: 'BBBBBB',
            sexo: 'BBBBBB',
            telefonoCelular: 'BBBBBB',
            telefonoFijo: 'BBBBBB',
            correoElectronico: 'BBBBBB',
            direccion: 'BBBBBB',
            cedula: 'BBBBBB',
            fechaIngreso: currentDate.format(DATE_FORMAT),
            fechaSalida: currentDate.format(DATE_FORMAT),
            foto: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaIngreso: currentDate,
            fechaSalida: currentDate
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

      it('should delete a Perfil', () => {
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

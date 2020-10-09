import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EntregadorService } from 'app/entities/entregador/entregador.service';
import { IEntregador, Entregador } from 'app/shared/model/entregador.model';

describe('Service Tests', () => {
  describe('Entregador Service', () => {
    let injector: TestBed;
    let service: EntregadorService;
    let httpMock: HttpTestingController;
    let elemDefault: IEntregador;
    let expectedResult: IEntregador | IEntregador[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EntregadorService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Entregador(0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Entregador', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Entregador()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Entregador', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            placaDaMoto: 'BBBBBB',
            latitudeAtual: 1,
            longitudeAtual: 1,
            cpf: 1,
            linkParaFoto: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Entregador', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            placaDaMoto: 'BBBBBB',
            latitudeAtual: 1,
            longitudeAtual: 1,
            cpf: 1,
            linkParaFoto: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Entregador', () => {
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

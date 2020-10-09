import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProdutoService } from 'app/entities/produto/produto.service';
import { IProduto, Produto } from 'app/shared/model/produto.model';
import { EstadoProduto } from 'app/shared/model/enumerations/estado-produto.model';

describe('Service Tests', () => {
  describe('Produto Service', () => {
    let injector: TestBed;
    let service: ProdutoService;
    let httpMock: HttpTestingController;
    let elemDefault: IProduto;
    let expectedResult: IProduto | IProduto[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProdutoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Produto(0, EstadoProduto.DISPONIVEL, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataDeCadastro: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Produto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataDeCadastro: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataDeCadastro: currentDate,
          },
          returnedFromService
        );

        service.create(new Produto()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Produto', () => {
        const returnedFromService = Object.assign(
          {
            produto: 'BBBBBB',
            nome: 'BBBBBB',
            descricao: 'BBBBBB',
            preco: 1,
            linkParaFoto: 'BBBBBB',
            dataDeCadastro: currentDate.format(DATE_FORMAT),
            totalEmEstoque: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataDeCadastro: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Produto', () => {
        const returnedFromService = Object.assign(
          {
            produto: 'BBBBBB',
            nome: 'BBBBBB',
            descricao: 'BBBBBB',
            preco: 1,
            linkParaFoto: 'BBBBBB',
            dataDeCadastro: currentDate.format(DATE_FORMAT),
            totalEmEstoque: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataDeCadastro: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Produto', () => {
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

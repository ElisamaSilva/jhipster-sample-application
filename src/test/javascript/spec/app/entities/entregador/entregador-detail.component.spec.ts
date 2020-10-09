import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SistemaDeliveryTestModule } from '../../../test.module';
import { EntregadorDetailComponent } from 'app/entities/entregador/entregador-detail.component';
import { Entregador } from 'app/shared/model/entregador.model';

describe('Component Tests', () => {
  describe('Entregador Management Detail Component', () => {
    let comp: EntregadorDetailComponent;
    let fixture: ComponentFixture<EntregadorDetailComponent>;
    const route = ({ data: of({ entregador: new Entregador(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SistemaDeliveryTestModule],
        declarations: [EntregadorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EntregadorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EntregadorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load entregador on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.entregador).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

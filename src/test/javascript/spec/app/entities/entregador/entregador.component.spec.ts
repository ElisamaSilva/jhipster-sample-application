import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SistemaDeliveryTestModule } from '../../../test.module';
import { EntregadorComponent } from 'app/entities/entregador/entregador.component';
import { EntregadorService } from 'app/entities/entregador/entregador.service';
import { Entregador } from 'app/shared/model/entregador.model';

describe('Component Tests', () => {
  describe('Entregador Management Component', () => {
    let comp: EntregadorComponent;
    let fixture: ComponentFixture<EntregadorComponent>;
    let service: EntregadorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SistemaDeliveryTestModule],
        declarations: [EntregadorComponent],
      })
        .overrideTemplate(EntregadorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntregadorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntregadorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Entregador(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.entregadors && comp.entregadors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

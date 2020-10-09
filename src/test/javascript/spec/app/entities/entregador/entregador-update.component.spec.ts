import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SistemaDeliveryTestModule } from '../../../test.module';
import { EntregadorUpdateComponent } from 'app/entities/entregador/entregador-update.component';
import { EntregadorService } from 'app/entities/entregador/entregador.service';
import { Entregador } from 'app/shared/model/entregador.model';

describe('Component Tests', () => {
  describe('Entregador Management Update Component', () => {
    let comp: EntregadorUpdateComponent;
    let fixture: ComponentFixture<EntregadorUpdateComponent>;
    let service: EntregadorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SistemaDeliveryTestModule],
        declarations: [EntregadorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EntregadorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntregadorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntregadorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Entregador(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Entregador();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

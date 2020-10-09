import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEntregador, Entregador } from 'app/shared/model/entregador.model';
import { EntregadorService } from './entregador.service';

@Component({
  selector: 'jhi-entregador-update',
  templateUrl: './entregador-update.component.html',
})
export class EntregadorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    placaDaMoto: [],
    latitudeAtual: [],
    longitudeAtual: [],
    cpf: [null, [Validators.required]],
    linkParaFoto: [],
  });

  constructor(protected entregadorService: EntregadorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entregador }) => {
      this.updateForm(entregador);
    });
  }

  updateForm(entregador: IEntregador): void {
    this.editForm.patchValue({
      id: entregador.id,
      nome: entregador.nome,
      placaDaMoto: entregador.placaDaMoto,
      latitudeAtual: entregador.latitudeAtual,
      longitudeAtual: entregador.longitudeAtual,
      cpf: entregador.cpf,
      linkParaFoto: entregador.linkParaFoto,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entregador = this.createFromForm();
    if (entregador.id !== undefined) {
      this.subscribeToSaveResponse(this.entregadorService.update(entregador));
    } else {
      this.subscribeToSaveResponse(this.entregadorService.create(entregador));
    }
  }

  private createFromForm(): IEntregador {
    return {
      ...new Entregador(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      placaDaMoto: this.editForm.get(['placaDaMoto'])!.value,
      latitudeAtual: this.editForm.get(['latitudeAtual'])!.value,
      longitudeAtual: this.editForm.get(['longitudeAtual'])!.value,
      cpf: this.editForm.get(['cpf'])!.value,
      linkParaFoto: this.editForm.get(['linkParaFoto'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntregador>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}

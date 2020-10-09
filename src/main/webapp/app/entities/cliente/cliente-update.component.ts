import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { IEntregador } from 'app/shared/model/entregador.model';
import { EntregadorService } from 'app/entities/entregador/entregador.service';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html',
})
export class ClienteUpdateComponent implements OnInit {
  isSaving = false;
  entregadors: IEntregador[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    telefone: [],
    email: [null, []],
    login: [null, [Validators.required]],
    senha: [],
    cepDeEndereco: [],
    logradouro: [],
    bairro: [],
    cidade: [],
    entregadores: [],
  });

  constructor(
    protected clienteService: ClienteService,
    protected entregadorService: EntregadorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);

      this.entregadorService.query().subscribe((res: HttpResponse<IEntregador[]>) => (this.entregadors = res.body || []));
    });
  }

  updateForm(cliente: ICliente): void {
    this.editForm.patchValue({
      id: cliente.id,
      nome: cliente.nome,
      telefone: cliente.telefone,
      email: cliente.email,
      login: cliente.login,
      senha: cliente.senha,
      cepDeEndereco: cliente.cepDeEndereco,
      logradouro: cliente.logradouro,
      bairro: cliente.bairro,
      cidade: cliente.cidade,
      entregadores: cliente.entregadores,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    return {
      ...new Cliente(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      telefone: this.editForm.get(['telefone'])!.value,
      email: this.editForm.get(['email'])!.value,
      login: this.editForm.get(['login'])!.value,
      senha: this.editForm.get(['senha'])!.value,
      cepDeEndereco: this.editForm.get(['cepDeEndereco'])!.value,
      logradouro: this.editForm.get(['logradouro'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      cidade: this.editForm.get(['cidade'])!.value,
      entregadores: this.editForm.get(['entregadores'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>): void {
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

  trackById(index: number, item: IEntregador): any {
    return item.id;
  }

  getSelected(selectedVals: IEntregador[], option: IEntregador): IEntregador {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}

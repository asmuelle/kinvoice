import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPGMeter } from '../pg-meter.model';
import { PGMeterService } from '../service/pg-meter.service';
import { PGMeterFormService, PGMeterFormGroup } from './pg-meter-form.service';

@Component({
  standalone: true,
  selector: 'jhi-pg-meter-update',
  templateUrl: './pg-meter-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PGMeterUpdateComponent implements OnInit {
  isSaving = false;
  pGMeter: IPGMeter | null = null;

  editForm: PGMeterFormGroup = this.pGMeterFormService.createPGMeterFormGroup();

  constructor(
    protected pGMeterService: PGMeterService,
    protected pGMeterFormService: PGMeterFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pGMeter }) => {
      this.pGMeter = pGMeter;
      if (pGMeter) {
        this.updateForm(pGMeter);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pGMeter = this.pGMeterFormService.getPGMeter(this.editForm);
    if (pGMeter.id !== null) {
      this.subscribeToSaveResponse(this.pGMeterService.update(pGMeter));
    } else {
      this.subscribeToSaveResponse(this.pGMeterService.create(pGMeter));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPGMeter>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(pGMeter: IPGMeter): void {
    this.pGMeter = pGMeter;
    this.pGMeterFormService.resetForm(this.editForm, pGMeter);
  }
}

import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPGMeter } from '../pg-meter.model';
import { PGMeterService } from '../service/pg-meter.service';

@Component({
  standalone: true,
  templateUrl: './pg-meter-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PGMeterDeleteDialogComponent {
  pGMeter?: IPGMeter;

  constructor(
    protected pGMeterService: PGMeterService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pGMeterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

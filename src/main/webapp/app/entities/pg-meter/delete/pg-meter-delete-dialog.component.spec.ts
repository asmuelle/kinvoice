jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PGMeterService } from '../service/pg-meter.service';

import { PGMeterDeleteDialogComponent } from './pg-meter-delete-dialog.component';

describe('PGMeter Management Delete Component', () => {
  let comp: PGMeterDeleteDialogComponent;
  let fixture: ComponentFixture<PGMeterDeleteDialogComponent>;
  let service: PGMeterService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, PGMeterDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(PGMeterDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PGMeterDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PGMeterService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});

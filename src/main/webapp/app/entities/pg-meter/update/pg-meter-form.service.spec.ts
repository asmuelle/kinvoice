import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../pg-meter.test-samples';

import { PGMeterFormService } from './pg-meter-form.service';

describe('PGMeter Form Service', () => {
  let service: PGMeterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PGMeterFormService);
  });

  describe('Service methods', () => {
    describe('createPGMeterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPGMeterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            meterId: expect.any(Object),
            ownerKey: expect.any(Object),
            ownerName: expect.any(Object),
            utility: expect.any(Object),
            namespace: expect.any(Object),
            meterName: expect.any(Object),
            ref: expect.any(Object),
            site: expect.any(Object),
            lat: expect.any(Object),
            lon: expect.any(Object),
            wsLat: expect.any(Object),
            wsLon: expect.any(Object),
            locationId: expect.any(Object),
            km: expect.any(Object),
            peerName: expect.any(Object),
            markers: expect.any(Object),
            price: expect.any(Object),
          }),
        );
      });

      it('passing IPGMeter should create a new form with FormGroup', () => {
        const formGroup = service.createPGMeterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            meterId: expect.any(Object),
            ownerKey: expect.any(Object),
            ownerName: expect.any(Object),
            utility: expect.any(Object),
            namespace: expect.any(Object),
            meterName: expect.any(Object),
            ref: expect.any(Object),
            site: expect.any(Object),
            lat: expect.any(Object),
            lon: expect.any(Object),
            wsLat: expect.any(Object),
            wsLon: expect.any(Object),
            locationId: expect.any(Object),
            km: expect.any(Object),
            peerName: expect.any(Object),
            markers: expect.any(Object),
            price: expect.any(Object),
          }),
        );
      });
    });

    describe('getPGMeter', () => {
      it('should return NewPGMeter for default PGMeter initial value', () => {
        const formGroup = service.createPGMeterFormGroup(sampleWithNewData);

        const pGMeter = service.getPGMeter(formGroup) as any;

        expect(pGMeter).toMatchObject(sampleWithNewData);
      });

      it('should return NewPGMeter for empty PGMeter initial value', () => {
        const formGroup = service.createPGMeterFormGroup();

        const pGMeter = service.getPGMeter(formGroup) as any;

        expect(pGMeter).toMatchObject({});
      });

      it('should return IPGMeter', () => {
        const formGroup = service.createPGMeterFormGroup(sampleWithRequiredData);

        const pGMeter = service.getPGMeter(formGroup) as any;

        expect(pGMeter).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPGMeter should not enable id FormControl', () => {
        const formGroup = service.createPGMeterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPGMeter should disable id FormControl', () => {
        const formGroup = service.createPGMeterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

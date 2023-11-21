import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPGMeter, NewPGMeter } from '../pg-meter.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPGMeter for edit and NewPGMeterFormGroupInput for create.
 */
type PGMeterFormGroupInput = IPGMeter | PartialWithRequiredKeyOf<NewPGMeter>;

type PGMeterFormDefaults = Pick<NewPGMeter, 'id'>;

type PGMeterFormGroupContent = {
  id: FormControl<IPGMeter['id'] | NewPGMeter['id']>;
  meterId: FormControl<IPGMeter['meterId']>;
  ownerKey: FormControl<IPGMeter['ownerKey']>;
  ownerName: FormControl<IPGMeter['ownerName']>;
  utility: FormControl<IPGMeter['utility']>;
  namespace: FormControl<IPGMeter['namespace']>;
  meterName: FormControl<IPGMeter['meterName']>;
  ref: FormControl<IPGMeter['ref']>;
  site: FormControl<IPGMeter['site']>;
  lat: FormControl<IPGMeter['lat']>;
  lon: FormControl<IPGMeter['lon']>;
  wsLat: FormControl<IPGMeter['wsLat']>;
  wsLon: FormControl<IPGMeter['wsLon']>;
  locationId: FormControl<IPGMeter['locationId']>;
  km: FormControl<IPGMeter['km']>;
  peerName: FormControl<IPGMeter['peerName']>;
  markers: FormControl<IPGMeter['markers']>;
  price: FormControl<IPGMeter['price']>;
};

export type PGMeterFormGroup = FormGroup<PGMeterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PGMeterFormService {
  createPGMeterFormGroup(pGMeter: PGMeterFormGroupInput = { id: null }): PGMeterFormGroup {
    const pGMeterRawValue = {
      ...this.getFormDefaults(),
      ...pGMeter,
    };
    return new FormGroup<PGMeterFormGroupContent>({
      id: new FormControl(
        { value: pGMeterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      meterId: new FormControl(pGMeterRawValue.meterId),
      ownerKey: new FormControl(pGMeterRawValue.ownerKey),
      ownerName: new FormControl(pGMeterRawValue.ownerName),
      utility: new FormControl(pGMeterRawValue.utility),
      namespace: new FormControl(pGMeterRawValue.namespace),
      meterName: new FormControl(pGMeterRawValue.meterName),
      ref: new FormControl(pGMeterRawValue.ref),
      site: new FormControl(pGMeterRawValue.site),
      lat: new FormControl(pGMeterRawValue.lat),
      lon: new FormControl(pGMeterRawValue.lon),
      wsLat: new FormControl(pGMeterRawValue.wsLat),
      wsLon: new FormControl(pGMeterRawValue.wsLon),
      locationId: new FormControl(pGMeterRawValue.locationId),
      km: new FormControl(pGMeterRawValue.km),
      peerName: new FormControl(pGMeterRawValue.peerName),
      markers: new FormControl(pGMeterRawValue.markers),
      price: new FormControl(pGMeterRawValue.price),
    });
  }

  getPGMeter(form: PGMeterFormGroup): IPGMeter | NewPGMeter {
    return form.getRawValue() as IPGMeter | NewPGMeter;
  }

  resetForm(form: PGMeterFormGroup, pGMeter: PGMeterFormGroupInput): void {
    const pGMeterRawValue = { ...this.getFormDefaults(), ...pGMeter };
    form.reset(
      {
        ...pGMeterRawValue,
        id: { value: pGMeterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PGMeterFormDefaults {
    return {
      id: null,
    };
  }
}

import { IScope, NewScope } from './scope.model';

export const sampleWithRequiredData: IScope = {
  id: 18068,
};

export const sampleWithPartialData: IScope = {
  id: 31902,
  meterDescription: 'gosh Jamir',
};

export const sampleWithFullData: IScope = {
  id: 29476,
  serviceId: 15054,
  contractId: 14542,
  meterDescription: 'Crew Borders Southwest',
  meterName: 'brand',
  meterUtility: 'East',
  pricePerMonth: 16731,
};

export const sampleWithNewData: NewScope = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

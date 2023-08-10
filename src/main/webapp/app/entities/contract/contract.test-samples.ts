import dayjs from 'dayjs/esm';

import { IContract, NewContract } from './contract.model';

export const sampleWithRequiredData: IContract = {
  id: 11125,
};

export const sampleWithPartialData: IContract = {
  id: 3422,
  name: 'knowledge Intelligent Dobra',
  customerContactAddresslines: 'primary Savings',
};

export const sampleWithFullData: IContract = {
  id: 15750,
  name: 'offensively azure',
  customerContactName: 'Pants',
  customerContactAddresslines: 'Austria',
  customerPurchaseNumber: 'Nevada Elegant',
  kwiqlyOrderNumber: 'Rohan',
  basePricePerMonth: 18783,
  startDate: dayjs('2023-08-10T04:41'),
  endDate: dayjs('2023-08-10T08:26'),
};

export const sampleWithNewData: NewContract = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

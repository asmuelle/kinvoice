import { IPGMeter, NewPGMeter } from './pg-meter.model';

export const sampleWithRequiredData: IPGMeter = {
  id: 23819,
};

export const sampleWithPartialData: IPGMeter = {
  id: 6041,
  meterId: 22891,
  utility: 'title',
  namespace: 'exactly',
  ref: 'deliberately',
  lat: 19466.72,
  lon: 27418.04,
  peerName: 'always rarely cactus',
  markers: 'trance power',
};

export const sampleWithFullData: IPGMeter = {
  id: 24174,
  meterId: 2176,
  ownerKey: 'lady amid crib',
  ownerName: 'whoosh boohoo goldfish',
  utility: 'unless',
  namespace: 'hence',
  meterName: 'startle recess probation',
  ref: 'adviser upbeat',
  site: 'whether',
  lat: 25180.2,
  lon: 8188.17,
  wsLat: 9772.12,
  wsLon: 27960.64,
  locationId: 7211,
  km: 27878.79,
  peerName: 'inasmuch judo',
  markers: 'despite rigidly knottily',
  price: 20549.99,
};

export const sampleWithNewData: NewPGMeter = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

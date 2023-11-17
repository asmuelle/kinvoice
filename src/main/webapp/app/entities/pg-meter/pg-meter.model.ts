export interface IPGMeter {
  id: number;
  meterId?: number | null;
  ownerKey?: string | null;
  ownerName?: string | null;
  utility?: string | null;
  namespace?: string | null;
  meterName?: string | null;
  ref?: string | null;
  site?: string | null;
  lat?: number | null;
  lon?: number | null;
  wsLat?: number | null;
  wsLon?: number | null;
  locationId?: number | null;
  km?: number | null;
  peerName?: string | null;
  markers?: string | null;
  price?: number | null;
}

export type NewPGMeter = Omit<IPGMeter, 'id'> & { id: null };

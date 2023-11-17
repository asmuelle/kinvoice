import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPGMeter, NewPGMeter } from '../pg-meter.model';

export type PartialUpdatePGMeter = Partial<IPGMeter> & Pick<IPGMeter, 'id'>;

export type EntityResponseType = HttpResponse<IPGMeter>;
export type EntityArrayResponseType = HttpResponse<IPGMeter[]>;

@Injectable({ providedIn: 'root' })
export class PGMeterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pg-meters');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(pGMeter: NewPGMeter): Observable<EntityResponseType> {
    return this.http.post<IPGMeter>(this.resourceUrl, pGMeter, { observe: 'response' });
  }

  update(pGMeter: IPGMeter): Observable<EntityResponseType> {
    return this.http.put<IPGMeter>(`${this.resourceUrl}/${this.getPGMeterIdentifier(pGMeter)}`, pGMeter, { observe: 'response' });
  }

  partialUpdate(pGMeter: PartialUpdatePGMeter): Observable<EntityResponseType> {
    return this.http.patch<IPGMeter>(`${this.resourceUrl}/${this.getPGMeterIdentifier(pGMeter)}`, pGMeter, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPGMeter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPGMeter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPGMeterIdentifier(pGMeter: Pick<IPGMeter, 'id'>): number {
    return pGMeter.id;
  }

  comparePGMeter(o1: Pick<IPGMeter, 'id'> | null, o2: Pick<IPGMeter, 'id'> | null): boolean {
    return o1 && o2 ? this.getPGMeterIdentifier(o1) === this.getPGMeterIdentifier(o2) : o1 === o2;
  }

  addPGMeterToCollectionIfMissing<Type extends Pick<IPGMeter, 'id'>>(
    pGMeterCollection: Type[],
    ...pGMetersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pGMeters: Type[] = pGMetersToCheck.filter(isPresent);
    if (pGMeters.length > 0) {
      const pGMeterCollectionIdentifiers = pGMeterCollection.map(pGMeterItem => this.getPGMeterIdentifier(pGMeterItem)!);
      const pGMetersToAdd = pGMeters.filter(pGMeterItem => {
        const pGMeterIdentifier = this.getPGMeterIdentifier(pGMeterItem);
        if (pGMeterCollectionIdentifiers.includes(pGMeterIdentifier)) {
          return false;
        }
        pGMeterCollectionIdentifiers.push(pGMeterIdentifier);
        return true;
      });
      return [...pGMetersToAdd, ...pGMeterCollection];
    }
    return pGMeterCollection;
  }
}

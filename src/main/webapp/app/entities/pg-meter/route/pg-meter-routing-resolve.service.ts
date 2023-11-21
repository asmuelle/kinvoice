import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPGMeter } from '../pg-meter.model';
import { PGMeterService } from '../service/pg-meter.service';

export const pGMeterResolve = (route: ActivatedRouteSnapshot): Observable<null | IPGMeter> => {
  const id = route.params['id'];
  if (id) {
    return inject(PGMeterService)
      .find(id)
      .pipe(
        mergeMap((pGMeter: HttpResponse<IPGMeter>) => {
          if (pGMeter.body) {
            return of(pGMeter.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default pGMeterResolve;

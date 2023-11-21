import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PGMeterComponent } from './list/pg-meter.component';
import { PGMeterDetailComponent } from './detail/pg-meter-detail.component';
import { PGMeterUpdateComponent } from './update/pg-meter-update.component';
import PGMeterResolve from './route/pg-meter-routing-resolve.service';

const pGMeterRoute: Routes = [
  {
    path: '',
    component: PGMeterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PGMeterDetailComponent,
    resolve: {
      pGMeter: PGMeterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PGMeterUpdateComponent,
    resolve: {
      pGMeter: PGMeterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PGMeterUpdateComponent,
    resolve: {
      pGMeter: PGMeterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default pGMeterRoute;

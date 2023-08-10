import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer',
        data: { pageTitle: 'myApp.customer.home.title' },
        loadChildren: () => import('./customer/customer.routes'),
      },
      {
        path: 'contract',
        data: { pageTitle: 'myApp.contract.home.title' },
        loadChildren: () => import('./contract/contract.routes'),
      },
      {
        path: 'service',
        data: { pageTitle: 'myApp.service.home.title' },
        loadChildren: () => import('./service/service.routes'),
      },
      {
        path: 'scope',
        data: { pageTitle: 'myApp.scope.home.title' },
        loadChildren: () => import('./scope/scope.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}

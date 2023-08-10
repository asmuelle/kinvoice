import NavbarItem from 'app/layouts/navbar/navbar-item.model';

export const EntityNavbarItems: NavbarItem[] = [
  {
    name: 'Customer',
    route: '/customer',
    translationKey: 'global.menu.entities.customer',
  },
  {
    name: 'Contract',
    route: '/contract',
    translationKey: 'global.menu.entities.contract',
  },
  {
    name: 'Service',
    route: '/service',
    translationKey: 'global.menu.entities.service',
  },
  {
    name: 'Scope',
    route: '/scope',
    translationKey: 'global.menu.entities.scope',
  },
];

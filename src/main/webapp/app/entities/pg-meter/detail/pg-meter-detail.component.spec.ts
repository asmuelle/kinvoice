import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PGMeterDetailComponent } from './pg-meter-detail.component';

describe('PGMeter Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PGMeterDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PGMeterDetailComponent,
              resolve: { pGMeter: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PGMeterDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load pGMeter on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PGMeterDetailComponent);

      // THEN
      expect(instance.pGMeter).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

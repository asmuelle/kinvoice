import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PGMeterService } from '../service/pg-meter.service';

import { PGMeterComponent } from './pg-meter.component';

describe('PGMeter Management Component', () => {
  let comp: PGMeterComponent;
  let fixture: ComponentFixture<PGMeterComponent>;
  let service: PGMeterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'pg-meter', component: PGMeterComponent }]),
        HttpClientTestingModule,
        PGMeterComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              }),
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PGMeterComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PGMeterComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PGMeterService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        }),
      ),
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.pGMeters?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to pGMeterService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPGMeterIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPGMeterIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

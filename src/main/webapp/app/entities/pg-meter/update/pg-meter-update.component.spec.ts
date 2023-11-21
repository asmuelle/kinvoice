import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PGMeterService } from '../service/pg-meter.service';
import { IPGMeter } from '../pg-meter.model';
import { PGMeterFormService } from './pg-meter-form.service';

import { PGMeterUpdateComponent } from './pg-meter-update.component';

describe('PGMeter Management Update Component', () => {
  let comp: PGMeterUpdateComponent;
  let fixture: ComponentFixture<PGMeterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pGMeterFormService: PGMeterFormService;
  let pGMeterService: PGMeterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), PGMeterUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PGMeterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PGMeterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pGMeterFormService = TestBed.inject(PGMeterFormService);
    pGMeterService = TestBed.inject(PGMeterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pGMeter: IPGMeter = { id: 456 };

      activatedRoute.data = of({ pGMeter });
      comp.ngOnInit();

      expect(comp.pGMeter).toEqual(pGMeter);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPGMeter>>();
      const pGMeter = { id: 123 };
      jest.spyOn(pGMeterFormService, 'getPGMeter').mockReturnValue(pGMeter);
      jest.spyOn(pGMeterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pGMeter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pGMeter }));
      saveSubject.complete();

      // THEN
      expect(pGMeterFormService.getPGMeter).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pGMeterService.update).toHaveBeenCalledWith(expect.objectContaining(pGMeter));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPGMeter>>();
      const pGMeter = { id: 123 };
      jest.spyOn(pGMeterFormService, 'getPGMeter').mockReturnValue({ id: null });
      jest.spyOn(pGMeterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pGMeter: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pGMeter }));
      saveSubject.complete();

      // THEN
      expect(pGMeterFormService.getPGMeter).toHaveBeenCalled();
      expect(pGMeterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPGMeter>>();
      const pGMeter = { id: 123 };
      jest.spyOn(pGMeterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pGMeter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pGMeterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

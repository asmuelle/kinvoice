package com.myapp.domain;

import static com.myapp.domain.PGMeterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PGMeterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PGMeter.class);
        PGMeter pGMeter1 = getPGMeterSample1();
        PGMeter pGMeter2 = new PGMeter();
        assertThat(pGMeter1).isNotEqualTo(pGMeter2);

        pGMeter2.setId(pGMeter1.getId());
        assertThat(pGMeter1).isEqualTo(pGMeter2);

        pGMeter2 = getPGMeterSample2();
        assertThat(pGMeter1).isNotEqualTo(pGMeter2);
    }
}

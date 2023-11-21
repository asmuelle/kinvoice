package com.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PGMeterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PGMeter getPGMeterSample1() {
        return new PGMeter()
            .id(1L)
            .meterId(1L)
            .ownerKey("ownerKey1")
            .ownerName("ownerName1")
            .utility("utility1")
            .namespace("namespace1")
            .meterName("meterName1")
            .ref("ref1")
            .site("site1")
            .locationId(1L)
            .peerName("peerName1")
            .markers("markers1");
    }

    public static PGMeter getPGMeterSample2() {
        return new PGMeter()
            .id(2L)
            .meterId(2L)
            .ownerKey("ownerKey2")
            .ownerName("ownerName2")
            .utility("utility2")
            .namespace("namespace2")
            .meterName("meterName2")
            .ref("ref2")
            .site("site2")
            .locationId(2L)
            .peerName("peerName2")
            .markers("markers2");
    }

    public static PGMeter getPGMeterRandomSampleGenerator() {
        return new PGMeter()
            .id(longCount.incrementAndGet())
            .meterId(longCount.incrementAndGet())
            .ownerKey(UUID.randomUUID().toString())
            .ownerName(UUID.randomUUID().toString())
            .utility(UUID.randomUUID().toString())
            .namespace(UUID.randomUUID().toString())
            .meterName(UUID.randomUUID().toString())
            .ref(UUID.randomUUID().toString())
            .site(UUID.randomUUID().toString())
            .locationId(longCount.incrementAndGet())
            .peerName(UUID.randomUUID().toString())
            .markers(UUID.randomUUID().toString());
    }
}

package com.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.myapp.domain.PGMeter} entity. This class is used
 * in {@link com.myapp.web.rest.PGMeterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pg-meters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PGMeterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter meterId;

    private StringFilter ownerKey;

    private StringFilter ownerName;

    private StringFilter utility;

    private StringFilter namespace;

    private StringFilter meterName;

    private StringFilter ref;

    private StringFilter site;

    private FloatFilter lat;

    private FloatFilter lon;

    private FloatFilter wsLat;

    private FloatFilter wsLon;

    private LongFilter locationId;

    private FloatFilter km;

    private StringFilter peerName;

    private StringFilter markers;

    private FloatFilter price;

    private Boolean distinct;

    public PGMeterCriteria() {}

    public PGMeterCriteria(PGMeterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.meterId = other.meterId == null ? null : other.meterId.copy();
        this.ownerKey = other.ownerKey == null ? null : other.ownerKey.copy();
        this.ownerName = other.ownerName == null ? null : other.ownerName.copy();
        this.utility = other.utility == null ? null : other.utility.copy();
        this.namespace = other.namespace == null ? null : other.namespace.copy();
        this.meterName = other.meterName == null ? null : other.meterName.copy();
        this.ref = other.ref == null ? null : other.ref.copy();
        this.site = other.site == null ? null : other.site.copy();
        this.lat = other.lat == null ? null : other.lat.copy();
        this.lon = other.lon == null ? null : other.lon.copy();
        this.wsLat = other.wsLat == null ? null : other.wsLat.copy();
        this.wsLon = other.wsLon == null ? null : other.wsLon.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.km = other.km == null ? null : other.km.copy();
        this.peerName = other.peerName == null ? null : other.peerName.copy();
        this.markers = other.markers == null ? null : other.markers.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PGMeterCriteria copy() {
        return new PGMeterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getMeterId() {
        return meterId;
    }

    public LongFilter meterId() {
        if (meterId == null) {
            meterId = new LongFilter();
        }
        return meterId;
    }

    public void setMeterId(LongFilter meterId) {
        this.meterId = meterId;
    }

    public StringFilter getOwnerKey() {
        return ownerKey;
    }

    public StringFilter ownerKey() {
        if (ownerKey == null) {
            ownerKey = new StringFilter();
        }
        return ownerKey;
    }

    public void setOwnerKey(StringFilter ownerKey) {
        this.ownerKey = ownerKey;
    }

    public StringFilter getOwnerName() {
        return ownerName;
    }

    public StringFilter ownerName() {
        if (ownerName == null) {
            ownerName = new StringFilter();
        }
        return ownerName;
    }

    public void setOwnerName(StringFilter ownerName) {
        this.ownerName = ownerName;
    }

    public StringFilter getUtility() {
        return utility;
    }

    public StringFilter utility() {
        if (utility == null) {
            utility = new StringFilter();
        }
        return utility;
    }

    public void setUtility(StringFilter utility) {
        this.utility = utility;
    }

    public StringFilter getNamespace() {
        return namespace;
    }

    public StringFilter namespace() {
        if (namespace == null) {
            namespace = new StringFilter();
        }
        return namespace;
    }

    public void setNamespace(StringFilter namespace) {
        this.namespace = namespace;
    }

    public StringFilter getMeterName() {
        return meterName;
    }

    public StringFilter meterName() {
        if (meterName == null) {
            meterName = new StringFilter();
        }
        return meterName;
    }

    public void setMeterName(StringFilter meterName) {
        this.meterName = meterName;
    }

    public StringFilter getRef() {
        return ref;
    }

    public StringFilter ref() {
        if (ref == null) {
            ref = new StringFilter();
        }
        return ref;
    }

    public void setRef(StringFilter ref) {
        this.ref = ref;
    }

    public StringFilter getSite() {
        return site;
    }

    public StringFilter site() {
        if (site == null) {
            site = new StringFilter();
        }
        return site;
    }

    public void setSite(StringFilter site) {
        this.site = site;
    }

    public FloatFilter getLat() {
        return lat;
    }

    public FloatFilter lat() {
        if (lat == null) {
            lat = new FloatFilter();
        }
        return lat;
    }

    public void setLat(FloatFilter lat) {
        this.lat = lat;
    }

    public FloatFilter getLon() {
        return lon;
    }

    public FloatFilter lon() {
        if (lon == null) {
            lon = new FloatFilter();
        }
        return lon;
    }

    public void setLon(FloatFilter lon) {
        this.lon = lon;
    }

    public FloatFilter getWsLat() {
        return wsLat;
    }

    public FloatFilter wsLat() {
        if (wsLat == null) {
            wsLat = new FloatFilter();
        }
        return wsLat;
    }

    public void setWsLat(FloatFilter wsLat) {
        this.wsLat = wsLat;
    }

    public FloatFilter getWsLon() {
        return wsLon;
    }

    public FloatFilter wsLon() {
        if (wsLon == null) {
            wsLon = new FloatFilter();
        }
        return wsLon;
    }

    public void setWsLon(FloatFilter wsLon) {
        this.wsLon = wsLon;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public LongFilter locationId() {
        if (locationId == null) {
            locationId = new LongFilter();
        }
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public FloatFilter getKm() {
        return km;
    }

    public FloatFilter km() {
        if (km == null) {
            km = new FloatFilter();
        }
        return km;
    }

    public void setKm(FloatFilter km) {
        this.km = km;
    }

    public StringFilter getPeerName() {
        return peerName;
    }

    public StringFilter peerName() {
        if (peerName == null) {
            peerName = new StringFilter();
        }
        return peerName;
    }

    public void setPeerName(StringFilter peerName) {
        this.peerName = peerName;
    }

    public StringFilter getMarkers() {
        return markers;
    }

    public StringFilter markers() {
        if (markers == null) {
            markers = new StringFilter();
        }
        return markers;
    }

    public void setMarkers(StringFilter markers) {
        this.markers = markers;
    }

    public FloatFilter getPrice() {
        return price;
    }

    public FloatFilter price() {
        if (price == null) {
            price = new FloatFilter();
        }
        return price;
    }

    public void setPrice(FloatFilter price) {
        this.price = price;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PGMeterCriteria that = (PGMeterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(meterId, that.meterId) &&
            Objects.equals(ownerKey, that.ownerKey) &&
            Objects.equals(ownerName, that.ownerName) &&
            Objects.equals(utility, that.utility) &&
            Objects.equals(namespace, that.namespace) &&
            Objects.equals(meterName, that.meterName) &&
            Objects.equals(ref, that.ref) &&
            Objects.equals(site, that.site) &&
            Objects.equals(lat, that.lat) &&
            Objects.equals(lon, that.lon) &&
            Objects.equals(wsLat, that.wsLat) &&
            Objects.equals(wsLon, that.wsLon) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(km, that.km) &&
            Objects.equals(peerName, that.peerName) &&
            Objects.equals(markers, that.markers) &&
            Objects.equals(price, that.price) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            meterId,
            ownerKey,
            ownerName,
            utility,
            namespace,
            meterName,
            ref,
            site,
            lat,
            lon,
            wsLat,
            wsLon,
            locationId,
            km,
            peerName,
            markers,
            price,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PGMeterCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (meterId != null ? "meterId=" + meterId + ", " : "") +
            (ownerKey != null ? "ownerKey=" + ownerKey + ", " : "") +
            (ownerName != null ? "ownerName=" + ownerName + ", " : "") +
            (utility != null ? "utility=" + utility + ", " : "") +
            (namespace != null ? "namespace=" + namespace + ", " : "") +
            (meterName != null ? "meterName=" + meterName + ", " : "") +
            (ref != null ? "ref=" + ref + ", " : "") +
            (site != null ? "site=" + site + ", " : "") +
            (lat != null ? "lat=" + lat + ", " : "") +
            (lon != null ? "lon=" + lon + ", " : "") +
            (wsLat != null ? "wsLat=" + wsLat + ", " : "") +
            (wsLon != null ? "wsLon=" + wsLon + ", " : "") +
            (locationId != null ? "locationId=" + locationId + ", " : "") +
            (km != null ? "km=" + km + ", " : "") +
            (peerName != null ? "peerName=" + peerName + ", " : "") +
            (markers != null ? "markers=" + markers + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

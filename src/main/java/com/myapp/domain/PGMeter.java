package com.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PGMeter.
 */
@Entity
@Table(name = "pg_meter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PGMeter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "meter_id")
    private Long meterId;

    @Column(name = "owner_key")
    private String ownerKey;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "utility")
    private String utility;

    @Column(name = "namespace")
    private String namespace;

    @Column(name = "meter_name")
    private String meterName;

    @Column(name = "ref")
    private String ref;

    @Column(name = "site")
    private String site;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lon")
    private Float lon;

    @Column(name = "ws_lat")
    private Float wsLat;

    @Column(name = "ws_lon")
    private Float wsLon;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "km")
    private Float km;

    @Column(name = "peer_name")
    private String peerName;

    @Column(name = "markers")
    private String markers;

    @Column(name = "price")
    private Float price;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PGMeter id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMeterId() {
        return this.meterId;
    }

    public PGMeter meterId(Long meterId) {
        this.setMeterId(meterId);
        return this;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    public String getOwnerKey() {
        return this.ownerKey;
    }

    public PGMeter ownerKey(String ownerKey) {
        this.setOwnerKey(ownerKey);
        return this;
    }

    public void setOwnerKey(String ownerKey) {
        this.ownerKey = ownerKey;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public PGMeter ownerName(String ownerName) {
        this.setOwnerName(ownerName);
        return this;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getUtility() {
        return this.utility;
    }

    public PGMeter utility(String utility) {
        this.setUtility(utility);
        return this;
    }

    public void setUtility(String utility) {
        this.utility = utility;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public PGMeter namespace(String namespace) {
        this.setNamespace(namespace);
        return this;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getMeterName() {
        return this.meterName;
    }

    public PGMeter meterName(String meterName) {
        this.setMeterName(meterName);
        return this;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

    public String getRef() {
        return this.ref;
    }

    public PGMeter ref(String ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSite() {
        return this.site;
    }

    public PGMeter site(String site) {
        this.setSite(site);
        return this;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Float getLat() {
        return this.lat;
    }

    public PGMeter lat(Float lat) {
        this.setLat(lat);
        return this;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return this.lon;
    }

    public PGMeter lon(Float lon) {
        this.setLon(lon);
        return this;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public Float getWsLat() {
        return this.wsLat;
    }

    public PGMeter wsLat(Float wsLat) {
        this.setWsLat(wsLat);
        return this;
    }

    public void setWsLat(Float wsLat) {
        this.wsLat = wsLat;
    }

    public Float getWsLon() {
        return this.wsLon;
    }

    public PGMeter wsLon(Float wsLon) {
        this.setWsLon(wsLon);
        return this;
    }

    public void setWsLon(Float wsLon) {
        this.wsLon = wsLon;
    }

    public Long getLocationId() {
        return this.locationId;
    }

    public PGMeter locationId(Long locationId) {
        this.setLocationId(locationId);
        return this;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Float getKm() {
        return this.km;
    }

    public PGMeter km(Float km) {
        this.setKm(km);
        return this;
    }

    public void setKm(Float km) {
        this.km = km;
    }

    public String getPeerName() {
        return this.peerName;
    }

    public PGMeter peerName(String peerName) {
        this.setPeerName(peerName);
        return this;
    }

    public void setPeerName(String peerName) {
        this.peerName = peerName;
    }

    public String getMarkers() {
        return this.markers;
    }

    public PGMeter markers(String markers) {
        this.setMarkers(markers);
        return this;
    }

    public void setMarkers(String markers) {
        this.markers = markers;
    }

    public Float getPrice() {
        return this.price;
    }

    public PGMeter price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PGMeter)) {
            return false;
        }
        return getId() != null && getId().equals(((PGMeter) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PGMeter{" +
            "id=" + getId() +
            ", meterId=" + getMeterId() +
            ", ownerKey='" + getOwnerKey() + "'" +
            ", ownerName='" + getOwnerName() + "'" +
            ", utility='" + getUtility() + "'" +
            ", namespace='" + getNamespace() + "'" +
            ", meterName='" + getMeterName() + "'" +
            ", ref='" + getRef() + "'" +
            ", site='" + getSite() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            ", wsLat=" + getWsLat() +
            ", wsLon=" + getWsLon() +
            ", locationId=" + getLocationId() +
            ", km=" + getKm() +
            ", peerName='" + getPeerName() + "'" +
            ", markers='" + getMarkers() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}

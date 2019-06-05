package com.tufike.taxi.common.components.SlideableCardsViewPager;


import com.google.android.gms.maps.model.LatLng;

public class RequestItem {
    private String origin;
    private String destination;
    private double distance;
    private double fromYou;
    private LatLng pickUpLocation;
    private LatLng dropOffLocation;
    public RequestItem(String Origin, String Destination, double Distance, double FromYou) {
        origin = Origin;
        destination = Destination;
        distance = Distance;
        fromYou = FromYou;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getFromYou() {
        return fromYou;
    }

    public void setFromYou(double fromYou) {
        this.fromYou = fromYou;
    }

    public LatLng getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(LatLng pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public LatLng getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(LatLng dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }
}

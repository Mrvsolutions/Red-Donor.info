package mr.vsolutions.red_donorinfo.model;

import com.google.android.gms.maps.model.LatLng;

public class PlacesItem {
    public String PlaceId, Placetitle, Placedescription, PlaceImageURL;
    public boolean isPlaying;


    public LatLng Placelatlng;

    public PlacesItem(String placeId, String Placetitle, String PlaceImageURL, LatLng Placelatlng) {
        PlaceId = placeId;
        this.Placetitle = Placetitle;
        this.PlaceImageURL = PlaceImageURL;
        this.Placelatlng = Placelatlng;

    }
    public PlacesItem() {}

    public LatLng getPlacelatlng() {
        return Placelatlng;
    }

    public void setPlacelatlng(LatLng placelatlng) {
        Placelatlng = placelatlng;
    }
    public String getPlaceId() {
        return PlaceId;
    }

    public void setPlaceId(String placeId) {
        PlaceId = placeId;
    }

    public String getPlacetitle() {
        return Placetitle;
    }

    public void setPlacetitle(String placetitle) {
        this.Placetitle = placetitle;
    }

    public String getPlacedescription() {
        return Placedescription;
    }

    public void setPlacedescription(String placedescription) {
        this.Placedescription = placedescription;
    }

    public String getPlaceImageURL() {
        return PlaceImageURL;
    }

    public void setPlaceImageURL(String placeImageURL) {
        this.PlaceImageURL = placeImageURL;
    }

}

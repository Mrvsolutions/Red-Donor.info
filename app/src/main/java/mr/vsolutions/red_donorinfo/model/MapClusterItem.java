package mr.vsolutions.red_donorinfo.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MapClusterItem implements ClusterItem {

    private final String username;
    private final LatLng latLng;
    private final DonorDataMain.Donordata donordata;

    public DonorDataMain.Donordata getDonordata() {
        return donordata;
    }

    public MapClusterItem(String username, LatLng latLng, DonorDataMain.Donordata donordata) {
        this.username = username;
        this.latLng = latLng;
        this.donordata = donordata;
    }

    @Override
    public LatLng getPosition() {  // 1
        return latLng;
    }

    @Override
    public String getTitle() {  // 2
        return username;
    }

    @Override
    public String getSnippet() {
        return "";
    }
}
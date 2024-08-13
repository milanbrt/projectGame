package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Location;
import be.howest.ti.game.logic.Tile;

import java.util.Set;

public class GetReachableLocationsResponse extends ResponseWithHiddenStatus {
    Set<Location> reachableLocations;
    public GetReachableLocationsResponse(Set<Location> reachableLocations) {
        super(200);
        this.reachableLocations = reachableLocations;
    }

    public Set<Location> getReachableLocations() {
        return reachableLocations;
    }
}


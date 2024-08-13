package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Location;

import java.util.List;

public class MovePlayerResponse extends ResponseWithHiddenStatus {
    private final String name;
    private final Location location;
    private final List<String> treasuresFound;
    private final String state;
    private final String objective;
    public MovePlayerResponse(String name, Location location, List<String> treasuresFound, String state, String objective) {
        super(200);
        this.name = name;
        this.location = location;
        this.treasuresFound = treasuresFound;
        this.state = state;
        this.objective = objective;
    }

    public String getName(){
        return name;
    }

    public Location getLocation(){
        return location;
    }

    public List<String> getTreasuresFound(){
        return treasuresFound;
    }

    public String getState(){
        return state;
    }

    public String getObjective(){
        return objective;
    }
}

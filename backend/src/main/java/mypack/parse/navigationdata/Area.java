package mypack.parse.navigationdata;

import java.util.Map;
import com.fasterxml.jackson.annotation.*;

public class Area {
	private Beacon[][] beacons;
    private long isCrossroad;
    private long isGoal;
    private long isStart;
    private String navigation;
    private long rotateDegree;
    private long routeId;
    private long steps;

    @JsonProperty("beacons")
    public Beacon[][] getBeacons() { return beacons; }
    @JsonProperty("beacons")
    public void setBeacons(Beacon[][] value) { this.beacons = value; }

    @JsonProperty("isCrossroad")
    public long getIsCrossroad() { return isCrossroad; }
    @JsonProperty("isCrossroad")
    public void setIsCrossroad(long value) { this.isCrossroad = value; }

    @JsonProperty("isGoal")
    public long getIsGoal() { return isGoal; }
    @JsonProperty("isGoal")
    public void setIsGoal(long value) { this.isGoal = value; }

    @JsonProperty("isStart")
    public long getIsStart() { return isStart; }
    @JsonProperty("isStart")
    public void setIsStart(long value) { this.isStart = value; }

    @JsonProperty("navigation")
    public String getNavigation() { return navigation; }
    @JsonProperty("navigation")
    public void setNavigation(String value) { this.navigation = value; }

    @JsonProperty("rotateDegree")
    public long getRotateDegree() { return rotateDegree; }
    @JsonProperty("rotateDegree")
    public void setRotateDegree(long value) { this.rotateDegree = value; }

    @JsonProperty("routeId")
    public long getRouteId() { return routeId; }
    @JsonProperty("routeId")
    public void setRouteId(long value) { this.routeId = value; }

    @JsonProperty("steps")
    public long getSteps() { return steps; }
    @JsonProperty("steps")
    public void setSteps(long value) { this.steps = value; }
}

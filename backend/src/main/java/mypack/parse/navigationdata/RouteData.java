package mypack.parse.navigationdata;

import java.util.Map;
import com.fasterxml.jackson.annotation.*;

public class RouteData {
	private Area[] areas;
    private String destinationName;
    private String eventId;
    private String sourceName;

    @JsonProperty("areas")
    public Area[] getAreas() { return areas; }
    @JsonProperty("areas")
    public void setAreas(Area[] value) { this.areas = value; }

    @JsonProperty("destinationName")
    public String getDestinationName() { return destinationName; }
    @JsonProperty("destinationName")
    public void setDestinationName(String value) { this.destinationName = value; }

    @JsonProperty("eventId")
    public String getEventId() { return eventId; }
    @JsonProperty("eventId")
    public void setEventId(String value) { this.eventId = value; }

    @JsonProperty("sourceName")
    public String getSourceName() { return sourceName; }
    @JsonProperty("sourceName")
    public void setSourceName(String value) { this.sourceName = value; }
}

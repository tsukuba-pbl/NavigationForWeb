package io.quicktype;

import java.util.Map;
import com.fasterxml.jackson.annotation.*;

public class GettingStarted {
	private Area[] areas;
    private String destinationName;
    private long eventId;
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
    public long getEventId() { return eventId; }
    @JsonProperty("eventId")
    public void setEventId(long value) { this.eventId = value; }

    @JsonProperty("sourceName")
    public String getSourceName() { return sourceName; }
    @JsonProperty("sourceName")
    public void setSourceName(String value) { this.sourceName = value; }
}

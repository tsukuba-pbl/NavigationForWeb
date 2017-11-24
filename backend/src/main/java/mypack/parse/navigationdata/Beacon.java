package mypack.parse.navigationdata;

import com.fasterxml.jackson.annotation.*;

public class Beacon {
	private long minorId;
    private double rssi;

    @JsonProperty("minorId")
    public long getMinorId() { return minorId; }
    @JsonProperty("minorId")
    public void setMinorId(long value) { this.minorId = value; }

    @JsonProperty("rssi")
    public double getRssi() { return rssi; }
    @JsonProperty("rssi")
    public void setRssi(double value) { this.rssi = value; }
}

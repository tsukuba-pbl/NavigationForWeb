package com.example.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.json.GsonJsonParser;

import com.example.web.entity.BeaconEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomDeserializer {
	
	/**
	 * RequestのJSONのDateフォーマットをStringからDateに変換
	 * @author tomohiro
	 *
	 */
	public static class DateDeserializer extends JsonDeserializer<Date> {
		@Override
		public Date deserialize(com.fasterxml.jackson.core.JsonParser jsonParser, DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	        String date = jsonParser.getText();
	        try {
				return format.parse(date);
			} catch (java.text.ParseException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * RequestのJSONのDateフォーマットをStringからDateに変換
	 * @author tomohiro
	 *
	 */
	public static class BeaconsDeserializer extends JsonDeserializer<BeaconEntity[]> {
		@Override
		public BeaconEntity[] deserialize(com.fasterxml.jackson.core.JsonParser jsonParser, DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
	        String beacons = jsonParser.getText();
	        BeaconEntity[] beaconList = mapper.readValue(beacons, BeaconEntity[].class);
	        return beaconList;
		}
	}
}

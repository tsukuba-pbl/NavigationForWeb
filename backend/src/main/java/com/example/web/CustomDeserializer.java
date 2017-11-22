package com.example.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

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
}

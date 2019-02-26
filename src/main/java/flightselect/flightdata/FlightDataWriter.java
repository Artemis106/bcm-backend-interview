package flightselect.flightdata;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FlightDataWriter {

	public static String getJson(List<FlightData> flightData) {
		String result = "{}";
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			result = mapper.writeValueAsString(flightData);
		} catch (JsonProcessingException e) {
			String error = e.getMessage();
		}
		
		return result;
	}
}

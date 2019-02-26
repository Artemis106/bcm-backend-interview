package flightselect.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import flightselect.flightdata.FlightData;

public class FlightDataDeserialiser extends StdDeserializer<FlightData>{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FlightDataDeserialiser(Class<?> vc) {
	        super(vc);
	    }

	@Override
	public FlightData deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		FlightData data = new FlightData();
		
		 while(!parser.isClosed()){
	            JsonToken jsonToken = parser.nextToken();

	            if(JsonToken.FIELD_NAME.equals(jsonToken)){
	                String fieldName = parser.getCurrentName();
	                System.out.println(fieldName);

	                jsonToken = parser.nextToken();

	                if("price".equals(fieldName)){
	                	data.setPrice(new Double(parser.getValueAsString()));
	                } else if ("dtime".equals(fieldName) || "departure_time".equals(fieldName)){
	                	data.setDeparture_time(parser.getValueAsString());
	                } else if ("atime".equals(fieldName) || "arrival_time".equals(fieldName)){
	                	data.setArrival_time(parser.getValueAsString());
	                }
	            }
	        }
		
		return data;
	}
	
	 
}

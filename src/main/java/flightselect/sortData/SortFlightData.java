package flightselect.sortData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import flightselect.flightdata.FlightData;

public class SortFlightData {

	public static List<FlightData> sortData(List<FlightData> jazz, List<FlightData> moon, List<FlightData> beam) {
		
		
		List<FlightData> allFlightData = new ArrayList<FlightData>();
		
		allFlightData.addAll(jazz);
		allFlightData.addAll(moon);
		allFlightData.addAll(beam);
		
		Collections.sort(allFlightData);
		
		allFlightData = allFlightData.subList(0, 100);
		
		return allFlightData;
	}
}

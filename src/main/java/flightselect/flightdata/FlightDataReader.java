package flightselect.flightdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.text.AbstractDocument.Content;
import javax.swing.tree.RowMapper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import flightselect.constant.Compagny;
import flightselect.deserializer.FlightDataDeserialiser;

public class FlightDataReader {

	ObjectMapper mapper;
	String airUrl;
	String airName;
	List<FlightData> flightDataList;
	
	public FlightDataReader(Compagny compagny) {
		//initialisation des info de la compagny
		this.airName = compagny.getName();
		this.airUrl = compagny.getUrl();
		flightDataList = new ArrayList<>();
		
//		SimpleModule module = new SimpleModule();
//		module.addDeserializer(FlightData.class, new FlightDataDeserialiser(FlightData.class));

		this.mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		mapper.registerModule(module);

	}
	
	public List<FlightData> getFlightData() {
		StringBuffer content = new StringBuffer();
		BufferedReader in = null;
		try {
			//connextion au mock
			URL url = new URL(airUrl);
			HttpURLConnection dataReader = (HttpURLConnection) url.openConnection();
			dataReader.setRequestMethod("GET");
			dataReader.setRequestProperty("X-API-Key", "dd764f40");
			dataReader.setConnectTimeout(5000);
			dataReader.setReadTimeout(5000);
			
			//récuppération des données apres requête
			
			in = new BufferedReader(
					  new InputStreamReader(dataReader.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine);
					if (Compagny.BEAM_AIR.getName().equals(airName)) {
						content.append("\n");
					}
				}
			dataReader.disconnect();
			
		} catch (IOException e) {
			String error = e.getMessage(); 
		}
		
		try {
			if (!content.toString().isEmpty() || Compagny.BEAM_AIR.getName().equals(airName)) {
				String rawData = content.toString();
				if (!Compagny.BEAM_AIR.getName().equals(airName)) {
					if (Compagny.JAZZ_AIR.getName().equals(airName)) {
						//trick pour permettre au mapper de parser l'objet correctement
						// il était prévu de customizer un deserializer mais impossible d'en sortir une List
						// (voir FlightDataDeserializer.java)
						rawData = rawData.replace("atime", "arrival_time");
						rawData = rawData.replace("dtime", "departure_time");
					}
					//parsing des données Json
					flightDataList = mapper.readValue(rawData, new TypeReference<List<FlightData>>(){});
				} else {
					//parsing des données CSV
					//malgré de nombreuse tentative je n'ai pas réussi a exploiter le flux csv
					
					boolean isHeader = true;
					try (
							Reader reader = new StringReader(rawData);
							CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
									.withHeader("id", "price", "arrival_time", "departure_time")
									.withIgnoreHeaderCase()
									.withTrim());
							) {
						for (CSVRecord csvRecord : csvParser) {
							if (isHeader) {
								isHeader = false;
								continue;
							}
							FlightData flightData = new FlightData();
							flightData.setProvider(airName);
							flightData.setPrice(new Double(csvRecord.get(1)));
							flightData.setDeparture_time(csvRecord.get(2));
							flightData.setArrival_time(csvRecord.get(3));						  
							flightDataList.add(flightData);
						}
					}					
				}
			}
		} catch (IOException e) {
			String error = e.getMessage();
		}				

		for (FlightData  flightData: flightDataList) {
			flightData.setProvider(airName);
		}
		
		return flightDataList;
	}
}

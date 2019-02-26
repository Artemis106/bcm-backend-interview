package flightselect.flightdata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"provider", "price", "departure_time", "arrival_time"})
public class FlightData implements Comparable<FlightData> {

	//on empêche l'id d'être parsé
	@JsonIgnore
	private String id;
	private String provider;
	private Double price;
	private String departure_time;
	private String arrival_time;
	
	public FlightData() {
		
	}
	
		public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvider() {
		return provider;
	}
	
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDeparture_time() {
		return departure_time;
	}

	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}

	public String getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}

	@Override
	public String toString() {
		return "FlightData [provider=" + provider + ", price=" + price + ", departure_time=" + departure_time
				+ ", arrival_time=" + arrival_time + "]";
	}
	
	//rentre l'objet triable par l'attribut price
	@Override
	  public int compareTo(FlightData fd) {
	    if (getPrice() == 0 || fd.getPrice() == 0) {
	      return 0;
	    }
	    return getPrice().compareTo(fd.getPrice());
	  }
}

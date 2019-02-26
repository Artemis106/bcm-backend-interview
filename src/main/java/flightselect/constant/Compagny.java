package flightselect.constant;

public enum Compagny {
	
	JAZZ_AIR("Jazz-air", "https://my.api.mockaroo.com/air-jazz/flights"),
	MOON_AIR("Moon-air", "https://my.api.mockaroo.com/air-moon/flights"),
	BEAM_AIR("Beam-air", "https://my.api.mockaroo.com/air-beam/flights");

	private String name = "";
	private String url = "";
	
	Compagny(String name, String url) {
		this.name = name;
		this.url = url;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}
}

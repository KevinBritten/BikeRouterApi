package bikeRouterApi.model;

import java.util.ArrayList;
import java.util.List;

public class GeoJsonLineString {
	private String type = "LineString";
	private List<double[]> coordinates;

	public GeoJsonLineString() {
		this.coordinates = new ArrayList<>();
	}

	public GeoJsonLineString(List<double[]> coordinates) {
		this.coordinates = coordinates;
	}

	public String getType() {
		return type;
	}

	public List<double[]> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<double[]> coordinates) {
		this.coordinates = coordinates;
	}
}

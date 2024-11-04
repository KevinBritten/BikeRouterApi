package bikeRouterApi;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Route {
	@JsonProperty("_id")
	private String id; // Use ObjectId if MongoDB auto-generates IDs
	private String name;
	private GeoJsonLineString path; // Define a GeoJsonLineString for geospatial queries

	public Route() {
		// Default constructor
	}

	public Route(String name, List<double[]> coordinates) {
		this.name = name;
		this.path = new GeoJsonLineString(coordinates);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GeoJsonLineString getPath() {
		return path;
	}

	public void setPath(GeoJsonLineString path) {
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Route{id=" + id + ", name='" + name + "'}";
	}

	// Inner class to represent a GeoJSON LineString
	public static class GeoJsonLineString {
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
}

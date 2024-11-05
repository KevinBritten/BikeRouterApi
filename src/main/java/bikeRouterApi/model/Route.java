package bikeRouterApi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Route {
	@JsonProperty("_id")
	private String id;
	private String name;
	private GeoJsonLineString path;

	public Route() {
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

}

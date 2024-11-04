package bikeRouterApi;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Route {
	@JsonProperty("_id")
	private String id; // Map MongoDB's _id field to this property
	private String name;
	private ArrayList<Coord> path;

	public Route() {
		this.path = new ArrayList<>();
	}

	public Route(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Coord> getPath() {
		return path;
	}

	public void setPath(ArrayList<Coord> path) {
		this.path = path;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Route{id=" + id + ", name='" + name + "'}";
	}
}

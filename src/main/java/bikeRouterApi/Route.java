package bikeRouterApi;

import java.util.ArrayList;

public class Route {
	private static int nextId = 1;
	private int id;
	private String name;
	private ArrayList<Coord> path;

	public Route() {
		this.id = nextId++;
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

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Route{id=" + id + ", name='" + name + "'}";
	}
}

package bikeRouterApi;

public class Route {
	private static int nextId = 1;
	private int id;
	private String name;

	public Route(String name) {
		this.id = nextId++;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Route{id=" + id + ", name='" + name + "'}";
	}
}

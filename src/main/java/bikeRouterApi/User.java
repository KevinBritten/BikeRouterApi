package bikeRouterApi;

import java.util.ArrayList;

public class User {
	private static int nextId = 1;
	private int id;
	private String username;
	private String password;
	private ArrayList<Integer> routeIds;
	private ArrayList<String> routeNames;

	public User() {
	};

	public User(String username, String password) {
		this.id = nextId++;
		this.username = username;
		this.password = password;
		this.routeIds = new ArrayList<>();
		this.routeNames = new ArrayList<>();

	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<Integer> getRouteIds() {
		return routeIds;
	}

	public ArrayList<String> getRouteNames() {
		return routeNames;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void addRoute(int id, String name) {
		this.routeIds.add(id);
		this.routeNames.add(name);
	}

	public void removeRoute(int id, String name) {
		this.routeIds.remove(id);
		this.routeNames.remove(name);
	}

	@Override
	public String toString() {
		return "User{id=" + id + ", username='" + username + "', password='" + password + "'}";
	}
}

package bikeRouterApi;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	@JsonProperty("_id")
	private String id; // Map MongoDB's _id field to this property
	private String username;
	private String password;
	private ArrayList<Integer> routeIds;
	private ArrayList<String> routeNames;

	public User() {
	};

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.routeIds = new ArrayList<>();
		this.routeNames = new ArrayList<>();

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return "User{username='" + username + "', password='" + password + "'}";
	}
}

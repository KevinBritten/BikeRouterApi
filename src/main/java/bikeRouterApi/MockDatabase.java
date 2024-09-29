package bikeRouterApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MockDatabase {

	private static MockDatabase instance;

	private static Map<Integer, User> usersTable;
	private static Map<Integer, Route> routesTable;

	private MockDatabase() {
		usersTable = new HashMap<>();
		routesTable = new HashMap<>();
	}

	public static MockDatabase getInstance() {
		if (instance == null) {
			instance = new MockDatabase();
		}
		return instance;
	}

	public Map<Integer, User> getAllUsers() {
		return usersTable;
	}

	public User getUserByName(String username) {
		for (Map.Entry<Integer, User> entry : usersTable.entrySet()) {
			if (entry.getValue().username.equals(username)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public User getUserById(int id) {
		return usersTable.get(id);
	}

	public void addUser(User user) {
		usersTable.put(user.getId(), user);
	}

	public Map<Integer, Route> getAllRoutes() {
		return routesTable;
	}

	public Route getRouteById(int id) {
		return routesTable.get(id);
	}

	public void addRoute(Route route) {
		routesTable.put(route.getId(), route);
	}

	public void addRouteIdToUser(int userId, int routeId) {
		usersTable.get(userId).addRouteId(routeId);
	}

	public void removeRouteIdFromUser(int userId, int routeId) {
		usersTable.get(userId).removeRouteId(routeId);
	}

	public static class User {
		private static int nextId = 1;
		private int id;
		private String username;
		private String password;
		private ArrayList<Integer> routeIds;

		public User() {
		};

		public User(String username, String password) {
			this.id = nextId++;
			this.username = username;
			this.password = password;
			this.routeIds = new ArrayList<>();
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

		public void setUsername(String username) {
			this.username = username;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void addRouteId(int id) {
			this.routeIds.add(id);
		}

		public void removeRouteId(int id) {
			this.routeIds.remove(id);
		}

		@Override
		public String toString() {
			return "User{id=" + id + ", username='" + username + "', password='" + password + "'}";
		}
	}

	public static class Route {
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
}

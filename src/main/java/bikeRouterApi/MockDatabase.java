package bikeRouterApi;

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
			if (entry.getValue().getUsername().equals(username)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public User getUserById(int id) {
		if (usersTable.containsKey(id))
			return usersTable.get(id);
		return null;
	}

	public void addUser(User user) {

	}

	public Map<Integer, Route> getAllRoutes() {
		return routesTable;
	}

	public Route getRouteById(int id) {
		if (routesTable.containsKey(id))
			return routesTable.get(id);
		return null;
	}

	public void addRoute(Route route) {
		routesTable.put(route.getId(), route);
	}

	public void addRouteIdToUser(int userId, int routeId, String routeName) {
		usersTable.get(userId).addRoute(routeId, routeName);
	}

	public void removeRouteIdFromUser(int userId, int routeId, String routeName) {
		usersTable.get(userId).removeRoute(routeId, routeName);
	}

}

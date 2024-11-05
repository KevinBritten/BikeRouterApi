package bikeRouterApi.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;

import bikeRouterApi.database.MongoObjectMapper;
import bikeRouterApi.model.Route;
import bikeRouterApi.model.User;

public class MongoDBService {
	private MongoCollection<Document> collection;
	MongoObjectMapper objectMapper = new MongoObjectMapper();

	public MongoDBService(MongoCollection<Document> collection) {
		this.collection = collection;
		MongoObjectMapper objectMapper = new MongoObjectMapper();

	}

	public void insertUser(User user) throws Exception {
		Map<String, Object> userMap = objectMapper.convertValue(user, Map.class);
		userMap.remove("_id");
		Document userDocument = new Document(userMap);
		collection.insertOne(userDocument);
	}

	public void updateUser(ObjectId id, User updatedUser) throws Exception {
		Map<String, Object> userMap = objectMapper.convertValue(updatedUser, Map.class);
		userMap.remove("_id");
		Document updatedDocument = new Document(userMap);

		Document query = new Document("_id", id);
		Document update = new Document("$set", updatedDocument);

		collection.updateOne(query, update);
	}

	public User getUserByName(String username) throws Exception {
		Document query = new Document("username", username);
		Document userDocument = collection.find(query).first();

		if (userDocument != null) {
			User user = objectMapper.convertValue(userDocument, User.class);
			return user;
		}

		return null;
	}

	public User getUserById(ObjectId id) throws Exception {
		Document query = new Document("_id", id);
		Document userDocument = collection.find(query).first();
		if (userDocument != null) {
			User user = objectMapper.convertValue(userDocument, User.class);
			return user;
		}

		return null;
	}

	// routes

	public ObjectId addRoute(Route route) throws Exception {
		Map<String, Object> routeMap = objectMapper.convertValue(route, Map.class);
		routeMap.remove("_id"); // Ensure MongoDB sets the _id automatically
		Document routeDocument = new Document(routeMap);
		collection.insertOne(routeDocument);
		return routeDocument.getObjectId("_id");

	}

	public Route getRouteById(ObjectId routeId) throws Exception {
		Document query = new Document("_id", routeId);
		Document routeDocument = collection.find(query).first();

		if (routeDocument != null) {
			return objectMapper.convertValue(routeDocument, Route.class);
		}

		return null;
	}

	public List<Route> getRoutesWithinSquare(double[][] squareCoordinates) throws Exception {
		Document square = new Document("type", "Polygon").append("coordinates",
				List.of(List.of(List.of(squareCoordinates[0][0], squareCoordinates[0][1]),
						List.of(squareCoordinates[1][0], squareCoordinates[1][1]),
						List.of(squareCoordinates[2][0], squareCoordinates[2][1]),
						List.of(squareCoordinates[3][0], squareCoordinates[3][1]),
						List.of(squareCoordinates[0][0], squareCoordinates[0][1]))));
		Document query = new Document("path", new Document("$geoIntersects", new Document("$geometry", square)));
		List<Route> routes = new ArrayList<>();
		for (Document routeDocument : collection.find(query)) {
			Route route = objectMapper.convertValue(routeDocument, Route.class);
			routes.add(route);
		}
		return routes;
	}
}

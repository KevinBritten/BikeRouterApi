package bikeRouterApi;

import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;

public class MongoDBService {
	private MongoCollection<Document> collection;
	MongoObjectMapper objectMapper = new MongoObjectMapper();

	public MongoDBService(MongoCollection<Document> collection) {
		this.collection = collection;
		MongoObjectMapper objectMapper = new MongoObjectMapper();

	}

	public void insertUser(User user) throws Exception {
		Map<String, Object> userMap = objectMapper.convertValue(user, Map.class);
		// remove id so mongo can set it automatically
		userMap.remove("_id");
		Document userDocument = new Document(userMap);
		collection.insertOne(userDocument);
	}

	public void updateUser(ObjectId id, User updatedUser) throws Exception {
		Map<String, Object> userMap = objectMapper.convertValue(updatedUser, Map.class);
		userMap.remove("_id"); // Ensure MongoDB handles the _id field
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
		// Convert Route to Document and insert it
		Map<String, Object> routeMap = objectMapper.convertValue(route, Map.class);
		routeMap.remove("_id"); // Ensure MongoDB sets the _id automatically
		Document routeDocument = new Document(routeMap);
		collection.insertOne(routeDocument);

		// Get the ObjectId of the inserted route
		return routeDocument.getObjectId("_id");

	}

}

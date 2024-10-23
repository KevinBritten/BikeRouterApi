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
		userMap.remove("id");
		Document userDocument = new Document(userMap);
		collection.insertOne(userDocument);
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

}

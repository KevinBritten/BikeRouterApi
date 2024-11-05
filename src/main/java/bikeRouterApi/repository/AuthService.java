package bikeRouterApi.repository;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

public class AuthService {

	private final MongoCollection<Document> collection;

	public AuthService(MongoCollection<Document> collection) {
		this.collection = collection;
	}

	public Document getUserByToken(String authHeader) {
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring("Bearer ".length()) : authHeader;

		Document query = new Document("authToken", token);
		Document user = collection.find(query).first();

		return user;
	}

}

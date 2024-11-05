package bikeRouterApi.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
	private static MongoClient mongoClient;
	private static MongoDatabase database;

	static {

		String connectionString = "mongodb://localhost:27017";
		mongoClient = MongoClients.create(connectionString);

		database = mongoClient.getDatabase("bikeRouter");
	}

	public static MongoDatabase getDatabase() {
		return database;
	}

	public static void closeConnection() {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}
}

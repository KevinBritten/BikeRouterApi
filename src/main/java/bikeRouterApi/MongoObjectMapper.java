package bikeRouterApi;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class MongoObjectMapper extends ObjectMapper {

	public MongoObjectMapper() {
		SimpleModule module = new SimpleModule();
		module.addSerializer(ObjectId.class, new ObjectIdSerializer());
		module.addDeserializer(ObjectId.class, new ObjectIdDeserializer());
		this.registerModule(module);
	}
}

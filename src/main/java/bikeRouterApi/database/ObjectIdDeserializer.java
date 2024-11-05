package bikeRouterApi.database;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ObjectIdDeserializer extends StdDeserializer<ObjectId> {

	public ObjectIdDeserializer() {
		this(null);
	}

	public ObjectIdDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public ObjectId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		return new ObjectId(p.getValueAsString()); // Convert string to ObjectId
	}
}

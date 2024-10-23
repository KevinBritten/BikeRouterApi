package bikeRouterApi;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ObjectIdSerializer extends StdSerializer<ObjectId> {

	public ObjectIdSerializer() {
		this(null);
	}

	public ObjectIdSerializer(Class<ObjectId> t) {
		super(t);
	}

	@Override
	public void serialize(ObjectId value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeString(value.toHexString()); // Convert ObjectId to its hexadecimal string representation
	}
}

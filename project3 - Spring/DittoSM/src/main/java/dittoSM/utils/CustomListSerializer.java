package dittoSM.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import dittoSM.model.UserAccount;

/**
 * 
 * @author Jae Kyoung Lee (LJ)
 *
 */
public class CustomListSerializer extends StdSerializer<UserAccount> {

	
	public CustomListSerializer() {
		this(null);
	}

	public CustomListSerializer(Class<UserAccount> t) {
		super(t);
	}

	@Override
	public void serialize(UserAccount user, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {	

		generator.writeStartObject();
		generator.writeNumberField("userId",user.getUserId());
		generator.writeStringField("username", user.getUsername());
		generator.writeStringField("firstName", user.getFirstName());
		generator.writeStringField("lastName", user.getLastName());
		generator.writeEndObject();
	}

}

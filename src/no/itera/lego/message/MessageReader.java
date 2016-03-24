package no.itera.lego.message;

import java.io.StringReader;
import java.lang.UnsupportedOperationException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MessageReader {

  public static Message readJson(String json) {

    JSONParser parser = new JSONParser();
    Object obj;
    try {
      obj = parser.parse(json);
    } catch (ParseException e) {
      throw new UnsupportedOperationException(String.format("Could not parse message as json: '%s'", json));
    }

    if (obj instanceof JSONObject) {
      JSONObject object = (JSONObject) obj;

      String type = (String)object.get("type");

      if (type.equals(Register.TYPE)) {
        return Register.fromJson(object);
      } else if (type.equals(Status.TYPE)) {
        return Status.fromJson(object);
      } else if (type.equals(Update.TYPE)) {
        return Update.fromJson(object);
      } else {
        throw new UnsupportedOperationException(String.format("Unsupported message type: '%s'", type));
      }
    }
    throw new UnsupportedOperationException(String.format("Message was not a JSON object: '%s'", json));
  }
}

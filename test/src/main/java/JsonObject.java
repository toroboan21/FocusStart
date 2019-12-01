import java.util.HashMap;
import java.util.Map;

public class JsonObject {
    private static final Map<String, String> value = new HashMap<>();

    public void setValue(String key, String value) {
        JsonObject.value.put(key, value);
    }

    public String getValue(String key) {
        return value.get(key);
    }
}

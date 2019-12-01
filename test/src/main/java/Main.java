public class Main {
    public static void main(String[] args) {
        String json = "{\"key1\":\"1\",\"key2\":\"2\"}";
        JsonObject result = JsonParser.parse(json);
        System.out.println(result.getValue("key1"));
        System.out.println("key2");
    }
}

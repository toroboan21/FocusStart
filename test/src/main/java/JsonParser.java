public class JsonParser {
    private static final String JSON_OBJECT_START_SIMBOL = "{";
    private static final String JSON_OBJECT_END_SYMBOL = "}";
    private static final String KEY_VALUE_SEPARATOR = ":";
    private static final String QUOTE = "\"";
    private static final String ARRAY_START_SYMBOL = "[";
    private static final String ARRAY_END_SEPARATOR = "]";
    private static final String ROW_SEPARATOR = ",";


    public static JsonObject parse(String json) {
        String[] jsonArray = json.split("");

        checkFirstSymbol(jsonArray[0]);

        JsonObject jsonObject = new JsonObject();

        for (int i = 0; i <= jsonArray.length; ) {
            String currentSymbol = jsonArray[i];
            int offset = 0;
            switch (currentSymbol) {
                case QUOTE:
                    offset = parseKeyValuePair(jsonObject, jsonArray, i);
                    break;
                case ARRAY_START_SYMBOL:
                    offset = parseArray(jsonObject, jsonArray, i);
                    break;
                case JSON_OBJECT_START_SIMBOL:
                    offset = parseObject(jsonObject, jsonArray, i);
                    break;
                default:
                    throw new IllegalArgumentException("По индексу" + jsonArray[i] +
                            "обнаружен некорректный символ! Не удалось завершить парсинг JSON");
            }
            i += offset;
        }
        return jsonObject;
    }

    private static int parseObject(JsonObject jsonObject, String[] jsonArray, int i) {
        return 0;
    }

    private static int parseArray(JsonObject jsonObject, String[] jsonArray, int i) {
        return 0;
    }

    private static int parseKeyValuePair(JsonObject jsonObject, String[] jsonArray, int startIndex) {
        StringBuilder keyBuilder = new StringBuilder();
        int currentIndex = parseKey(jsonArray, startIndex, keyBuilder);
        StringBuilder valueBuilder = new StringBuilder();
        currentIndex = parseKey(jsonArray, currentIndex, valueBuilder);
        jsonObject.setValue(keyBuilder.toString(), valueBuilder.toString());
        return currentIndex;
    }

    private static int parseKey(String[] jsonArray, int startIndex, StringBuilder builder) {
        int currentIndex = startIndex;
        for (int i = currentIndex; ; ) {
            currentIndex++;
            if (jsonArray[i].equals(QUOTE)) {
                break;
            }
            builder.append(jsonArray[i]);
        }
        return currentIndex + 1;
    }

    private static void checkFirstSymbol(String json) {
        String firstSymbol = Character.toString(json.charAt(0));
        if (!firstSymbol.equals(JSON_OBJECT_START_SIMBOL)) {
            throw new IllegalArgumentException("JSON должен начинаться с символа " + JSON_OBJECT_START_SIMBOL + "!");
        }
    }
}

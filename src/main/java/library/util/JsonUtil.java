package library.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import library.model.Library;

import java.io.File;
import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void exportToJson(Library library, String filePath) throws IOException {
        mapper.writeValue(new File(filePath), library);
    }

    public static Library importFromJson(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), Library.class);
    }
}
package org.example.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exceptions.InvalidTestDataException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonFile {
    private static final Logger LOGGER = LogManager.getLogger(JsonFile.class.getName());

    public static Map<String, Map> getNodeValueAsMapFromJsonFile(String fileName, String node){
        Map<String, Map> map = loadJsonFile(fileName);
        LOGGER.info("\tNode: " + node);
        Map<String, Map> envMap = map.get(node);
        LOGGER.info("\tLoaded map: " + envMap);
        if(null == envMap) {
            throw new InvalidTestDataException(
                    String.format("Node: '%s' not found in file: '%s'", node, fileName));
        }
        return envMap;
    }
    private static Map<String, Map> loadJsonFile(String fileName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Map> jsonMap = mapper.readValue(new File(fileName), Map.class);

            // Access JSON data
            LOGGER.info("Parsed JSON: " + jsonMap);
            return jsonMap;
        } catch (IOException e) {
            throw new InvalidTestDataException(
                    String.format("Unable to load json file: '%s'", fileName), e);
        }
    }

}

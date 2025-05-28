package service;

import exceptions.DataProcessingException;
import model.UsageRecord;
import model.UserData;
import java.util.HashMap;
import java.util.Map;

public class DataProcessor {
    //Hashmap that stores "9000600600": UserData{mobileNumber: "9000600600",g4Home: 100,g5Home: 200,g4Roaming: 50,g5Roaming: 100}
    private Map<String, UserData> userMap = new HashMap<>();

    public void processRecord(UsageRecord record) throws DataProcessingException {
    try {
        userMap.computeIfAbsent(record.getMobileNumber(), UserData::new)
               .addUsage(record);
    } catch (IllegalArgumentException e) {
        throw new DataProcessingException("Failed to process record: " + e.getMessage(), e);
    }
}

    public Map<String, UserData> getUserData() {
        return userMap;
    }
}

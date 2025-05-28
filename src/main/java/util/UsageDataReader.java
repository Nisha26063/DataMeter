package util;

import model.UsageRecord;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsageDataReader {  // Changed from FileReader to UsageDataReader
    public List<UsageRecord> readFile(String filePath) throws IOException {
        List<UsageRecord> records = new ArrayList<>();
        //Code works even when 'o' used instead of 0
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    int g4 = "o".equals(parts[2]) ? 0 : Integer.parseInt(parts[2]);
                    int g5 = "o".equals(parts[3]) ? 0 : Integer.parseInt(parts[3]);
                    boolean roaming = "Yes".equals(parts[4]);
                    
                    records.add(new UsageRecord(parts[0], g4, g5, roaming));
                }
            }
        }
        return records;
    }
}
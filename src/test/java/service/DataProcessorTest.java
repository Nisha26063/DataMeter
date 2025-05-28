package service;
import exceptions.DataProcessingException;

import model.UsageRecord;
import model.UserData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataProcessorTest {
    @Test
public void testProcessRecord() throws DataProcessingException {
    DataProcessor processor = new DataProcessor();
    
    // Home usage
    processor.processRecord(new UsageRecord("9000600600", 100, 200, false));
    // Roaming usage
    processor.processRecord(new UsageRecord("9000600600", 50, 100, true));
    
    UserData user = processor.getUserData().get("9000600600");
    assertNotNull(user);
    assertEquals(100, user.getG4Home());
    assertEquals(200, user.getG5Home());
    assertEquals(50, user.getG4Roaming());
    assertEquals(100, user.getG5Roaming());
}

}
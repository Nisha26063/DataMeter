package service;
import exceptions.*;
import model.UsageRecord;
import model.UserData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.*;

class DataProcessorTest {
    
    private DataProcessor processor;
    
    @BeforeEach
    void setUp() {
        processor = new DataProcessor();
    }

    @Test
    void shouldProcessValidRecord() throws DataProcessingException {
        UsageRecord record = new UsageRecord("9000600600", 1000, 2000, false);
        
        processor.processRecord(record);
        UserData user = processor.getUserData().get("9000600600");
        
        assertThat(user).isNotNull();
        assertThat(user.getG4Home()).isEqualTo(1000);
        assertThat(user.getG5Home()).isEqualTo(2000);
    }

    
    @Test
    void shouldAccumulateRecords() throws DataProcessingException {
        processor.processRecord(new UsageRecord("9000600600", 1000, 2000, false));
        processor.processRecord(new UsageRecord("9000600600", 500, 1000, true));
        
        UserData user = processor.getUserData().get("9000600600");
        assertThat(user.getG4Home()).isEqualTo(1000);
        assertThat(user.getG5Home()).isEqualTo(2000);
        assertThat(user.getG4Roaming()).isEqualTo(500);
        assertThat(user.getG5Roaming()).isEqualTo(1000);
    }

    @Test
    void shouldHandleMultipleUsers() throws DataProcessingException {
        processor.processRecord(new UsageRecord("9000600600", 1000, 2000, false));
        processor.processRecord(new UsageRecord("9000600601", 500, 1000, true));
        
        assertThat(processor.getUserData())
            .hasSize(2)
            .containsKeys("9000600600", "9000600601");
    }

    
}
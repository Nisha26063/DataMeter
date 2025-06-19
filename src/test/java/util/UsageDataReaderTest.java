package util;

import model.UsageRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UsageDataReaderTest {
    
    @TempDir
    Path tempDir;
    private UsageDataReader reader;
    
    @BeforeEach
    void setUp() {
        reader = new UsageDataReader();
    }
    
    @Test
    void shouldReadValidFile() throws IOException {
        Path tempFile = tempDir.resolve("test.txt");
        Files.writeString(tempFile, 
            "9000600600|TOWER1|1000|2000|No\n" +
            "9000600601|TOWER2|500|1000|Yes\n" +
            "9000600602|TOWER3|0|0|No\n");
        
        List<UsageRecord> records = reader.readFile(tempFile.toString());
        
        assertThat(records)
            .hasSize(3)
            .extracting(UsageRecord::getMobileNumber)
            .containsExactly("9000600600", "9000600601", "9000600602");
        
        assertThat(records.get(1).isRoaming()).isTrue();
        assertThat(records.get(2).getG4Usage()).isZero();
    }

    @Test
    void shouldSkipInvalidLines() throws IOException {
        Path tempFile = tempDir.resolve("test.txt");
        Files.writeString(tempFile, 
            "invalid|data|format\n" +
            "9000600600|TOWER1|1000|2000|No\n");
        
        List<UsageRecord> records = reader.readFile(tempFile.toString());
        
        assertThat(records)
            .hasSize(1)
            .extracting(UsageRecord::getMobileNumber)
            .containsExactly("9000600600");
    }

    @Test
    void shouldThrowOnMissingFile() {
        assertThatThrownBy(() -> reader.readFile("nonexistent_file.txt"))
            .isInstanceOf(FileNotFoundException.class);
    }
}
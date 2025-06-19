package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;

class UsageRecordTest {
    
    @Test
    void shouldCreateValidRecord() {
        UsageRecord record = new UsageRecord("9000600600", 1000, 2000, false);
        
        assertThat(record.getMobileNumber()).isEqualTo("9000600600");
        assertThat(record.getG4Usage()).isEqualTo(1000);
        assertThat(record.getG5Usage()).isEqualTo(2000);
        assertThat(record.isRoaming()).isFalse();
    }

    
    @ParameterizedTest
    @NullAndEmptySource
    void shouldRejectNullOrEmptyNumbers(String invalidNumber) {
        assertThatThrownBy(() -> new UsageRecord(invalidNumber, 100, 200, false))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Mobile number cannot be null or empty");
    }

    

          

    @ParameterizedTest
    @ValueSource(strings = {
        "9000600600",    // standard
        "9876543210",    // another valid
        "7000000000"     // edge case
    })
    void shouldAcceptValidMobileNumbers(String validNumber) {
        assertThatCode(() -> new UsageRecord(validNumber, 100, 200, false))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource({
        "-100, 200",
        "100, -200",
        "-1, -1"
    })
    void shouldRejectNegativeUsage(int g4Usage, int g5Usage) {
        assertThatThrownBy(() -> new UsageRecord("9000600600", g4Usage, g5Usage, false))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Usage values cannot be negative");
    }

    @Test
    void shouldAcceptZeroUsage() {
        UsageRecord record = new UsageRecord("9000600600", 0, 0, false);
        
        assertThat(record.getG4Usage()).isZero();
        assertThat(record.getG5Usage()).isZero();
    }

    @Test
    void shouldHandleRoamingFlag() {
        UsageRecord homeRecord = new UsageRecord("9000600600", 100, 200, false);
        UsageRecord roamingRecord = new UsageRecord("9000600600", 100, 200, true);
        
        assertThat(homeRecord.isRoaming()).isFalse();
        assertThat(roamingRecord.isRoaming()).isTrue();
    }
}
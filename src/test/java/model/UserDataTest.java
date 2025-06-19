package model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class UserDataTest {
    
    @Test
    void shouldAddHomeUsage() {
        UserData user = new UserData("9000600600");
        user.addUsage(new UsageRecord("9000600600", 1000, 2000, false));
        
        assertThat(user.getG4Home()).isEqualTo(1000);
        assertThat(user.getG5Home()).isEqualTo(2000);
        assertThat(user.getG4Roaming()).isZero();
        assertThat(user.getG5Roaming()).isZero();
    }

    @Test
    void shouldAddRoamingUsage() {
        UserData user = new UserData("9000600600");
        user.addUsage(new UsageRecord("9000600600", 500, 1000, true));
        
        assertThat(user.getG4Home()).isZero();
        assertThat(user.getG5Home()).isZero();
        assertThat(user.getG4Roaming()).isEqualTo(500);
        assertThat(user.getG5Roaming()).isEqualTo(1000);
    }

    @Test
    void shouldRejectMobileNumberMismatch() {
        UserData user = new UserData("9000600600");
        UsageRecord record = new UsageRecord("9000600601", 1000, 2000, false);
        
        assertThatThrownBy(() -> user.addUsage(record))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Mobile number mismatch");
    }

    @Test
    void shouldAccumulateMultipleRecords() {
        UserData user = new UserData("9000600600");
        user.addUsage(new UsageRecord("9000600600", 1000, 2000, false));
        user.addUsage(new UsageRecord("9000600600", 500, 1000, true));
        user.addUsage(new UsageRecord("9000600600", 2000, 3000, false));
        
        assertThat(user.getG4Home()).isEqualTo(3000);
        assertThat(user.getG5Home()).isEqualTo(5000);
        assertThat(user.getG4Roaming()).isEqualTo(500);
        assertThat(user.getG5Roaming()).isEqualTo(1000);
    }
}
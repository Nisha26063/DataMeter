package service;
import exceptions.*;
import model.UserData;
import model.UsageRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.data.Offset.offset;

class BillingServiceTest {
    
    private BillingService billingService;
    private Properties testConfig;
    
    @BeforeEach
    void setUp() throws BillingException {
        testConfig = new Properties();
        testConfig.setProperty("g4.rate", "0.05");
        testConfig.setProperty("g5.rate", "0.08");
        testConfig.setProperty("roaming.g4.multiplier", "1.10");
        testConfig.setProperty("roaming.g5.multiplier", "1.15");
        testConfig.setProperty("overage.multiplier", "1.05");
        testConfig.setProperty("overage.threshold", "1000000");
        
        billingService = new BillingService(testConfig);
    }
    
    @Test
    void shouldCalculateHomeUsageOnly() throws BillingException {
        UserData user = new UserData("9000600600");
        user.addUsage(new UsageRecord("9000600600", 10000, 20000, false));
        
        assertThat(billingService.calculateBill(user))
            .isCloseTo(2100.00, offset(0.001));
    }

    @Test
    void shouldApplyRoamingSurcharge() throws BillingException {
        UserData user = new UserData("9000600600");
        user.addUsage(new UsageRecord("9000600600", 10000, 0, false));
        user.addUsage(new UsageRecord("9000600600", 0, 10000, true));
        
        assertThat(billingService.calculateBill(user))
            .isCloseTo(1420.00, offset(0.001));
    }

    @Test
    void shouldApplyOverageCharge() throws BillingException {
        UserData user = new UserData("9000600600");
        user.addUsage(new UsageRecord("9000600600", 1000001, 0, false));
        
        assertThat(billingService.calculateBill(user))
            .isCloseTo(52500.0525, offset(0.001));
    }

    @Test
    void shouldHandleExactThreshold() throws BillingException {
        UserData user = new UserData("9000600600");
        user.addUsage(new UsageRecord("9000600600", 1000000, 0, false));
        
        assertThat(billingService.calculateBill(user))
            .isCloseTo(50000.00, offset(0.001));
    }

    @Test
    void shouldHandleZeroUsage() throws BillingException {
        UserData user = new UserData("9000600600");
        
        assertThat(billingService.calculateBill(user))
            .isCloseTo(0.00, offset(0.001));
    }

    
}
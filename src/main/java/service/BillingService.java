package service;
import exceptions.BillingException;
import model.UserData;
import java.util.Properties;

public class BillingService {
    private final double g4Rate;
    private final double g5Rate;
    private final double roamingG4Multiplier;
    private final double roamingG5Multiplier;
    private final double overageMultiplier;
    private final int overageThreshold;

    public BillingService(Properties config) {
        this.g4Rate = Double.parseDouble(config.getProperty("g4.rate"));
        this.g5Rate = Double.parseDouble(config.getProperty("g5.rate"));
        this.roamingG4Multiplier = Double.parseDouble(config.getProperty("roaming.g4.multiplier"));
        this.roamingG5Multiplier = Double.parseDouble(config.getProperty("roaming.g5.multiplier"));
        this.overageMultiplier = Double.parseDouble(config.getProperty("overage.multiplier"));
        this.overageThreshold = Integer.parseInt(config.getProperty("overage.threshold"));
    }

    public double calculateBill(UserData user) throws BillingException {
        // Convert KB to MB (divide by 1024) for more reasonable billing
        double total = 0;
        try{
        // Home usage (convert KB to MB)
        total += (user.getG4Home()) * g4Rate;
        total += (user.getG5Home()) * g5Rate;
        
        // Roaming usage (convert KB to MB and apply premium)
        total += (user.getG4Roaming()) * g4Rate * roamingG4Multiplier;
        total += (user.getG5Roaming()) * g5Rate * roamingG5Multiplier;
        
        // Overage check
        int totalUsageKB = user.getG4Home() + user.getG5Home() + 
                         user.getG4Roaming() + user.getG5Roaming();
        
        if (totalUsageKB > overageThreshold) {
            total *= overageMultiplier;
        }
        return total;
        }
        catch (Exception e) {
            throw new BillingException("Error calculating bill for " + user.getMobileNumber(), e);
        }
    }
}
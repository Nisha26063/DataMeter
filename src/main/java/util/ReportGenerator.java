package util;

import model.UserData;
import service.BillingService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import exceptions.BillingException;



public class ReportGenerator {
    public void generateReport(String outputPath, Map<String, UserData> userData, BillingService billingService) 
            throws IOException {
        
        // Create parent directories if they don't exist
        Path path = Paths.get(outputPath);
        Files.createDirectories(path.getParent());
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
        writer.write("Mobile Number|4G|5G|4G Roaming|5G Roaming|Cost\n");
        
        for (UserData user : userData.values()) {
            try {
                double cost = billingService.calculateBill(user);
                writer.write(String.format("%s|%d|%d|%d|%d|%.2f%n",
                    user.getMobileNumber(),
                    user.getG4Home(),
                    user.getG5Home(),
                    user.getG4Roaming(),
                    user.getG5Roaming(),
                    cost));
            } catch (BillingException e) {
                System.err.println("Skipping invalid billing for " + user.getMobileNumber());
                writer.write(String.format("%s|%d|%d|%d|%d|ERROR%n",
                    user.getMobileNumber(),
                    user.getG4Home(),
                    user.getG5Home(),
                    user.getG4Roaming(),
                    user.getG5Roaming()));
            }
        }
    }
    }
}
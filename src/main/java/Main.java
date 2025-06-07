import model.UsageRecord;
import service.BillingService;
import service.DataProcessor;
import util.UsageDataReader;
import util.ReportGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import exceptions.DataProcessingException;

public class Main {
    public static void main(String[] args) {
        try {
            // Load configuration
            Properties config = new Properties();
            try (FileInputStream input = new FileInputStream("src\\main\\resources\\config.properties")) {
                config.load(input);
            }

            // Initialize services
            DataProcessor processor = new DataProcessor();
            BillingService billingService = new BillingService(config);
            UsageDataReader usageDataReader = new UsageDataReader();
            ReportGenerator reportGenerator = new ReportGenerator();

            // Process input files
            // Process input files
            List<UsageRecord> records = usageDataReader.readFile("input/data.txt");
            for (UsageRecord record : records) {
                try {
                    processor.processRecord(record);
                } catch (DataProcessingException e) {
                    System.err.println("Failed to process record: " + e.getMessage());
                    e.printStackTrace();
                }
            }



            // Generate report
            reportGenerator.generateReport(
                "output/report.txt", 
                processor.getUserData(), 
                billingService);

            System.out.println("Report generated successfully!");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
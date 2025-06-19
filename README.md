# Data-Meter

### Problem Statement
Telecom operators collect large volumes of data usage logs from mobile towers, detailing consumption across different technologies (4G/5G) and scenarios (Home/Roaming). The goal is to:
1. Process multiple large log files (100K–200K rows each).
2. Aggregate data usage per mobile number, categorized as:<br>
4G Home<br>
5G Home<br>
4G Roaming<br>
5G Roaming<br>
3. Calculate billing costs with:<br>
Different rates for 4G/5G<br>
Roaming premiums (10% for 4G, 15% for 5G)<br>
Overage charges (5% if usage exceeds a threshold)

### Solution
The code processes the files, Aggregates the data usage for all mobile numbers using HashMap, Calculates the cost and generates the final report

### Components
<br>
UsageRecord.java: Represents a single log entry<br>
UserData.java: Stores aggregated usage per mobile number<br>
DataProcessor.java: Aggregates usage records into UserData objects<br>
BillingService.java: Calculates costs<br>
UsageDataReader.java: Reads and parses input files<br>
ReportGenerator.java: Writes the final report<br>
BillingExceptionjava: Handles billing exceptions<br>
DataProcessingException.java: Handles Data processing exceptions<br>

### Maven : used here for Dependency Management ,Built and Compile automation and unit Testing.<br>

### TO RUN THE CODE:<br>
1. Install maven and Java 21<br>
2. Clone the repo<br>
3. Insert /input/data.txt<br>
4. built and run : mvn clean install<br>
                   java -cp target/classes Main<br>
5. Check report in /output/report.txt<br>

### Developed with: Java 11
### Compatible with: Java 11+
### Tested on: JDK 21 

### For Configuration:<br>
edit /src/main/resources/config.properties<br>
Rates per KB  <br>
g4.rate=0.1  <br>
g5.rate=0.2  <br>

Roaming premiums  <br>
roaming.g4.multiplier=1.10  <br>
roaming.g5.multiplier=1.15  <br>

Overage (5% extra if usage > 100MB)  <br>
overage.multiplier=1.05  <br>
overage.threshold=100000  <br>
<br>
### For Unit testing: run mvn test

package model;


public class UserData {
    private String mobileNumber;
    private int g4Home = 0;
    private int g5Home = 0;
    private int g4Roaming = 0;
    private int g5Roaming = 0;

    public UserData(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void addUsage(UsageRecord record) {
        //check for mismatch in mobile number
        if (!mobileNumber.equals(record.getMobileNumber())) {
            throw new IllegalArgumentException("Mobile number mismatch");
        }
        if (record.isRoaming()) {
            g4Roaming += record.getG4Usage();
            g5Roaming += record.getG5Usage();
        } else {
            g4Home += record.getG4Usage();
            g5Home += record.getG5Usage();
        }
    }

    // Getters to access the private data values
    public String getMobileNumber() { return mobileNumber; }
    public int getG4Home() { return g4Home; }
    public int getG5Home() { return g5Home; }
    public int getG4Roaming() { return g4Roaming; }
    public int getG5Roaming() { return g5Roaming; }
}
package model;

public class UsageRecord {
    private String mobileNumber;
    private int g4Usage;
    private int g5Usage;
    private boolean isRoaming;

    public UsageRecord(String mobileNumber, int g4Usage, int g5Usage, boolean isRoaming) {
        //Exception handling for checking the validity of mobile number
        if (mobileNumber == null || mobileNumber.length() != 10) {
            throw new IllegalArgumentException("Mobile number must be 10 digits");
        }
        //Exception handling for negative values
        if (g4Usage < 0 || g5Usage < 0) {
            throw new IllegalArgumentException("Usage values cannot be negative");
        }
        this.mobileNumber = mobileNumber;
        this.g4Usage = g4Usage;
        this.g5Usage = g5Usage;
        this.isRoaming = isRoaming;
    }

    // Getters to access the private type data
    public String getMobileNumber() { return mobileNumber; }
    public int getG4Usage() { return g4Usage; }
    public int getG5Usage() { return g5Usage; }
    public boolean isRoaming() { return isRoaming; }
}

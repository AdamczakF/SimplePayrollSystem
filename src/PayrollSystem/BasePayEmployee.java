package PayrollSystem;

public class BasePayEmployee extends Employee{

    private double hourlyRate;

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double rate)
    {
        if(rate >= 0 && rate <= Double.MAX_VALUE)
        {
            this.hourlyRate = rate;
        }
        else
        {
            throw new IllegalArgumentException(String.format(FAILED_EMPLOYEE_UPDATE_MESSAGE, "hourly rate", getFirstName(), getLastName(), "Hourly rate too small/large"));
        }
    }

    @Override
    public double calculateEarnings(double hours) {
        return hours*getHourlyRate();
    }

    @Override
    public void populateEmployeeFromFile(String[] data, EmployeeReader reader) {

        this.setFirstName(data[1]);
        this.setLastName(data[2]);
        this.setAge(Integer.parseInt(data[3]));
        this.setId(Integer.parseInt(data[4]), reader);
        this.setHourlyRate(Double.parseDouble(data[5]));
        this.setBalance(Double.parseDouble(data[6]));

    }

    @Override
    public String toString()
    {
        return super.toString() + "\nHourly Rate: " + getHourlyRate();
    }

    @Override
    public String outputFileFormat()
    {
        return String.format("\"%s\", \"%s\", \"%s\", %s, %s, %s, %s", getClass().getSimpleName(), getFirstName(), getLastName(), getAge(), getIdNumber(), getHourlyRate(), getAccountBalance());
    }
}

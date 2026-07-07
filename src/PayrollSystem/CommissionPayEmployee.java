package PayrollSystem;

public class CommissionPayEmployee extends Employee{

    @Override
    public void populateEmployeeFromFile(String[] data, EmployeeReader reader)
    {
        this.setFirstName(data[1]);
        this.setLastName(data[2]);
        this.setAge(Integer.parseInt(data[3]));
        this.setId(Integer.parseInt(data[4]), reader);
        this.setBalance(Double.parseDouble(data[5]));
    }

    @Override
    public double calculateEarnings(double commission)
    {
        return commission;
    }

    @Override
    public String outputFileFormat()
    {
        return String.format("\"%s\", \"%s\", \"%s\", %s, %s, %s", getClass().getSimpleName(), getFirstName(), getLastName(), getAge(), getIdNumber(), getAccountBalance());
    }
}
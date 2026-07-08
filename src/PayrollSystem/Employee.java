package PayrollSystem;

import java.util.ArrayList;

public abstract class Employee {
    private String firstName;
    private String lastName;
    private int age;
    private int idNumber;
    private double accountBalance;

    public static final String FAILED_EMPLOYEE_UPDATE_MESSAGE = Visuals.RED + Visuals.ITALIC + "Failed to assign %s to %s %s.\nReason: %s\n" + Visuals.RESET;
    public static final String SUCCESSFUL_EMPLOYEE_UPDATE_MESSAGE = Visuals.GREEN + "Successfully updated employee details" + Visuals.RESET;

    public Employee()
    {

    }

    public Employee(String firstName, String lastName, int age, int idNumber, double accountBalance, EmployeeReader reader)
    {
        setFirstName(firstName);
        setLastName(lastName);
        setAge(age);
        setId(idNumber, reader);
        setBalance(accountBalance);
    }

    private boolean isValidName(String name)
    {
        boolean valid = (name != null && name.length() <= 20 && !name.isEmpty());

        for(int index = 0; valid && index < name.length(); index++)
        {
            char currentChar = name.charAt(index);
            if((currentChar < 'A' || currentChar > 'z') && currentChar != '\'')
            {
                valid = false;
            }
        }
        return valid;
    }

    public String getFirstName()
    {
        return firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public int getAge() {
        return age;
    }
    public int getIdNumber() {
        return idNumber;
    }
    public double getAccountBalance() {
        return accountBalance;
    }

    public void setFirstName(String firstName)
    {
        if(isValidName(firstName))
        {
            this.firstName = firstName;
        }
        else
        {
            throw new IllegalArgumentException(String.format(FAILED_EMPLOYEE_UPDATE_MESSAGE, "first name", getFirstName(), getLastName(), "First name cannot be empty and can only contain letters"));
        }
    }

    public void setLastName(String lastName)
    {
        if(isValidName(lastName))
        {
            this.lastName = lastName;
        }
        else
        {
            throw new IllegalArgumentException(String.format(FAILED_EMPLOYEE_UPDATE_MESSAGE, "last name", getFirstName(), getLastName(), "Last name cannot be empty and can only contain letters"));
        }
    }

    public void setAge(int age)
    {
        if(age > 0 && age < 150)
        {
            this.age = age;
        }
        else
        {
            throw new IllegalArgumentException(String.format(FAILED_EMPLOYEE_UPDATE_MESSAGE, "age", getFirstName(), getLastName(), "Age too small/large"));
        }
    }

    public void setId(int id, EmployeeReader reader)
    {
        if(id > 0 && id < Integer.MAX_VALUE)
        {
            boolean isUnique = true;
            ArrayList<Employee> employees = reader.getEmployees();
            for(int index = 0; index < employees.size() && isUnique; index++)
            {
                if(employees.get(index).getIdNumber() == id)
                {
                    isUnique = false;
                }
            }
            if(isUnique)
            {
                this.idNumber = id;
            }
            else
            {
                throw new IllegalArgumentException(String.format(FAILED_EMPLOYEE_UPDATE_MESSAGE, "ID number", getFirstName(), getLastName(), "ID number must be unique"));
            }
        }
        else
        {
            throw new IllegalArgumentException(String.format(FAILED_EMPLOYEE_UPDATE_MESSAGE, "ID number", getFirstName(), getLastName(), "ID number too small/large"));
        }
    }

    public void setBalance(double balance)
    {
        if(balance >= 0 && balance < Double.MAX_VALUE)
        {
            this.accountBalance = balance;
        }
        else
        {
            throw new IllegalArgumentException(String.format(FAILED_EMPLOYEE_UPDATE_MESSAGE, "balance", getFirstName(), getLastName(), "Balance too small/large"));
        }
    }

    @Override
    public String toString()
    {
        return "\nEmployee record for " + getFirstName() + " " + getLastName()
                + "\n------------------------------"
                + "\nFull Name: " + getFirstName() + " " + getLastName()
                + "\nID Number: " + getIdNumber()
                + "\nAge: " + getAge()
                + "\nAccount Balance: " + getAccountBalance();
    }

    public abstract double calculateEarnings(double amount);

    public abstract void populateEmployeeFromFile(String[] data, EmployeeReader reader);

    public abstract String outputFileFormat();

}
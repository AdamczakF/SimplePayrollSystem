package PayrollSystem;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.NoSuchElementException;

public class EmployeeReader
{
    public static final String EMPLOYEES_LIST_EMPTY = Visuals.RED + "Cannot perform action because no employee records are loaded. Please load employee records and try again" + Visuals.RESET;
    private final ArrayList<Employee> employees = new ArrayList<Employee>();
    private final ArrayList<Integer> failedRows = new ArrayList<Integer>();
    private String fileReference;

    public ArrayList<Employee> getEmployees()
    {
        return employees;
    }
    public String getFileReference()
    {
        return fileReference;
    }
    // Load Employee objects into the employees ArrayList from a CSV file. Each line in the file represents an employee record.
    void loadEmployees(String file)
    {
        if(!employees.isEmpty())
        {
            employees.clear();
        }
        int row = 0;
        fileReference = file;
        try
        {
            FileReader fileIn = new FileReader(file);
            BufferedReader buffIn = new BufferedReader(fileIn);

            String currentLine = "";
            while((currentLine = buffIn.readLine()) != null)
            {
                String[] employeeDetails = currentLine.split(",");
                parseRow(employeeDetails);
                loadEmployeeObject(employeeDetails, row);
                row++;
            }
            if(failedRows.isEmpty())
            {
                System.out.println(Visuals.GREEN + "Employee records loaded without errors." + Visuals.RESET);
            }
            else
            {
                System.out.println(Visuals.YELLOW + "Employee records loaded with errors. Failed to load employee record(s) at row(s): ");
                for(int failedRow : failedRows)
                {
                    System.out.println(" - Row " + failedRow);
                }
                failedRows.clear();
                System.out.println(Visuals.RESET);
            }
            fileIn.close();
            buffIn.close();
        }
        catch(Exception e){System.out.println(Visuals.RED + "Error: " + e.getMessage() + Visuals.RESET);}
    }

    // Removes white spaces and additional quotation marks from file data.
    private void parseRow(String[] data) {
        for (int index = 0; index < data.length; index++) {
            data[index] = data[index].replaceAll(" *\"*", "");
        }
    }

    //Loads a single Employee object into the arrayList. Differentiates between different employee types.
    private void loadEmployeeObject(String[] data, int row)
    {
        String employeeType = data[0];
        try
        {
            Employee employee = null;
            switch(employeeType)
            {
                case "BasePayEmployee":
                {
                    employee = new BasePayEmployee();
                }
                break;
                case "CommissionPayEmployee":
                {
                    employee = new CommissionPayEmployee();
                }
                break;
                default: throw new IllegalArgumentException(Visuals.RED + Visuals.ITALIC + "Employee type \""+ employeeType + "\" is not recognised." + Visuals.RESET);
            }

            employee.populateEmployeeFromFile(data, this);
            employees.add(employee);

        } catch(Exception e)
            {
                System.out.println(Visuals.RED + "\nError: Could not load employee due to conflicts at row " + row + ". Check employee record. Error details:\n"
                + e.getMessage() + Visuals.RESET);
                failedRows.add(row);
            }
    }

    Employee searchEmployees(int idNumber)
    {
        if(!employees.isEmpty())
        {
            for(int index = 0; index < employees.size(); index++)
            {
                if(idNumber == employees.get(index).getIdNumber())
                {
                    return employees.get(index);
                }
            }
        }
        else
        {
            throw new NoSuchElementException(EMPLOYEES_LIST_EMPTY);
        }

        return null;
    }
}

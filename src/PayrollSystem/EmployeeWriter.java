package PayrollSystem;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class EmployeeWriter {

    //Takes in an employee reader object, then updates the full employee records to the same file.
    public void writeEmployees(EmployeeReader reader)
    {
        ArrayList<Employee> employees = reader.getEmployees();
        if(!employees.isEmpty())
        {
            try
            {
                FileWriter fileWriter = new FileWriter(reader.getFileReference());
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                for (Employee employee : employees) {
                    bufferedWriter.write(employee.outputFileFormat() + "\r");
                }
                bufferedWriter.flush();

                fileWriter.close();
                bufferedWriter.close();

                System.out.println(Visuals.GREEN + "Employee records saved without errors." + Visuals.RESET);
            }
            catch (Exception e) {System.out.println(Visuals.RED + "Error: " + e.getMessage() + Visuals.RESET);}
        }
        else
        {
            throw new NoSuchElementException(EmployeeReader.EMPLOYEES_LIST_EMPTY);
        }
    }
    public Employee changeEmployeeType(Employee employee, String newType, EmployeeReader reader)
    {
        Employee newEmployee = null;
        int idNumber = employee.getIdNumber();
        switch(newType)
        {
            case "BasePayEmployee":
            {
                newEmployee = new BasePayEmployee(employee.getFirstName(), employee.getLastName(), employee.getAge(), Integer.MAX_VALUE-1, employee.getAccountBalance(), 0, reader);
            }
            break;
            case "CommissionPayEmployee":
            {
                newEmployee = new CommissionPayEmployee(employee.getFirstName(), employee.getLastName(), employee.getAge(), Integer.MAX_VALUE-1, employee.getAccountBalance(), reader);
            }
            break;
            default:
            {
                throw new IllegalArgumentException(Visuals.RED + Visuals.ITALIC + "Employee type \""+ newType + "\" is not recognised." + Visuals.RESET);
            }
        }
        for(int i = 0; i < reader.getEmployees().size(); i++)
        {
            if(reader.getEmployees().get(i) == employee)
            {
                reader.getEmployees().set(i, newEmployee);
                break;
            }
        }
        newEmployee.setId(idNumber, reader);
        return newEmployee;
    }

    public Employee changeEmployeeType(Employee employee, String newType, EmployeeReader reader, double hourlyRate)
    {
        BasePayEmployee newEmployee = (BasePayEmployee) changeEmployeeType(employee, newType, reader);
        newEmployee.setHourlyRate(hourlyRate);
        return newEmployee;
    }
}
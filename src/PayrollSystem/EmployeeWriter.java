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
}
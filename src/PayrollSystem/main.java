package PayrollSystem;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class main {

    public static final String COULD_NOT_RUN_FUNCTION_ERROR = Visuals.RED + "Could not complete this action due to an error. Error details:\n" + Visuals.RESET;
    public static final String EMPLOYEE_FOUND_MESSAGE = Visuals.GREEN + "Found employee record!" + Visuals.RESET;
    public static final String EMPLOYEE_NOT_FOUND__MESSAGE = Visuals.YELLOW + "Employee record not found" + Visuals.RESET;
    public static final String INVALID_INPUT_ERROR = Visuals.RED + "Invalid Input: %s" + Visuals.RESET;
    public static final String CONFIRM_EMPLOYEE_UPDATE = "Confirm details correct? (" +Visuals.GREEN + "yes" + Visuals.RESET + "/" + Visuals.RED + "no" + Visuals.RESET + ")";
    public static final String ACTION_CANCELLED = Visuals.YELLOW + "Action cancelled." + Visuals.ITALIC + Visuals.BOLD + " %s" + Visuals.RESET;

    public static void main(String[] args) throws InterruptedException {
        Scanner userInput = new Scanner(System.in);
        boolean exit = false;
        EmployeeReader employeeReader = new EmployeeReader();
        EmployeeWriter employeeWriter = new EmployeeWriter();

        System.out.println(Visuals.GREEN + "Welcome to the Payroll System!" + Visuals.RESET);

        while(!exit)
        {
            System.out.println("""
                    
                    Choose an action:
                    1: Load employees from file
                    2: Search for an employee by ID
                    3: Pay an employee
                    4: Change an employee's details
                    5: Create a new employee
                    6: Save changes
                    
                    Type "exit" to exit the program""");

            if(userInput.hasNextInt())
            {
                int choice = userInput.nextInt();

                switch(choice)
                {
                    case 1:
                    {
                        System.out.println("Enter the path of the file containing the employee records");
                        String filePath = userInput.next();
                        employeeReader.loadEmployees(filePath);
                        Thread.sleep(2000);
                    }
                    break;
                    case 2:
                    {
                        System.out.println("Enter the ID number of the employee to find");
                        if(userInput.hasNextInt())
                        {
                            int searchId = userInput.nextInt();
                            try
                            {
                                System.out.println("Retrieving employee details...");
                                Employee queriedEmployee = employeeReader.searchEmployees(searchId);
                                if (queriedEmployee != null) {
                                    System.out.println(EMPLOYEE_FOUND_MESSAGE);
                                    Thread.sleep(750);
                                    System.out.println(queriedEmployee);
                                    Thread.sleep(2000);
                                } else {
                                    System.out.println(EMPLOYEE_NOT_FOUND__MESSAGE);
                                }
                            }
                            catch (Exception e)
                            {
                                System.out.println(Visuals.RED + "Error: " + COULD_NOT_RUN_FUNCTION_ERROR + Visuals.ITALIC + e.getMessage() + Visuals.RESET);
                            }
                        }
                        else
                        {
                            System.out.printf(INVALID_INPUT_ERROR, "Please enter a number corresponding to an employee's ID number. Exiting function...");
                            userInput.next();
                        }
                    }
                    break;
                    case 3:
                    {
                        System.out.println("Enter the ID number of the employee to pay");
                        if (userInput.hasNextInt()) {
                            int searchId = userInput.nextInt();
                            try {
                                System.out.println("Retrieving employee details...");
                                Employee employee = employeeReader.searchEmployees(searchId);
                                if (employee != null) {
                                    System.out.println(EMPLOYEE_FOUND_MESSAGE);
                                    Thread.sleep(750);
                                    double amountToPay = 0;
                                    switch(employee.getClass().getSimpleName())
                                    {
                                        case "BasePayEmployee":
                                        {
                                            System.out.println("Enter the number of hours worked");
                                            if(userInput.hasNextDouble())
                                            {
                                                BasePayEmployee basePayEmployee = (BasePayEmployee) employee;
                                                amountToPay = basePayEmployee.calculateEarnings(userInput.nextDouble());
                                            }
                                            else
                                            {
                                                System.out.printf(INVALID_INPUT_ERROR, "Amount set to 0");
                                                userInput.next();
                                            }
                                        }
                                        break;
                                        case "CommissionPayEmployee": {
                                            System.out.println("Enter the employee's commission");
                                            if(userInput.hasNextDouble())
                                            {
                                                amountToPay = userInput.nextDouble();
                                            }
                                            else
                                            {
                                                System.out.printf(INVALID_INPUT_ERROR, "Amount set to 0");
                                                userInput.next();
                                            }
                                        }
                                        break;
                                    }
                                    System.out.println("\n" + employee);
                                    System.out.printf("\nAmount to pay employee: €%.2f", amountToPay);
                                    System.out.println("\n" + CONFIRM_EMPLOYEE_UPDATE);
                                    String confirmation = userInput.next();
                                    if (confirmation.equals("y") || confirmation.equals("yes"))
                                    {
                                        employee.setBalance(employee.getAccountBalance() + amountToPay);
                                        System.out.println(Visuals.GREEN + "Employee paid successfully" + Visuals.RESET);
                                        System.out.println(employee);
                                        System.out.println(Visuals.BOLD + Visuals.ITALIC + "\nMake sure you save changes before exiting the program" + Visuals.RESET);
                                        Thread.sleep(2000);
                                    }
                                    else {
                                        System.out.printf(ACTION_CANCELLED, "Employee has not been paid");
                                    }
                                }
                                else
                                {
                                    System.out.println(EMPLOYEE_NOT_FOUND__MESSAGE);
                                }
                            }
                            catch(Exception e)
                            {
                                System.out.println(Visuals.RED + "Error: " + COULD_NOT_RUN_FUNCTION_ERROR + Visuals.ITALIC + e.getMessage() + Visuals.RESET);
                                System.out.println(Visuals.ITALIC + Visuals.BOLD + "Employee has not been paid" + Visuals.RESET);
                            }
                        }
                        else
                        {
                            System.out.printf(INVALID_INPUT_ERROR, "Please enter a number corresponding to an employee's ID number. Exiting function...");
                            userInput.next();
                        }
                    }
                    break;
                    case 4:
                    {
                        System.out.println("Enter the ID number of the employee to alter");
                        if(userInput.hasNextInt())
                        {
                            int searchId = userInput.nextInt();
                            try
                            {
                                Employee employee = employeeReader.searchEmployees(searchId);
                                if (employee != null)
                                {
                                    System.out.println(EMPLOYEE_FOUND_MESSAGE);
                                    Thread.sleep(750);
                                    System.out.println(employee);
                                    System.out.println("\nChoose the details to modify:"
                                            + "\n1: First Name"
                                            + "\n2: Last Name"
                                            + "\n3: Age"
                                            + "\n4: ID number"
                                            + "\n5: Employee Type");
                                    if(employee.getClass().getSimpleName().equals("BasePayEmployee"))
                                    {
                                        System.out.println("6: Hourly Pay");
                                    }
                                    if(userInput.hasNextInt())
                                    {
                                        choice = userInput.nextInt();
                                        switch(choice)
                                        {
                                            case 1:
                                            {
                                                System.out.println("Enter new First Name");
                                                String newName = userInput.next();
                                                System.out.println("Proposed change: " + employee.getFirstName() + " -> " + newName);
                                                System.out.println("\n" + CONFIRM_EMPLOYEE_UPDATE);
                                                String confirmation = userInput.next();
                                                if(confirmation.equals("yes") || confirmation.equals("y"))
                                                {
                                                    employee.setFirstName(newName);
                                                    System.out.println(Employee.SUCCESSFUL_EMPLOYEE_UPDATE_MESSAGE);
                                                    System.out.println(employee);
                                                    Thread.sleep(2000);
                                                }
                                                else
                                                {
                                                    System.out.printf(ACTION_CANCELLED, "Employee has not been updated");
                                                }
                                            }
                                            break;
                                            case 2:
                                            {
                                                System.out.println("Enter new Last Name");
                                                String newName = userInput.next();
                                                System.out.println("Proposed change: " + employee.getLastName() + " -> " + newName);
                                                System.out.println("\n" + CONFIRM_EMPLOYEE_UPDATE);
                                                String confirmation = userInput.next();
                                                if(confirmation.equals("yes") || confirmation.equals("y"))
                                                {
                                                    employee.setLastName(newName);
                                                    System.out.println(Employee.SUCCESSFUL_EMPLOYEE_UPDATE_MESSAGE);
                                                    System.out.println(employee);
                                                    Thread.sleep(2000);
                                                }
                                                else
                                                {
                                                    System.out.printf(ACTION_CANCELLED, "Employee has not been updated");
                                                }
                                            }
                                            break;
                                            case 3:
                                            {
                                                System.out.println("Enter new age");
                                                int newAge = userInput.nextInt();
                                                System.out.println("Proposed change: " + employee.getAge() + " -> " + newAge);
                                                System.out.println("\n" + CONFIRM_EMPLOYEE_UPDATE);
                                                String confirmation = userInput.next();
                                                if(confirmation.equals("yes") || confirmation.equals("y"))
                                                {
                                                    employee.setAge(newAge);
                                                    System.out.println(Employee.SUCCESSFUL_EMPLOYEE_UPDATE_MESSAGE);
                                                    System.out.println(employee);
                                                    Thread.sleep(2000);
                                                }
                                                else
                                                {
                                                    System.out.printf(ACTION_CANCELLED, "Employee has not been updated");
                                                }
                                            }
                                            break;
                                            case 4:
                                            {
                                                System.out.println("Enter new ID number" + Visuals.BOLD + "\nID numbers must be unique");
                                                int newID = userInput.nextInt();
                                                System.out.println("Proposed change: " + employee.getIdNumber() + " -> " + newID);
                                                System.out.println("\n" + CONFIRM_EMPLOYEE_UPDATE);
                                                String confirmation = userInput.next();
                                                if(confirmation.equals("yes") || confirmation.equals("y"))
                                                {
                                                    employee.setId(newID, employeeReader);
                                                    System.out.println(Employee.SUCCESSFUL_EMPLOYEE_UPDATE_MESSAGE);
                                                    System.out.println(employee);
                                                    Thread.sleep(2000);
                                                }
                                                else
                                                {
                                                    System.out.printf(ACTION_CANCELLED, "Employee has not been updated");
                                                }
                                            }
                                            break;
                                            case 5:
                                            {
                                                System.out.println("Choose new employee type"
                                                                    + "\n1: BasePayEmployee"
                                                                    + "\n2: CommissionPayEmployee");
                                                int newType = userInput.nextInt();
                                                System.out.println("Proposed change: " + employee.getClass().getSimpleName() + " -> " + (newType ==1 ? "BasePayEmployee" : "CommissionPayEmployee"));
                                                System.out.println("\n" + CONFIRM_EMPLOYEE_UPDATE);
                                                String confirmation = userInput.next();
                                                if(confirmation.equals("yes") || confirmation.equals("y"))
                                                {
                                                    if(newType == 1)
                                                    {
                                                        System.out.println("Enter new hourly rate");
                                                        double newRate = userInput.nextDouble();
                                                        employee = employeeWriter.changeEmployeeType(employee, "BasePayEmployee", employeeReader, newRate);
                                                    }
                                                    else
                                                    {
                                                        employee = employeeWriter.changeEmployeeType(employee, "CommissionPayEmployee", employeeReader);
                                                    }

                                                    System.out.println(Employee.SUCCESSFUL_EMPLOYEE_UPDATE_MESSAGE);
                                                    System.out.println(employee);
                                                    Thread.sleep(2000);
                                                }
                                                else
                                                {
                                                    System.out.printf(ACTION_CANCELLED, "Employee has not been updated");
                                                }
                                            }
                                            break;
                                            case 6:
                                            {
                                                if(employee.getClass() == BasePayEmployee.class)
                                                {
                                                    BasePayEmployee basePayEmployee = (BasePayEmployee) employee;
                                                    System.out.println("Enter new hourly rate");
                                                    double newRate = userInput.nextDouble();
                                                    System.out.println("Proposed change: " + basePayEmployee.getHourlyRate() + " -> " + newRate);
                                                    System.out.println("\n" + CONFIRM_EMPLOYEE_UPDATE);
                                                    String confirmation = userInput.next();
                                                    if(confirmation.equals("yes") || confirmation.equals("y"))
                                                    {
                                                        basePayEmployee.setHourlyRate(newRate);
                                                        System.out.println(Employee.SUCCESSFUL_EMPLOYEE_UPDATE_MESSAGE);
                                                        System.out.println(employee);
                                                        Thread.sleep(2000);
                                                    }
                                                    else
                                                    {
                                                        System.out.printf(ACTION_CANCELLED, "Employee has not been updated");
                                                    }
                                                }
                                                else
                                                {
                                                    throw new IllegalArgumentException("Please enter a number corresponding to an employee detail. Exiting function...");
                                                }
                                            }
                                            break;
                                            default: throw new IllegalArgumentException("Please enter a number corresponding to an employee detail. Exiting function...");
                                        }
                                    }
                                    else
                                    {
                                        System.out.printf(INVALID_INPUT_ERROR, "Please enter a number corresponding to an employee detail. Exiting function...");
                                    }
                                } else
                                {
                                    System.out.println(EMPLOYEE_NOT_FOUND__MESSAGE);
                                }
                            }
                            catch (Exception e)
                            {
                                System.out.println(Visuals.RED + "Error: " + COULD_NOT_RUN_FUNCTION_ERROR + Visuals.RED + Visuals.ITALIC + e.getMessage() + Visuals.RESET);
                            }
                        }
                        else
                        {
                            System.out.printf(INVALID_INPUT_ERROR, "Please enter a number corresponding to an employee's ID number. Exiting function...");
                            userInput.next();
                        }
                    }
                    break;
                    case 5:
                    {
                        try {
                            if(employeeReader.getEmployees().isEmpty())
                            {
                                throw new NoSuchElementException(EmployeeReader.EMPLOYEES_LIST_EMPTY);
                            }
                            System.out.println("Provide details for the new employee:");
                            System.out.print("First Name: ");
                            String firstName = userInput.next();
                            System.out.print("Last Name: ");
                            String lastName = userInput.next();
                            System.out.print("Age: ");
                            int age = userInput.nextInt();
                            System.out.print("ID number " + Visuals.BOLD + Visuals.ITALIC + "Must be unique: " + Visuals.RESET);
                            int id = userInput.nextInt();
                            System.out.println("Choose new employee type"
                                    + "\n1: BasePayEmployee"
                                    + "\n2: CommissionPayEmployee");
                            int employeeType = userInput.nextInt();
                            double hourlyRate = 0;
                            switch (employeeType)
                            {
                                case 1:
                                    System.out.print("Hourly Rate: ");
                                    hourlyRate = userInput.nextDouble();
                                    break;
                                case 2:
                                    break;
                                default:
                                    throw new IllegalArgumentException("Please enter a number corresponding to an employee type. Exiting function...");
                            }
                            System.out.println("\n" + CONFIRM_EMPLOYEE_UPDATE);
                            String confirmation = userInput.next();
                            if(confirmation.equals("yes") || confirmation.equals("y"))
                            {
                                Employee employee = switch (employeeType) {
                                    case 1 -> new BasePayEmployee(firstName, lastName, age, id, 0, hourlyRate, employeeReader);
                                    case 2 -> new CommissionPayEmployee(firstName, lastName, age, id, 0, employeeReader);
                                    default -> throw new IllegalArgumentException("Please enter a number corresponding to an employee type. Exiting function...");
                                };
                                employeeReader.getEmployees().add(employee);
                                System.out.println(Visuals.GREEN + "Successfully created employee!" + Visuals.RESET);
                                Thread.sleep(750);
                                System.out.println(employee);
                                Thread.sleep(2000);
                            }
                            else
                            {
                                System.out.printf(ACTION_CANCELLED, "Employee has not been created");
                            }
                        }
                        catch(Exception e)
                        {
                            System.out.println(Visuals.RED + "Error: " + COULD_NOT_RUN_FUNCTION_ERROR + Visuals.ITALIC + e.getMessage() + Visuals.RESET);
                        }
                    }
                    break;
                    case 6:
                    {
                        try
                        {
                            employeeWriter.writeEmployees(employeeReader);
                        }
                        catch(Exception e)
                        {
                            System.out.println(Visuals.RED + "Error: " + COULD_NOT_RUN_FUNCTION_ERROR + Visuals.ITALIC + e.getMessage() + Visuals.RESET);
                        }
                    }
                    break;
                    default:
                    {
                        System.out.printf(INVALID_INPUT_ERROR, "Action not recognised. Please select an action from the list");
                    }
                }
            }
            else if(userInput.next().equals("exit"))
            {
                try {
                    System.out.println("Save changes? (" + Visuals.GREEN + "yes" + Visuals.RESET + "/" + Visuals.RED + "no" + Visuals.RESET + "/" + Visuals.YELLOW + "cancel" + Visuals.RESET + ")");
                    String choice = userInput.next();
                    if (choice.equals("yes") || choice.equals("y")) {
                        employeeWriter.writeEmployees(employeeReader);
                        exit = true;
                    } else if (choice.equals("no") || choice.equals("n")) {
                        exit = true;
                    }
                }
                catch(Exception e)
                {
                    System.out.println(Visuals.RED + "Error: " + COULD_NOT_RUN_FUNCTION_ERROR + Visuals.ITALIC + e.getMessage() + Visuals.RESET);
                }
            }
            else
            {
                System.out.printf(INVALID_INPUT_ERROR, "Please enter a number corresponding to an action or type 'exit' to exit.");

            }
        }
    }
}
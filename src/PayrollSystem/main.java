package PayrollSystem;

import java.util.Scanner;

public class main {

    public static final String COULD_NOT_RUN_FUNCTION_ERROR = Visuals.RED + "Could not complete this action due to an error. Error details:\n" + Visuals.RESET;

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        boolean exit = false;
        EmployeeReader employeeReader = new EmployeeReader();
        EmployeeWriter employeeWriter = new EmployeeWriter();

        System.out.println(Visuals.GREEN + "Welcome to the Payroll System!" + Visuals.RESET);

        while(!exit)
        {
            System.out.println("\nChoose an action:"
                    + "\n1: Load employees from file"
                    + "\n2: Search for an employee by ID"
                    + "\n3: Pay an employee"
                    + "\n5: Save changes"
                    + "\n\nType \"exit\" to exit the program");

            if(userInput.hasNextInt())
            {
                int choice = userInput.nextInt();

                switch(choice)
                {
                    case 1:
                    {
                      //  System.out.println("Enter the path of the file containing the employee records");
                      //  String filePath = userInput.next();
                        employeeReader.loadEmployees("employees.txt");
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
                                Employee queriedEmployee = employeeReader.searchEmployees(searchId);
                                if (queriedEmployee != null) {
                                    System.out.println(Visuals.GREEN + "Found employee record!" + Visuals.RESET);
                                    System.out.println(queriedEmployee);
                                } else {
                                    System.out.println(Visuals.YELLOW + "Employee record not found" + Visuals.RESET);
                                }
                            }
                            catch (Exception e)
                            {
                                System.out.println(Visuals.RED + "Error: " + COULD_NOT_RUN_FUNCTION_ERROR + Visuals.ITALIC + e.getMessage() + Visuals.RESET);
                            }
                        }
                        else
                        {
                            System.out.println(Visuals.RED + "Invalid input. Please enter a number corresponding to an employee's ID number. Exiting function..." + Visuals.RESET);
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
                                    System.out.println(Visuals.GREEN + "Found employee record!" + Visuals.RESET);
                                    double amountToPay = 0;
                                    switch(employee.getClass().getSimpleName())
                                    {
                                        case "BasePayEmployee":
                                        {
                                            System.out.println("Enter the number of hours worked");
                                            if(userInput.hasNextDouble())
                                            {
                                                BasePayEmployee basePayEmployee = (BasePayEmployee) employee;
                                                amountToPay = userInput.nextDouble() * basePayEmployee.getHourlyRate();
                                            }
                                            else
                                            {
                                                System.out.println(Visuals.RED + "Invalid input. Amount set to 0" + Visuals.RESET);
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
                                                System.out.println(Visuals.RED + "Invalid input. Amount set to 0" + Visuals.RESET);
                                                userInput.next();
                                            }
                                        }
                                        break;
                                    }
                                    System.out.println("\n" + employee);
                                    System.out.printf("\nAmount to pay employee: €%.2f", amountToPay);
                                    System.out.println("\nConfirm details correct? (yes/no)");
                                    String confirmation = userInput.next();
                                    if (confirmation.equals("y") || confirmation.equals("yes"))
                                    {
                                        employee.setBalance(employee.getAccountBalance() + amountToPay);
                                        System.out.println(Visuals.GREEN + "Employee paid successfully" + Visuals.RESET);
                                        System.out.println(employee);
                                        System.out.println(Visuals.BOLD + Visuals.ITALIC + "\nMake sure you save changes before exiting the program" + Visuals.RESET);
                                    }
                                    else {
                                        System.out.println("Action cancelled. Employee has not been paid");
                                    }
                                }
                                else
                                {
                                    System.out.println(Visuals.YELLOW + "Employee record not found" + Visuals.RESET);
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
                            System.out.println(Visuals.RED + "Invalid input. Please enter a number corresponding to an employee's ID number. Exiting function..." + Visuals.RESET);
                            userInput.next();
                        }
                    }
                    break;
                    case 5:
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
                }
            }
            else if(userInput.next().equals("exit"))
            {
                exit = true;
            }
            else
            {
                System.out.println(Visuals.RED + "Invalid input. Please enter a number corresponding to an action or type 'exit' to exit." + Visuals.RESET);

            }
        }
    }
}
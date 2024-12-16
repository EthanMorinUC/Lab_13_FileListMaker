import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SafeInput {

    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String retString = ""; // Set this to zero length. Loop runs until it isn't
        do
        {
            System.out.print("\n" +prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        }while(retString.length() == 0);

        return retString;
    }

    public static int getInt(Scanner pipe, String prompt) {
        int userInput = 0;
        boolean validInput = false;
        do
        {
            System.out.print(prompt);
            if (pipe.hasNextInt())
            {
                userInput = pipe.nextInt();
                validInput = true;
            }
            else
            {
                String trash = pipe.next();
                System.out.println("Invalid input: " + trash);
                System.out.println("Please enter a valid integer.");
            }
            pipe.nextLine();
        } while (!validInput);
        return userInput;
    }
    public static double getDouble(Scanner pipe, String prompt)
    {
        double userInput = 0.0;
        boolean validInput = false;

        do
        {
            System.out.print(prompt);
            if (pipe.hasNextDouble())
            {
                userInput = pipe.nextDouble();
                validInput = true;
            }
            else
            {
                String trash = pipe.next();
                System.out.println("Invalid input: " + trash);
                System.out.println("Please enter a valid double value.");
            }
            pipe.nextLine();
        }
        while (!validInput);
        return userInput;
    }
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high)
    {
        int userInput = 0;
        boolean validInput = false;
        do
        {
            System.out.print(prompt + " [" + low + " - " + high + "]: ");

            if (pipe.hasNextInt())
            {
                userInput = pipe.nextInt();
                if (userInput >= low && userInput <= high)
                {
                    validInput = true;
                }
                else
                {
                    System.out.println("Invalid input: " + userInput);
                    System.out.println("Please enter a value between " + low + " and " + high + ".");
                }
            }
            else
            {
                String trash = pipe.next();
                System.out.println("Invalid input: " + trash);
                System.out.println("Please enter a valid integer.");
            }
            pipe.nextLine();
        }
        while (!validInput);
        return userInput;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high)
    {
        double userInput = 0.0;
        boolean validInput = false;
        do
        {
            System.out.print(prompt + " [" + low + " - " + high + "]: ");
            if (pipe.hasNextDouble())
            {
                userInput = pipe.nextDouble();
                if (userInput >= low && userInput <= high)
                {
                    validInput = true;
                }
                else
                {
                    System.out.println("Invalid input: " + userInput);
                    System.out.println("Please enter a value between " + low + " and " + high + ".");
                }
            }
            else
            {
                String trash = pipe.next();
                System.out.println("Invalid input: " + trash);
                System.out.println("Please enter a valid double value.");
            }
            pipe.nextLine();
        }
        while (!validInput);
        return userInput;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt)
    {
        boolean validInput = false;
        boolean response = false;
        do
        {
            System.out.print(prompt + " [Y/N]: ");
            String userInput = pipe.nextLine().trim();

            if (userInput.equalsIgnoreCase("Y"))
            {
                response = true;
                validInput = true;
            }
            else if (userInput.equalsIgnoreCase("N"))
            {
                response = false;
                validInput = true;
            }
            else
            {
                System.out.println("Invalid input: " + userInput);
                System.out.println("Please enter 'Y' for Yes or 'N' for No.");
            }
        }
        while (!validInput);
        return response;
    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx)
    {
        String userInput = "";
        boolean validInput = false;
        do
        {
            System.out.print(prompt + ": ");
            userInput = pipe.nextLine().trim();
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(userInput);
            if (matcher.matches())
            {
                validInput = true;
            }
            else
            {
                System.out.println("Invalid input: " + userInput);
                System.out.println("Please enter a valid input that matches the pattern: " + regEx);
            }
        }
        while (!validInput);
        return userInput;
    }

    public static void prettyHeader(String msg)
    {
        int width = 60;
        int msgLength = msg.length();
        int starsOnEachSide = 3;
        int totalPadding = width - msgLength - (2 * starsOnEachSide) - 2;
        int paddingEachSide = totalPadding / 2;

        // Print top row of stars
        for (int i = 0; i < width; i++)
        {
            System.out.print("*");
        }
        System.out.println();

        // Print the centered message
        System.out.print("***");
        for (int i = 0; i < paddingEachSide; i++)
        {
            System.out.print(" ");
        }
        System.out.print(msg);
        for (int i = 0; i < paddingEachSide; i++)
        {
            System.out.print(" ");
        }
        if (totalPadding % 2 != 0)
        {
            System.out.print(" ");
        }
        System.out.println("***");

        // Print bottom row of stars
        for (int i = 0; i < width; i++)
        {
            System.out.print("*");
        }
        System.out.println();
    }
}


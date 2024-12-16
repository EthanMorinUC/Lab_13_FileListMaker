import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileListMaker
{
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<String> myArrList = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFilename = "";

    public static void main(String[] args)
    {
        boolean running = true;
        while (running)
        {
            displayMenu();
            String choice = SafeInput.getRegExString(scanner, "Enter a command", "[AaDdIiPpQqMmOoSsCcVv]");

            if (choice.equalsIgnoreCase("A"))
            {
                addItem();
            }
            else if (choice.equalsIgnoreCase("D"))
            {
                deleteItem();
            }
            else if (choice.equalsIgnoreCase("I"))
            {
                insertItem();
            }
            else if (choice.equalsIgnoreCase("M"))
            {
                moveItem();
            }
            else if (choice.equalsIgnoreCase("O"))
            {
                openFile();
            }
            else if (choice.equalsIgnoreCase("S"))
            {
                saveFile();
            }
            else if (choice.equalsIgnoreCase("C"))
            {
                clearList();
            }
            else if (choice.equalsIgnoreCase("V"))
            {
                printList();
            }
            else if (choice.equalsIgnoreCase("Q"))
            {
                running = quitProgram();
            }
        }
    }

    private static void displayMenu()
    {
        System.out.println("\nCurrent List:");
        printList();
        System.out.println("\nMenu:");
        System.out.println("A – Add an item to the list");
        System.out.println("D – Delete an item from the list");
        System.out.println("I – Insert an item into the list");
        System.out.println("M – Move an item");
        System.out.println("O – Open a list file from disk");
        System.out.println("S – Save the current list file to disk");
        System.out.println("C – Clear the list");
        System.out.println("V – View the list");
        System.out.println("Q – Quit the program");
    }

    private static void addItem()
    {
        String item = SafeInput.getNonZeroLenString(scanner, "Enter an item to add");
        myArrList.add(item);
        needsToBeSaved = true;
    }

    private static void deleteItem()
    {
        if (myArrList.isEmpty())
        {
            System.out.println("The list is empty. Nothing to delete.");
        }
        else
        {
            int index = SafeInput.getRangedInt(scanner, "Enter the index of the item to delete", 1, myArrList.size());
            myArrList.remove(index - 1);  // Convert to 0-based index
            needsToBeSaved = true;
        }
    }

    private static void insertItem()
    {
        if (myArrList.isEmpty())
        {
            System.out.println("The list is empty. Use the Add option to add the first item.");
        }
        else
        {
            int index = SafeInput.getRangedInt(scanner, "Enter the index to insert the item at", 1, myArrList.size() + 1);
            String item = SafeInput.getNonZeroLenString(scanner, "Enter an item to insert");
            myArrList.add(index - 1, item);  // Convert to 0-based index
            needsToBeSaved = true;
        }
    }

    private static void moveItem()
    {
        if (myArrList.isEmpty())
        {
            System.out.println("The list is empty. Nothing to move.");
        }
        else
        {
            int fromIndex = SafeInput.getRangedInt(scanner, "Enter the index of the item to move", 1, myArrList.size());
            int toIndex = SafeInput.getRangedInt(scanner, "Enter the index to move the item to", 1, myArrList.size());
            String item = myArrList.remove(fromIndex - 1);  // Remove the item
            myArrList.add(toIndex - 1, item);  // Reinsert the item
            needsToBeSaved = true;
        }
    }

    private static void openFile()
    {
        if (needsToBeSaved)
        {
            if (!SafeInput.getYNConfirm(scanner, "There are unsaved changes. Do you want to continue without saving?"))
            {
                saveFile();
            }
        }

        // Using absolute path
        String filename = SafeInput.getNonZeroLenString(scanner, "Enter the filename to open (without extension)") + ".txt";
        String filePath = "C:\\Users\\ambie\\IdeaProjects\\Lab_13_FileListMaker\\src\\" + filename;

        System.out.println("Attempting to open file: " + filePath); // Debug print statement

        File file = new File(filePath);
        if (file.exists())
        {
            myArrList.clear();
            try (Scanner fileScanner = new Scanner(file))
            {
                while (fileScanner.hasNextLine())
                {
                    String line = fileScanner.nextLine();
                    myArrList.add(line);
                }
                needsToBeSaved = false;
                currentFilename = filePath;
            }
            catch (IOException e)
            {
                System.out.println("An error occurred while opening the file: " + e.getMessage());
            }
        }
        else
        {
            System.out.println("File not found: " + filePath);
        }
    }


    private static void saveFile()
    {
        String filename;
        if (currentFilename.isEmpty())
        {
            filename = SafeInput.getNonZeroLenString(scanner, "Enter the filename to save (without extension)") + ".txt";
        }
        else
        {
            filename = currentFilename;
        }

        // Construct the full path to save the file in the src directory
        String filePath = "C:\\Users\\ambie\\IdeaProjects\\Lab_13_FileListMaker\\src\\" + filename;

        System.out.println("Saving file to: " + filePath); // Debug print statement

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
        {
            for (String item : myArrList)
            {
                writer.write(item);
                writer.newLine();
            }
            needsToBeSaved = false;
            currentFilename = filePath;
            System.out.println("File saved successfully!");
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while saving the file: " + e.getMessage());
        }
    }


    private static void clearList()
    {
        myArrList.clear();
        needsToBeSaved = true;
    }

    private static void printList()
    {
        if (myArrList.isEmpty())
        {
            System.out.println("The list is empty.");
        }
        else
        {
            for (int i = 0; i < myArrList.size(); i++)
            {
                System.out.println((i + 1) + ": " + myArrList.get(i));
            }
        }
    }

    private static boolean quitProgram()
    {
        if (needsToBeSaved)
        {
            if (SafeInput.getYNConfirm(scanner, "You have unsaved changes. Do you want to save before quitting?"))
            {
                saveFile();
            }
        }
        return false;
    }
}

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Lab_13_FileListMaker {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<String> myArrList = new ArrayList<>();
    private static boolean needsToBeSaved = false;

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            displayMenu();
            String choice = SafeInput.getRegExString(scanner, "Enter a command", "[AaDdIiPpQqMmOoSsCcVv]");

            if (choice.equalsIgnoreCase("A")) {
                addItem();
            } else if (choice.equalsIgnoreCase("D")) {
                deleteItem();
            } else if (choice.equalsIgnoreCase("I")) {
                insertItem();
            } else if (choice.equalsIgnoreCase("M")) {
                moveItem();
            } else if (choice.equalsIgnoreCase("O")) {
                openFile();
            } else if (choice.equalsIgnoreCase("S")) {
                saveFile();
            } else if (choice.equalsIgnoreCase("C")) {
                clearList();
            } else if (choice.equalsIgnoreCase("V")) {
                printList();
            } else if (choice.equalsIgnoreCase("Q")) {
                running = quitProgram();
            }
        }
    }

    private static void displayMenu() {
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

    private static void addItem() {
        String item = SafeInput.getNonZeroLenString(scanner, "Enter an item to add");
        myArrList.add(item);
        needsToBeSaved = true;
    }

    private static void deleteItem() {
        if (myArrList.isEmpty()) {
            System.out.println("The list is empty. Nothing to delete.");
        } else {
            int index = SafeInput.getRangedInt(scanner, "Enter the index of the item to delete", 1, myArrList.size());
            myArrList.remove(index - 1);
            needsToBeSaved = true;
        }
    }

    private static void insertItem() {
        if (myArrList.isEmpty()) {
            System.out.println("The list is empty. Use the Add option to add the first item.");
        } else {
            int index = SafeInput.getRangedInt(scanner, "Enter the index to insert the item at", 1, myArrList.size() + 1);
            String item = SafeInput.getNonZeroLenString(scanner, "Enter an item to insert");
            myArrList.add(index - 1, item);
            needsToBeSaved = true;
        }
    }

    private static void moveItem() {
        if (myArrList.isEmpty()) {
            System.out.println("The list is empty. Nothing to move.");
        } else {
            int fromIndex = SafeInput.getRangedInt(scanner, "Enter the index of the item to move", 1, myArrList.size());
            int toIndex = SafeInput.getRangedInt(scanner, "Enter the index to move the item to", 1, myArrList.size());
            String item = myArrList.remove(fromIndex - 1);
            myArrList.add(toIndex - 1, item);
            needsToBeSaved = true;
        }
    }

    private static void openFile() {
        if (needsToBeSaved) {
            if (!SafeInput.getYNConfirm(scanner, "There are unsaved changes. Do you want to continue without saving?")) {
                saveFile();
            }
        }
        String filename = SafeInput.getNonZeroLenString(scanner, "Enter the filename to open") + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            myArrList.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                myArrList.add(line);
            }
            needsToBeSaved = false;
        } catch (IOException e) {
            System.out.println("An error occurred while opening the file: " + e.getMessage());
        }
    }

    private static void saveFile() {
        String filename = SafeInput.getNonZeroLenString(scanner, "Enter the filename to save") + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String item : myArrList) {
                writer.write(item);
                writer.newLine();
            }
            needsToBeSaved = false;
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file: " + e.getMessage());
        }
    }

    private static void clearList() {
        myArrList.clear();
        needsToBeSaved = true;
    }

    private static void printList() {
        if (myArrList.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            for (int i = 0; i < myArrList.size(); i++) {
                System.out.println((i + 1) + ": " + myArrList.get(i));
            }
        }
    }

    private static boolean quitProgram() {
        if (needsToBeSaved) {
            return !SafeInput.getYNConfirm(scanner, "You have unsaved changes. Are you sure you want to quit without saving?");
        }
        return false;
    }
}

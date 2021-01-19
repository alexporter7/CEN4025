package module2;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * <h4>ToDoApplication</h4>
 * Used to create, modify and remove to-do lists and items
 * <p>
 * This was created by Alex Porter and is provided AS-IS.
 *
 * @author Alex Porter
 * @since 2021-01-19
 */

/*
    Write a simple to-do-item application in Java. It should support the following features,
        - Add a to-do item
        - Delete a to-do item
        - View the to-do items

    Make sure to structure your program in a modular way. In this case, that means you would have a command-line
    application which uses a class that holds the to-do items internally and provides public methods to add an item,
    delete an item, and provide the list of to-do items.

    Submit your source code, and some screen shots of your program in action.
*/

public class ToDoApplication {

    private static boolean isInit = false;
    private static int selectedListId = Integer.MIN_VALUE;
    private static List<TDList> toDoLists = new ArrayList<>();

    private static final String DATA_FILE = "todo.ser";

    public static void main(String[] args) throws InterruptedException {
//        TDItem item = new TDItem(0, "test", "test");
//        TDItem item2 = new TDItem(1, "test2", "test");
//        TDItem item3 = new TDItem(2, "test3", "test");
//
//        List<TDItem> items = Arrays.asList(item, item2, item3);
//        TDList list = new TDList(0, "test", items);
//        TDList list2 = new TDList(1, "test2", items);
//
//        toDoLists.add(list);
//        toDoLists.add(list2);

        //Confirm the code has been initialized
        if(!isInit) initToDo();

        printLists();

        Scanner userInput = new Scanner(System.in);
        System.out.print("To Do $: ");
        String command = userInput.nextLine();

        if(!command.equals("exit")) {
            parseCommand(command);
            main(null);
        }

        exitApplication();

    }

    public static void parseCommand(String command) throws InterruptedException {
        String[] commandArguments = command.split(" ");
        switch (commandArguments[0]) {
            case "add":
                add(commandArguments);
                break;
            case "edit":
                modify(commandArguments);
                break;
            case "remove":
                remove(commandArguments);
                break;
            default:
                System.out.println(String.format("Command [%s] is not a valid command", commandArguments[0]));
                main(null);
        }

    }

    //add list <name>
    //add item <list_name||list_id> <name>
    public static void add(String[] commandArguments) {
        switch(commandArguments[1]) {
            case "item":
                int listId = getIdFromListName(commandArguments[2]);
                String itemName = commandArguments[3];
                //implement dueDate
                if(listId != -1) {
                    toDoLists.get(listId).addItem(
                            new TDItem(itemName)
                    );
                } else System.out.println("Invalid list id");

                break;
            case "list":
                toDoLists.add(new TDList(
                        toDoLists.size(),   //List ID
                        commandArguments[2] //List Name
                        ));
        }
    }

    //edit <list,item> <id||name> <(name),(name,due_date)>
    public static void modify(String[] commandArguments) {
        switch(commandArguments[1]) {
            case "item":

                break;
            case "list":

        }
    }

    //remove <list,item> <id||name>
    public static void remove(String[] commandArguments) {
        switch(commandArguments[1]) {
            case "item":

                break;
            case "list":

        }
    }

    public static int getIdFromListName(String listName) {
        if(isInteger(listName) && Integer.parseInt(listName) < toDoLists.size())
            return Integer.parseInt(listName);

        for(TDList tdList : toDoLists) {
            if(tdList.getListName().equals(listName))
                return tdList.getListId();
        }
        return -1;
    }

    public static boolean isInteger(String listId) {
        try {
            Integer.parseInt(listId);
        } catch (NumberFormatException numberFormatException) {
            return false;
        } catch (NullPointerException nullPointerException) {
            return false;
        }
        return true;
    }

    public static void printLists() {
        System.out.println("======= Lists =======");
        if(toDoLists.size() != 0) {
            for(TDList tdList : toDoLists) {
                System.out.println(String.format(
                        "List Name: %s%n" +
                                "Total Items: %s%n", tdList.getListName(),tdList.getListItems().size()
                        ));
            }
        } else {
            System.out.println("No lists to show, add a list to display it here!");
        }
    }

    public static void exitApplication() throws InterruptedException {
        if(toDoLists.size() != 0) {
            System.out.println("Saving output......");
            TDResult saveResult = saveOutput();
            if (saveResult.getResultType() == TDResult.ResultType.PASS) {
                System.out.println("Saved file successfully, exiting in 3 seconds");
                TimeUnit.SECONDS.sleep(3);
                System.exit(0);
            } else {
                System.out.println("Could not save file, exiting in 3 seconds");
                TimeUnit.SECONDS.sleep(3);
                System.exit(1);
            }
        } else {
            System.out.println("No data to save, exiting in 3 seconds");
            TimeUnit.SECONDS.sleep(3);
            System.exit(0);
        }
    }

    public static void initToDo() {

    }

    /**
     * Serializes and saves currently active To Do List data into todo.ser
     * @return (ResultType) Returns either PASS or FAIL to handle error action and alert user
     */
    public static TDResult saveOutput() {
        TDResult saveResult = new TDResult("Could not save file.", TDResult.ResultType.FAIL);
        try {
            //Create the FileOutputStream && ObjectOutputStream
            FileOutputStream fileOutput = new FileOutputStream(DATA_FILE);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            //Save each list and associated items
            objectOutput.writeObject(toDoLists);
            //Close out FileOutputStream && ObjectOutputStream
            objectOutput.close();
            fileOutput.close();
            //Return Successful
            saveResult.setResultType(TDResult.ResultType.PASS);
            saveResult.setReturnMessage("Data was successfully serialized");
            return saveResult;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println(String.format("%s could not be saved to.", DATA_FILE));
        }
        return saveResult;
    }

    public static TDResult loadOutput() {
        TDResult loadResult = new TDResult("Data could not be loaded.", TDResult.ResultType.FAIL);
        try {
            //Create the FileInputStream && ObjectInputStream
            FileInputStream fileInput = new FileInputStream(DATA_FILE);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            //Load the toDoLists
            toDoLists = (ArrayList<TDList>) objectInput.readObject();
            //Close the FileInputStream && ObjectInputStream
            objectInput.close();
            fileInput.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println(String.format("%s could not be opened or found", DATA_FILE));
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
            System.out.println("Classes could not be loaded into the ToDo List");
        }

        return loadResult;
    }

}

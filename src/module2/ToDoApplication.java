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
            case "view":
                view(commandArguments);
                break;
            default:
                System.out.println(String.format("Command [%s] is not a valid command", commandArguments[0]));
                main(null);
        }

    }

    /**
     * Logic to add either items to a list, or to add a list.
     * <p><b>Usage:</b></p>
     * <p><b>add list [list_name]</b></p>
     * <p><b>add item [list_name || list_id] [name]</b></p>
     * @param commandArguments (String[]) Command split up to be further parsed
     */
    public static void add(String[] commandArguments) {
        switch(commandArguments[1]) {
            case "item":
                if(commandArguments.length != 4) {
                    System.out.println("Invalid arguments. (add item [list_name || list_id] [name])");
                    return;
                }
                int listId = getIdFromListName(commandArguments[2]);
                String itemName = commandArguments[3];
                //implement dueDate
                //Check if the lists exists, if its -1, it does not exist
                if(listId != -1) {
                    toDoLists.get(listId).addItem(
                            new TDItem(itemName)
                    );
                } else System.out.println("Invalid list id");

                break;
            case "list":
                if(commandArguments.length != 3) {
                    System.out.println("Invalid arguments. (add list [list_name])");
                    return;
                }
                toDoLists.add(new TDList(
                        toDoLists.size(),   //List ID
                        commandArguments[2] //List Name
                        ));
        }
    }

    /**
     * Logic to edit either items from a list, or to edit a list.
     * <p><b>Usage:</b></p>
     * <p><b>edit list [id || name] [newName]</b></p>
     * <p><b>edit item [id || listName] [itemId] [newName]</b></p>
     * @param commandArguments (String[]) Command split up to be further parsed
     */
    public static void modify(String[] commandArguments) {
        int listId = 0;
        int itemId = 0;
        switch(commandArguments[1]) {
            case "item":
                if(commandArguments.length != 5) {
                    System.out.println("Invalid arguments. (edit item [id || listName] [itemId] [newName])");
                    return;
                }
                listId = getIdFromListName(commandArguments[2]);
                itemId = getIdFromItemName(listId, commandArguments[3]);
                if(listId == -1) {
                    System.out.println("Invalid List ID/Name");
                } else if(itemId == -1) {
                    System.out.println("Invalid Item ID/Name");
                } else {
                    toDoLists.get(listId).getItem(itemId).setItemName(commandArguments[4]);
                }
                break;
            case "list":
                if(commandArguments.length != 4) {
                    System.out.println("Invalid arguments. (edit list [id || name] [newName])");
                    return;
                }
                listId = getIdFromListName(commandArguments[2]);
                if(listId == -1)
                    System.out.println("Invalid List ID/Name");
                else
                    toDoLists.get(listId).setListName(commandArguments[3]);
        }
    }

    /**
     * Logic to remove either items from a list, or to remove a list.
     * <p><b>Usage:</b></p>
     * <p><b>remove [list || item] [id || name]</b></p>
     * @param commandArguments (String[]) Command split up to be further parsed
     */
    public static void remove(String[] commandArguments) {
        switch(commandArguments[1]) {
            case "item":

                break;
            case "list":

        }
    }

    /**
     * Logic to view either items from a specified list or all items from all lists
     * <p><b>Usage:</b></p>
     * <p><b>view list [list_name || list_id]</b></p>
     * <p><b>view all</b></p>
     * @param commandArguments (String[]) Command split up to be further parsed
     */
    public static void view(String[] commandArguments) {
        switch(commandArguments[1]) {
            case "list":
                if(commandArguments.length != 3) {
                    System.out.println("Invalid Arguments. (view list [list_name || list_id])");
                    return;
                }
                int listId = getIdFromListName(commandArguments[2]);
                if(listId != -1) {
                    System.out.println(String.format("### %s ###", toDoLists.get(listId).getListName()));
                    printListItems(listId);
                } else {
                    System.out.println("List does not exist or was not valid");
                }
                break;
            case "all":
                if(commandArguments.length != 2) {
                    System.out.println("Invalid Arguments. (view all)");
                }
                for(TDList tdList : toDoLists) {
                    System.out.println(String.format("### %s ###", tdList.getListName()));
                    printListItems(tdList.getListId());
                }
                break;

        }
    }

    /**
     * Prints out items from a specified list
     * @param listId (int) List ID
     */
    public static void printListItems(int listId) {
        for(TDItem item : toDoLists.get(listId).getListItems()) {
            System.out.println(String.format("Item ID: %s | Item Name: %s", item.getId(), item.getItemName()));
        }
        System.out.println(); //Print a blank line
    }

    /**
     * Returns the list id based on the name of a list. If an integer is parsed through and it exists (based on the
     * lists from toDoLists) it will return the list id. If the name is found, it will return that list id. If
     * the id or name doesn't exist, it returns -1.
     * @param listName (String) The list name or id to be parsed
     * @return (int) Returns the associated list id or -1 if it cannot be found
     */
    public static int getIdFromListName(String listName) {
        if(isInteger(listName) && Integer.parseInt(listName) < toDoLists.size())
            return Integer.parseInt(listName);

        for(TDList tdList : toDoLists) {
            if(tdList.getListName().equals(listName))
                return tdList.getListId();
        }
        return -1;
    }

    /**
     * Returns the item id based on the name of an item. If an integer is parsed through and it exists it will
     * return the list id. If the name is found, it will return that list id. If
     * the id or name doesn't exist, it returns -1.
     * @param listId (int) The list name or id to be parsed
     * @param itemName (String) The item name or id to be parsed
     * @return (int) Returns the associated list id or -1 if it cannot be found
     */
    public static int getIdFromItemName(int listId, String itemName) {
        if(isInteger(itemName) && Integer.parseInt(itemName) < toDoLists.get(listId).getListItems().size()) {
            return Integer.parseInt(itemName);
        }

        for(TDItem tdItem : toDoLists.get(listId).getListItems()) {
            if(tdItem.getItemName().equals(itemName)) {
                return tdItem.getId();
            }
        }

        return -1;
    }

    /**
     * Checks to see if a String can be parsed as an integer
     * @param listId (String) List or Item ID
     * @return (boolean) Returns True if it can be parsed or False if not
     */
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

    /**
     * Prints out the current lists that are stored / active
     */
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

    /**
     * Exits the application gracefully, before exiting it will attempt to save the user's file
     * if unsuccessful it will still exit and throw error code 1.
     * @throws InterruptedException
     */
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

    /**
     * Initializes the to do application and loads saved lists
     */
    public static void initToDo() {
        loadOutput();
        isInit = true;
    }

    /**
     * Serializes and saves currently active To Do List data into to do.ser
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

    /**
     * Deserializes and saves currently active To Do List data into to do.ser
     * @return (ResultType) Returns either PASS or FAIL to handle error action and alert user
     */
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
            if(isInit) {
                ioException.printStackTrace();
                System.out.println(String.format("%s could not be opened or found", DATA_FILE));
            }
        } catch (ClassNotFoundException classNotFoundException) {
            if(isInit) {
                classNotFoundException.printStackTrace();
                System.out.println("Classes could not be loaded into the ToDo List");
            }
        }

        return loadResult;
    }

}

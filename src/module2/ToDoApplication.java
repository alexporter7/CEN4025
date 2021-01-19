package module2;

public class ToDoApplication {

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

    public static void main(String[] args) {
        TDItem item = new TDItem(0, "test", "test");
    }

}

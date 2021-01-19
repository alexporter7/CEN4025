package module2;

import java.time.LocalDateTime;

public class TDItem {

    private int id;
    private String itemName;
    private String listName;
    private LocalDateTime dueDate;

    /**
     * @param id Unique identifier given to item
     * @param itemName Name of the Item that will be displayed
     * @param listName Name of the List that the item will be attatched to
     */
    public TDItem(int id, String itemName, String listName) {
        this.id = id;
        this.itemName = itemName;
        this.listName = listName;
        this.dueDate = null;
    }

    /**
     * @param id Unique identifier given to item
     * @param itemName Name of the Item that will be displayed
     * @param listName Name of the List that the item will be attatched to
     */
    public TDItem(int id, String itemName, String listName, LocalDateTime dueDate) {
        this.id = id;
        this.itemName = itemName;
        this.listName = listName;
        this.dueDate = dueDate;
    }

}

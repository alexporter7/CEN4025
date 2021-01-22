package module2;

import java.time.LocalDateTime;

/**
 * <h4>TD Item Class</h4>
 * Used for each item instance
 * <p>
 * This was created by Alex Porter and is provided AS-IS.
 *
 * @author Alex Porter
 * @since 2021-01-19
 */
public class TDItem implements java.io.Serializable {

    private int id;
    private String itemName;
    private String listName;
    private LocalDateTime dueDate;

    public TDItem(String itemName) {
        this.id = 0;
        this.itemName = itemName;
        this.listName = null;
        this.dueDate = null;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}

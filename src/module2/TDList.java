package module2;

import java.util.ArrayList;
import java.util.List;

/**
 * <h4>TD List Class</h4>
 * Used for each list instance
 * <p>
 * This was created by Alex Porter and is provided AS-IS.
 *
 * @author Alex Porter
 * @since 2021-01-19
 */

public class TDList implements java.io.Serializable {

    private int listId;
    private String listName;
    private List<TDItem> listItems;

    /**
     * Initialize the TDList object
     * @param listId (int) The ID of the list
     * @param listName (String) The Name of the list
     */
    public TDList(int listId, String listName) {
        this.listId = listId;
        this.listName = listName;
        this.listItems = new ArrayList<>();
    }

    /**
     * Initialize the TDList object populated with list items
     * @param listId (int) The ID of the list
     * @param listName (String) The Name of the list
     * @param listItems (List<TDItem>) Array List of items
     */
    public TDList(int listId, String listName, List<TDItem> listItems) {
        this.listId = listId;
        this.listName = listName;
        this.listItems = listItems;
    }

    /**
     * Add an item into the list
     * @param item (TDItem) - The item that will be appended to the list
     */
    public void addItem(TDItem item) {
        item.setId(this.listItems != null ? 0 : this.listItems.get(this.listItems.size()).getId() + 1);
        item.setListName(this.listName);
        this.listItems.add(item);
    }

    public String getListName() {
        return this.listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public int getListId() {
        return this.listId;
    }

    public List<TDItem> getListItems() {
        return this.listItems;
    }

    public void setListItems(List<TDItem> listItems) {
        this.listItems = listItems;
    }

    @Override
    public String toString() {
        StringBuilder toStringBuilder = new StringBuilder();
        for(TDItem item : listItems) {
            toStringBuilder.append(String.format(
                    "%s | %s | %s%n", item.getId(), item.getItemName(), item.getListName()
            ));
        }
        return toStringBuilder.toString();
    }
}

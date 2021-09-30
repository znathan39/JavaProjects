import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/*
 * Class Room - a room in an adventure game.
 *
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Room 
{
    private String description;
    private HashMap exits;        // stores exits of this room.
    ArrayList<Item> items = new ArrayList<Item>();

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "in a kitchen" or "in an open court 
     * yard".
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap();
    }

    /**
     * Define an exit from this room.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Return the description of the room (the one that was defined in the
     * constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of this room, in the form:
     *     You are in the kitchen.
     *     Exits: north west
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set keys = exits.keySet();
        for(Iterator iter = keys.iterator(); iter.hasNext(); )
            returnString += " " + iter.next();
        returnString += "\nItems in the room:\n";
        returnString += getRoomItems();
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     */
    public Room getExit(String direction) 
    {
        return (Room)exits.get(direction);
    }

    //Get items from the room
    public Item getItem(int index) {
      return items.get(index);
    }

    public Item getItem(String itemName) {
      for (int i = 0; i < items.size(); i++) {
        if (items.get(i).getDescription().equals(itemName)) {
          return items.get(i);
        }
      }
      return null;
    }

    //remove items from the room
    public void removeItem(String itemName) {
      for (int i = 0; i < items.size(); i++) {
        if (items.get(i).getDescription().equals(itemName)) {
          items.remove(i);
        }
      }
    }

    //Set a particular item in the room
    public void setItem(Item newitem) {
      items.add(newitem);
    }

    //Get a description of the items in a room
    public String getRoomItems() { 
      String output = "";
      for (int i = 0; i < items.size(); i++) {
        output += items.get(i).getDescription() + " ";
      }
      return output;
    }
}


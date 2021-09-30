/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
**/
import java.util.ArrayList;

class Game 
{
    private Parser parser;
    private Room currentRoom;
    Room outside, theatre, pub, lab, office, onetwenty, library, cafeteria, parkinglot, gymnasium, field, lockerroom, portables, auditorium, studio, office2, home;
    ArrayList<Item> inventory = new ArrayList<Item>();
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        onetwenty = new Room("in the coolest place in the world");
        library = new Room("in the Library. No one is here");
        cafeteria = new Room("in the cafeteria");
        parkinglot = new Room("in the parking lot");
        gymnasium = new Room("in the gymnasium");
        field = new Room("in the field");
        lockerroom = new Room("in the locker room");
        portables = new Room("in the portables");
        auditorium = new Room("in the auditorium");
        studio = new Room("in the art studio");
        office2 = new Room("in the university main office");
        home = new Room("on the way home");

        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", onetwenty);

        theatre.setExit("west", outside);
        theatre.setExit("south", studio);
        theatre.setExit("north", auditorium);
        theatre.setExit("east", gymnasium);

        pub.setExit("east", outside);
        pub.setExit("west", cafeteria);

        lab.setExit("north", outside);
        lab.setExit("south", office);

        office.setExit("north", lab);

        onetwenty.setExit("south", outside);
        onetwenty.setExit("north", portables);

        portables.setExit("south", onetwenty);
        portables.setExit("east", field);

        studio.setExit("north", theatre);

        cafeteria.setExit("east", pub);
        cafeteria.setExit("north", library);

        library.setExit("south", cafeteria);

        field.setExit("east",office2);
        field.setExit("west", portables);

        auditorium.setExit("south", theatre);

        gymnasium.setExit("west", theatre);
        gymnasium.setExit("north", lockerroom);
        gymnasium.setExit("east", parkinglot);

        parkinglot.setExit("west", gymnasium);
        parkinglot.setExit("south", home);

        lockerroom.setExit("south", gymnasium);

        office2.setExit("west", field);

        office.setExit("north", lab);

        home.setExit("north", parkinglot);

        currentRoom = outside;  // start game outside

        //All the items in their rooms
        inventory.add(new Item("computer"));
        onetwenty.setItem(new Item("robot"));
        lab.setItem(new Item("keyboard"));
        office.setItem(new Item("laptop"));
        pub.setItem(new Item("bottle"));
        cafeteria.setItem(new Item("pizza"));
        library.setItem(new Item("book"));
        field.setItem(new Item("ball"));
        office2.setItem(new Item("phone"));
        theatre.setItem(new Item("popcorn"));
        studio.setItem(new Item("brush"));
        auditorium.setItem(new Item("pamphlet"));
        gymnasium.setItem(new Item("towel"));
        lockerroom.setItem(new Item("lock"));
        parkinglot.setItem(new Item("car"));

    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Adventure!");
        System.out.println("Adventure is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            wantToQuit = goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("inventory")) {
          printInventory();
        }
        else if (commandWord.equals("get")) {
          getItem(command);
        }
        else if (commandWord.equals("drop")) {
          dropItem(command);
        }
        return wantToQuit;
    }

    //get item command
    private void getItem(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to pick up...
            System.out.println("Get what?");
            return;
        }

        String item = command.getSecondWord();

        //Get an item
        Item newItem = currentRoom.getItem(item);

        if (newItem == null) {
          System.out.println("That item is not here!");
        }
        else {
          inventory.add(newItem);
          currentRoom.removeItem(item);
          System.out.println("Picked up: " + item);
        }
    }

    //drop item command
    private void dropItem(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }

        String item = command.getSecondWord();

        //Drop an item
        Item newItem = null;
        int index = 0;
        for (int i = 0; i < inventory.size(); i++) {
          if (inventory.get(i).getDescription().equals(item)) {
            newItem = inventory.get(i);
            index = i;
          }
        }

        if (newItem == null) {
          System.out.println("That item is not in your inventory!");
        }
        else {
          inventory.remove(index);
          currentRoom.setItem(new Item(item));
          System.out.println("Dropped: " + item);
        }
    }

    //See whats in inventory
    private void printInventory() {
      String output = "";
      for (int i = 0; i < inventory.size(); i++) {
        output += inventory.get(i).getDescription() + " ";
      }
      System.out.println("You are carrying:");
      System.out.println(output);
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("If you are lost and need help, here is map of the place");
        System.out.println("https://docs.google.com/drawings/d/1oG1KxB3yH0X6Bhuim3RcQOgsVhaNhso-t_n2ayu-iT0/edit?usp=sharing");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private boolean goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return false;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null)
            System.out.println("There is no door!");
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());

            if (currentRoom == home && inventory.size() == 15) {//Win Condition
              System.out.println("You win!");
              return true;
            }
        }
        return false;
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else
            return true;  // signal that we want to quit
    }
}

import java.util.HashMap;
import java.util.Map;

/**
 * =========================================================
 * ABSTRACT CLASS - Room
 * =========================================================
 * @version 4.1
 */
abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

/**
 * =========================================================
 * ROOM TYPES
 * =========================================================
 * @version 4.1
 */
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

/**
 * =========================================================
 * CLASS - RoomInventory
 * =========================================================
 * Centralized inventory (read + write, but search uses read only)
 *
 * @version 4.1
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // (Write method exists but NOT used in search use case)
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public Map<String, Integer> getAllInventory() {
        return inventory;
    }
}

/**
 * =========================================================
 * CLASS - RoomSearchService
 * =========================================================
 * Handles READ-ONLY search logic
 *
 * @version 4.0 (new class)
 */
class RoomSearchService {

    private RoomInventory inventory;
    private Map<String, Room> roomMap;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;

        // Map room types to their objects (domain model)
        roomMap = new HashMap<>();
        roomMap.put("Single", new SingleRoom());
        roomMap.put("Double", new DoubleRoom());
        roomMap.put("Suite", new SuiteRoom());
    }

    /**
     * Displays only available rooms (availability > 0)
     * READ-ONLY operation
     */
    public void searchAvailableRooms() {
        System.out.println("Available Rooms:\n");

        for (String type : roomMap.keySet()) {
            int available = inventory.getAvailability(type);

            // Filter unavailable rooms
            if (available > 0) {
                System.out.println(type + " Room:");
                roomMap.get(type).displayRoomDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

/**
 * =========================================================
 * MAIN CLASS - UseCase4RoomSearch
 * =========================================================
 * @version 4.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Hotel Room Search (Read-Only)\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Perform search (READ ONLY)
        searchService.searchAvailableRooms();
    }
}
package md.utm.tmps.lab3.domain.models;

/**
 * Storage component (SSD, HDD, etc.)
 */
public class Storage extends Component {
    private final int capacity; // in GB
    private final String type; // SSD, HDD, NVMe

    public Storage(String name, double price, int capacity, String type) {
        super(name, price, "Storage");
        this.capacity = capacity;
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("%s - %d GB %s - $%.2f", 
            getName(), capacity, type, getPrice());
    }
}

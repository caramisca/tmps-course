package md.utm.tmps.lab3.domain.models;

/**
 * RAM (Random Access Memory) component
 */
public class RAM extends Component {
    private final int capacity; // in GB
    private final int speed; // in MHz

    public RAM(String name, double price, int capacity, int speed) {
        super(name, price, "RAM");
        this.capacity = capacity;
        this.speed = speed;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return String.format("%s - %d GB @ %d MHz - $%.2f", 
            getName(), capacity, speed, getPrice());
    }
}

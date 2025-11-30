package md.utm.tmps.lab3.domain.models;

/**
 * CPU (Central Processing Unit) component
 */
public class CPU extends Component {
    private final int cores;
    private final double clockSpeed; // in GHz

    public CPU(String name, double price, int cores, double clockSpeed) {
        super(name, price, "CPU");
        this.cores = cores;
        this.clockSpeed = clockSpeed;
    }

    public int getCores() {
        return cores;
    }

    public double getClockSpeed() {
        return clockSpeed;
    }

    @Override
    public String toString() {
        return String.format("%s - %d cores @ %.1f GHz - $%.2f", 
            getName(), cores, clockSpeed, getPrice());
    }
}

package md.utm.tmps.lab3.domain.models;

/**
 * GPU (Graphics Processing Unit) component
 */
public class GPU extends Component {
    private final int vram; // in GB

    public GPU(String name, double price, int vram) {
        super(name, price, "GPU");
        this.vram = vram;
    }

    public int getVram() {
        return vram;
    }

    @Override
    public String toString() {
        return String.format("%s - %d GB VRAM - $%.2f", 
            getName(), vram, getPrice());
    }
}

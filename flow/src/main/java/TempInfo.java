import java.util.Random;

/**
 * @author deer
 * @date 2021-12-09
 */
public class TempInfo {
    private static final Random RANDOM = new Random();
    private final String town;
    private final int temp;

    public TempInfo(String town, int temp) {
        this.town = town;
        this.temp = temp;
    }

    public static TempInfo fetch(String town) {
        if (RANDOM.nextInt(10) == 0) {
            throw new RuntimeException("Error!");
        }
        return new TempInfo(town, RANDOM.nextInt(100));
    }

    @Override
    public String toString() {
        return "TempInfo{" +
                "town='" + town + '\'' +
                ", temp=" + temp +
                '}';
    }

    public String getTown() {
        return town;
    }

    public int getTemp() {
        return temp;
    }
}

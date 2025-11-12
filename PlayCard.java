
/**
 * This class has been completed for you.
 * You should not make any change on this class.
 * When we compile your program, we will use this exact same class.
 */
public class PlayCard {
    public final String color;
    public final String type;
   
    public PlayCard(String color, String type) {
        this.color = color;
        this.type = type;
    }
    
    public String toString() {
        return "PlayCard{" + color + "," + type + "}";
    }
    
    public String getColor() {
        return color;
    }
    
    public String getType() {
        return type;
    }
    
    public int getSteps() {
        if (type.equals("+1")) {
            return 1;
        } else if (type.equals("+2")) {
            return 2;
        } else if (type.equals("-1")) {
            return -1;
        } else if (type.equals("-2")) {
            return -2;
        }
        return 0;
    }
}

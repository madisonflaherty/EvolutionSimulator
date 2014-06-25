/**
 * @author Madison Flaherty (mef4824)
 *         $Log$
 *         $Id$
 */

/**
 * The apple class. Represents a single apple
 */
public class Apple {
    int location;

    /**
     * Apple constructor. Currently only information about an apple is its location.
     * @param location - the location (vertically) on the tree
     */
    public Apple(int location){
        this.location = location;
    }

    /**
     * Returns the location of the apple
     * @return the location of the apple (vertically).
     */
    public int getLocation() {
        return location;
    }
}

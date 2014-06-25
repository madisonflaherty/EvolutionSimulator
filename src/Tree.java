/**
 * @author Madison Flaherty (mef4824)
 *         $Log$
 *         $Id$
 */
import java.util.ArrayList;

/**
 * The Tree class, represents a single tree (apple tree).
 */
public class Tree {
    int numOfStartingApples;
    int numOfApples = 0;
    int minHeight = 1;
    int maxHeight = 15;
    ArrayList<Apple> applesOnTree = new ArrayList<Apple>();

    /**
     * Constructor for the tree. Currently requires for the tree to spawn two apples near its base.
     * @param numberOfApples - int, the number of apples that the tree starts out with.
     */
    public Tree(int numberOfApples){
        this.numOfStartingApples = numberOfApples;
        int appleRange = (int)Math.floor(minHeight + Math.random() * (maxHeight - minHeight));
        Apple apple1 = new Apple(1);
        Apple apple2 = new Apple(2);
        applesOnTree.add(apple1);
        applesOnTree.add(apple2);
        numOfApples = numOfApples + 2;
        for (int i = 0; i < this.numOfStartingApples; ++i){
            Apple apple = new Apple(appleRange);
            applesOnTree.add(apple);
            ++numOfApples;
            //System.out.println("A new apple has been added to a total value of " + numOfApples);
        }
    }

    /**
     * Returns the arraylist of apples currently on the tree
     * @return ArrayList<Apple> that are on the tree
     */
    public ArrayList<Apple> getApplesOnTree() {
        return applesOnTree;
    }

    /**
     * Returns the number of apples on the tree currently.
     * @return the number of apples currently on the tree
     */
    public int getNumOfApples() {
        return numOfApples;
    }

    /**
     * Reduces teh numOfApples count by one.
     */
    public void reduceNumOfApples() {
        this.numOfApples--;
    }

    /**
     * Grows new apples whenever it is called.
     */
    public void growApples(){
        int numOfNewApples = (int) Math.floor(Math.random() * 5);
        int appleRange = (int)Math.floor(minHeight + Math.random() * (maxHeight - minHeight));
        for (int i = 0; i < numOfNewApples; ++i){
            Apple apple = new Apple(appleRange);
            applesOnTree.add(apple);
            numOfApples++;
        }
    }
}

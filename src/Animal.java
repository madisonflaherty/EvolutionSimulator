/**
 * @author Madison Flaherty (mef4824)
 *         $Log$
 *         $Id$
 */
import java.util.ArrayList;
import java.util.Collections;
/**
 * The object representation of one animal in the simulation.
 * Recognize that for the Punnet square, the different values have been
 * simplified to numerical equivalent in order to improve efficiency.
 * Ex:
 *      T = 0
 *      t = 1
 *      ei: TT = 0
 *          Tt = 1
 *          tt = 2
 *
 */
public class Animal implements Comparable<Animal>{
    /*The identification number*/
    int id;
    /* The current age of the animal*/
    int age;
    /* The neck height of the animal according to its "punnet" traits*/
    int punnetHeight;
    /* The neck height of the animal according to its actual height*/
    int actualHeight;
    /* Boolean value, if the animal has not eaten after "x" days, it will die. */
    boolean eatenYet = false;
    boolean matedYet = false;
    Environment env;


    /**
     * Constructor for the animal class.
     */
    public Animal(int id, int punnetHeight, int actualHeight, Environment env){
        this.id = id;
        this.punnetHeight = punnetHeight;
        this.actualHeight = actualHeight;
        this.age = 0;
        this.env = env;

    }

    /**
     * Evaluates according to a very general and simple punnet square. Only
     * evaluates one trait (ie: short or tall) at a time.
     * @param punnetValue1 - the punnet value (ie: TT or tt) from one parent.
     * @param punnetValue2 - the punnet value of the other parent.
     * @return - returns a new punnet value that is indicative of the child's trait.
     */
    public int punnetEvaluator(int punnetValue1, int punnetValue2){
        int pv1_1=3;
        int pv1_2=3;
        int pv2_1=3;
        int pv2_2=3;
        ArrayList<Integer> possValues = new ArrayList<Integer>();
        switch(punnetValue1){
            case 0: pv1_1 = 0; pv1_2 = 0; break;
            case 1: pv1_1 = 0; pv1_2 = 1; break;
            case 2: pv1_1 = 1; pv1_2 = 1; break;
        }
        switch(punnetValue2){
            case 0: pv2_1 = 0; pv2_2 = 0; break;
            case 1: pv2_1 = 0; pv2_2 = 1; break;
            case 2: pv2_1 = 1; pv2_2 = 1; break;
        }

        possValues.add(pv1_1 + pv2_1);
        possValues.add(pv1_1 + pv2_2);
        possValues.add(pv1_2 + pv2_1);
        possValues.add(pv1_2 + pv2_2);

        Collections.shuffle(possValues);
        return possValues.get(0);
    }

    /**
     * Evaluates the child's punnet value, compares the parent's actual values (ie: 8 feet),
     * and attempts to create a new actual value that represents the child.
     * @param actValue1 - the actual value of the first parent (ie: 8ft).
     * @param actValue2 - the actual value of the second parent.
     * @param newPunnetValue - the newly determined punnet value of the child (ie: TT)
     * @return - the new actual value of the child.
     */
    public int actualEvaluator(int actValue1, int actValue2, int newPunnetValue){
        int largerValue=0;
        int smallerValue=0;
        if(actValue1 >= actValue2){
            largerValue = actValue1;
            smallerValue = actValue2;
        } else {
            smallerValue = actValue1;
            largerValue = actValue2;
        }

        //if it is a "TT" value, then it has the chance to be any where between the average, and slightly
        //above the larger Valued parent.
        int newValueRangeMin = 0;
        int newValueRangeMax = 0;
        if(newPunnetValue == 0){
            newValueRangeMin = (int) (Math.ceil(((double) largerValue + (double) smallerValue) / 2));
            newValueRangeMax = (largerValue - newValueRangeMin) + largerValue + (int)(Math.ceil(largerValue/5));
        }
        //if it is a "Tt" or "tT" value (equivalent),then it has the chance to be of any value between the two parents.
        else if(newPunnetValue == 1){
            newValueRangeMin = smallerValue;
            newValueRangeMax = largerValue;
        }
        //if it is a "tt" value, then it has the chance to be anywhere between the average, and slightly
        //below the smaller valued parent.
        else if(newPunnetValue == 2){
            newValueRangeMax = (int) (Math.floor(((double) largerValue + (double) smallerValue) / 2));
            newValueRangeMin = smallerValue - (largerValue - newValueRangeMin) - (int)(Math.ceil(largerValue/5));
            if(newValueRangeMin <= 0){
                newValueRangeMin = 1;
            }
        }
        return (int) (Math.floor(newValueRangeMin + Math.random() * (newValueRangeMax-newValueRangeMin)));
    }

    /**
     * Returns the actual height of the animal.
     * @return actual height (ie: 5 ft)
     */
    public int getActualHeight() {
        return actualHeight;
    }

    /**
     * Returns the punnet height of the animal
     * @return punnet height (ie: Tt)
     */
    public int getPunnetHeight() {
        return punnetHeight;
    }

    /**
     * Returns the ID of this animal
     * @return Id (integer) of the animal
     */
    public int getId() {
        return id;
    }

    /**
     * Returns whether or not the animal has mated yet.
     * @return boolean value, true if the animal has mated during this year.
     */
    public boolean isMatedYet() {
        return matedYet;
    }

    /**
     * Allows the mated variable to be set.
     * @param matedYet - the new mated value (either true or false)
     */
    public void setMatedYet(boolean matedYet) {
        this.matedYet = matedYet;
    }

    /**
     * Returns whether or not the animal has eaten yet.
     * @return boolean, if the animal has eaten (to as much as it needs to in order
     * to survive, it will return true.
     */
    public boolean hasEatenYet() {
        return eatenYet;
    }

    /**
     * Allows the eatenYet variable to be set.
     * @param eatenYet - the new eatenYet value (either true or false)
     */
    public void setEatenYet(boolean eatenYet) {
        this.eatenYet = eatenYet;
    }

    /**
     * Very general Mating method. Will be changed as the program changes to include
     * all of the different possible traits that can be passed on.
     * @param otherAnimal the other animal that is being mated with
     * @return Animal that was born during the mating.
     */
    public Animal mate(Animal otherAnimal){
        int newPunnetHeight = punnetEvaluator(this.punnetHeight, otherAnimal.getPunnetHeight());
        int newActualHeight = actualEvaluator(this.actualHeight, otherAnimal.getActualHeight(), newPunnetHeight);
        Animal newAnimal = new Animal(env.getNextId(), newPunnetHeight, newActualHeight, env);
        return newAnimal;
    }

    /**
     * Very general Eating method. Will be changed later to include different ways
     * that animals can eat.
     * @param tree the current tree
     * @return returns true if the
     */
    public boolean eat(Tree tree){
        for(Apple reachableApple : tree.getApplesOnTree()){
            if(reachableApple.getLocation() <= this.getActualHeight()){
                tree.reduceNumOfApples();
                this.setEatenYet(true);
                tree.getApplesOnTree().remove(reachableApple);
                return true;
            } else {
                this.setEatenYet(false);
            }
        }
        return false;
    }

    /**
     * Compare to method. This method compares the animal's id's for proper sorting.
     * @param otherAnimal the animal being compared.
     * @return The value that is returned determines if one animal is "before" another
     * according to their ids
     */
    public int compareTo(Animal otherAnimal){
        return (this.getId() - otherAnimal.getId());
    }



}

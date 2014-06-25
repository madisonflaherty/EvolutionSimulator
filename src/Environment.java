/**
 * @author Madison Flaherty (mef4824)
 *         $Log$
 *         $Id$
 */
import java.util.ArrayList;
import java.util.Collections;
/**
 * Runs
 */
public class Environment {
    int maxYears = 20;
    int nextId = 3;
    Tree tree;
    ArrayList<Animal> animals = new ArrayList<Animal>();

    public Environment(){
        this.tree = new Tree(20);
        start();
    }
    public static void main(String args[]){
        Environment environ = new Environment();
    }

    public int getNextId() {
        this.nextId++;
        return nextId-1;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public void start(){
        //Currently the first two animals are hardcoded in. Will be changed later.
        Animal Adam = new Animal(1, 1, 8, this);
        Animal Eve = new Animal(2,1, 4, this);
        animals.add(Adam);
        animals.add(Eve);
        // For every year until final year
        for(int i = 0; i < maxYears; i++){
            tree.growApples();
            ArrayList<Animal> survivedAnimals = new ArrayList<Animal>();
            System.out.println("START OF YEAR " + i);
            System.out.println("--------------------------------------");

            //Animals eat to survive
            ArrayList<Animal> managedToEat = eating(survivedAnimals);
            //Animals mate to pass on genes
            ArrayList<Animal> babyAnimals = mating(managedToEat);

            //Gets rid of previous animals.
            animals.clear();
            //Adds animals that survived the year back in and also adds in the babies
            animals.addAll(managedToEat);
            animals.addAll(babyAnimals);

            Collections.sort(animals);
            for(Animal animal : animals){
                System.out.println("    Animal " + animal.getId() + " is " + animal.getActualHeight() + " feet tall and is a " + animal.getPunnetHeight());
            }
            Collections.shuffle(animals);

        }
    }

    /**
     * Method used for making each of the animals in the system eat as they need to.
     * @param survivedAnimals Animals that have
     * @return
     */
    public ArrayList<Animal> eating(ArrayList<Animal> survivedAnimals){
        Collections.shuffle(animals);
        for(Animal anim : animals){
            int counter=0;
            anim.setMatedYet(false);
            for(Apple apple : tree.getApplesOnTree()){
                if(apple.getLocation() <= anim.getActualHeight()){
                    counter++;
                }
            }
            if(anim.eat(tree)){
                survivedAnimals.add(anim);
                System.out.println("Animal " + anim.getId() + " has eaten an apple.");

                System.out.println("There were " + counter + " apples that it could reach.");
                System.out.println("There were exactly " + tree.getNumOfApples() + " on the tree.");


            } else {
                System.out.println("xxxxxxxx   Animal " + anim.getId() + " has died of starvation.  xxxxxxxxx");
                System.out.println("There were " + counter + " apples it could have eaten though");
            }
        }
        return survivedAnimals;
    }

    /**
     * Method used for making each of the animals mate as they do.
     * @param survivedAnimals the animals that have survived up until this point.
     * @return returns a list of animals that were born during this year
     */
    public ArrayList<Animal> mating(ArrayList<Animal> survivedAnimals){
        ArrayList<Animal> babyAnimals = new ArrayList<Animal>();
        for(Animal animal : survivedAnimals){
            //If the animal has not yet mated
            if(!animal.isMatedYet()){
                for(Animal possMate : survivedAnimals){
                    if(!animal.isMatedYet()){
                        if(!possMate.isMatedYet()){
                            //find an animal that is not itself that has also
                            //not mated yet.
                            if(possMate.getId() != animal.getId()){
                                Animal babyAnimal = animal.mate(possMate);
                                babyAnimals.add(babyAnimal);
                                animal.setMatedYet(true);
                                possMate.setMatedYet(true);
                                System.out.println("Animal " + animal.getId() + " mated with Animal " + possMate.getId() + " and had Animal " + babyAnimal.getId());

                            }
                        }
                    }
                }
            }

        }
        return babyAnimals;

    }
}

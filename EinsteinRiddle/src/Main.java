import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		System.out.println("*********************** Start ***********************");
		// Initialize the solver. 
		EinsteinConstraintSolver solver = new EinsteinConstraintSolver();
		printVariables(solver.getVariables());
		
		/*
		 * Add Constraints
		 * 1. All different Constraint 
		 * 2. All constraints from the riddle.
		 */
		
		// All variables should be different! 
		List<Variable> variableList = new ArrayList<>(solver.getVariables().values());
		for(Variable var : variableList){
			for(Variable var2 : variableList){
				// if the variables have the same componentType (for example "colour"), but are different variables (for example "Red" and "Blue")
				if(var.getComponentType().equals(var2.getComponentType()) && !var.equals(var2)){
					solver.addConstraint(ConstraintType.unequal, var.getName(), var2.getName());
					solver.addConstraint(ConstraintType.unequal, var2.getName(), var.getName());
				}
			}
		}
		
		// 1. The britain lives in the red house
		solver.addConstraint(ConstraintType.equal, "Britain", "Red");
		solver.addConstraint(ConstraintType.equal, "Red", "Britain");
		// 2. The swede has a dog.
		solver.addConstraint(ConstraintType.equal, "Swede", "Dog");
		solver.addConstraint(ConstraintType.equal, "Dog", "Swede");
		// 3. The dane likes drinking tea.
		solver.addConstraint(ConstraintType.equal, "Dane", "Tea");
		solver.addConstraint(ConstraintType.equal, "Tea", "Dane");
		// 4. The green house is left to the white house.
		solver.addConstraint(ConstraintType.leftTo, "Green", "White");
		solver.addConstraint(ConstraintType.rightTo, "White", "Green");
		// 5. The owner of the green house likes drinking coffee.
		solver.addConstraint(ConstraintType.equal, "Green", "Coffee");
		solver.addConstraint(ConstraintType.equal, "Coffee", "Green");
		// 6. The person who smokes pall mall has got a bird.
		solver.addConstraint(ConstraintType.equal, "PallMall", "Bird");
		solver.addConstraint(ConstraintType.equal, "Bird", "PallMall");
		// 7. The man who lives in the middle house likes drinking milk.
		solver.addConstraint(ConstraintType.middle, "Milk", "Milk"); // therefore House 3.
		// 8. The owner of the yellow house smokes dunhill.
		solver.addConstraint(ConstraintType.equal, "Yellow", "Dunhill");
		solver.addConstraint(ConstraintType.equal, "Dunhill", "Yellow");
		// 9. The norwegian lives in the first house.
		solver.addConstraint(ConstraintType.first, "Norwegian", "Norwegian"); // therefore House 1.
		// 10. The Malboro guy lives next to the person who has got a cat.
		solver.addConstraint(ConstraintType.nextTo, "Malboro", "Cat");
		solver.addConstraint(ConstraintType.nextTo, "Cat", "Malboro");
		// 11. The man with the horse lives next to the person who smokes dunhill.
		solver.addConstraint(ConstraintType.nextTo, "Horse", "Dunhill");
		solver.addConstraint(ConstraintType.nextTo, "Dunhill", "Horse");
		// 12. The Winfield guy likes drinking beer.
		solver.addConstraint(ConstraintType.equal, "Winfield", "Beer");
		solver.addConstraint(ConstraintType.equal, "Beer", "Winfield");
		// 13. The Norwegian lives next to the blue house.
		solver.addConstraint(ConstraintType.nextTo, "Norwegian", "Blue"); // therefore House 2.
		solver.addConstraint(ConstraintType.nextTo, "Blue", "Norwegian");
		// 14. The german smokes rothmanns.
		solver.addConstraint(ConstraintType.equal, "German", "Rothmanns");
		solver.addConstraint(ConstraintType.equal, "Rothmanns", "German");
		// 15. The malboro guy has got a neighbour who drinks water.
		solver.addConstraint(ConstraintType.nextTo, "Malboro", "Water");
		solver.addConstraint(ConstraintType.nextTo, "Water", "Malboro");
		
		printConstraints(solver.getConstraints());
		
		System.out.println("\nStart solving the problem...");
		solver.solveRiddle();
		printVariables(solver.getVariables());
	}

	/**
	 * Prints all variables
	 */
	public static void printVariables(Map<String, Variable> variables){
		String output = "Variables:\n";
		int i=0;
        for(Map.Entry<String, Variable> entry: variables.entrySet()){
            output+= "----> Nr. " + i + ": " + entry.getValue() + "\n";
            i+=1;
        }
        System.out.println(output);
	}
	
	/**
	 * Prints all constraints
	 */
	public static void printConstraints(Set<Constraint> constraints){
		String output = "Constraints:\n";
		Iterator<Constraint> constraintIterator = constraints.iterator();
		int i=0;
		while(constraintIterator.hasNext()){
			output+= "----> Nr. " + i + ": " + constraintIterator.next() + "\n";
			i++;
		}
		System.out.println(output);
	}
}

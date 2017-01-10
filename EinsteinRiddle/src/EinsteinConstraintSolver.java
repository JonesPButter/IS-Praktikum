import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class EinsteinConstraintSolver {

	// Contains all Variables (25 in total for this riddle)
	Map<String, Variable> variables;
	// Contains all Constraints. (Edges between variables)
	Set<Constraint> constraints;

	/**
	 * Initializes the constrains solver class.
	 */
	public EinsteinConstraintSolver() {
		variables = new HashMap<>();
		initializeVariables();
		constraints = new HashSet<>();
	}

	public void ac3() {
		Queue<Constraint> queue = new LinkedList<>(constraints);
		while (!queue.isEmpty()) {
			Constraint currentConstraint = queue.poll();
			if (revise(currentConstraint)) {
				for (Constraint otherConstraint : constraints) {
					// wenn der andere constraint auf den current constraint "zeigt"
					if (otherConstraint.getToVar().equals(currentConstraint.getFromVar())) {
						// keine zyklische Kante
						if (otherConstraint.getFromVar().equals(otherConstraint.getToVar()) || otherConstraint.getFromVar().equals(currentConstraint.getToVar())) {
							continue;
						}
						queue.add(otherConstraint);
					}
				}
			}
		}
	}

	/*
	 * Removes all inconsistent values in the Domain of D(x)
	 */
	private boolean revise(Constraint constraint) {
		boolean removed = false;
		// D(x)
		List<Integer> domain = constraint.getFromVar().getDomain();
		for(int value : new ArrayList<>(domain)){
			// remove all values from D(x) that doesn't pass the constraint.
			if (!check(constraint, value)) {
				domain.remove(domain.indexOf(value));
				removed = true;
			}
		}
		return removed;
	}

	private boolean check(Constraint constraint, Integer house1) {
		for (int house2 : constraint.getToVar().getDomain()) {
			if (ConstraintChecker.check(constraint.getContraintType(), house1, house2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds a Constraint
	 * 
	 * @param type
	 *            - the Type of the Constraint. For example "equal" or "left to"
	 * @param from
	 *            - the Name of the Variable from where the constraint-Edge
	 *            begins. For example "Britain"
	 * @param to
	 *            - the Name of the Variable where the constraint-Edge ends. For
	 *            example "Red"
	 */
	public void addConstraint(ConstraintType type, String from, String to) {
		Variable v1 = variables.get(from);
		Variable v2 = variables.get(to);
		constraints.add(new Constraint(type, v1, v2));
	}

	public Map<String, Variable> getVariables() {
		return variables;
	}

	public Set<Constraint> getConstraints() {
		return constraints;
	}

	/**
	 * Initializes almost all Variables with a domain containing values from 1
	 * to 5 (house number). Only for the Variables "Norwegian", "Blue" and
	 * "Milk" we allready know exactly where they live. Therefore we can assign
	 * them the correct domain in the first place.
	 * 
	 * @return all variables necessary for the einstein riddle
	 */
	private void initializeVariables() {
		List<Integer> domain = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5 });
		List<Integer> firstHouseDomain = Arrays.asList(new Integer[] { 1 });
		List<Integer> secondHouseDomain = Arrays.asList(new Integer[] { 2 });
		List<Integer> middleHouseDomain = Arrays.asList(new Integer[] { 3 });

		// nation variables
		addVariable("Britain", "Nation", domain);
		addVariable("Swede", "Nation", domain);
		addVariable("Dane", "Nation", domain);
		// "The norwegian lives in the first house."
		addVariable("Norwegian", "Nation", firstHouseDomain); 
		addVariable("German", "Nation", domain);

		addVariable("Red", "Colour", domain);
		addVariable("Green", "Colour", domain);
		addVariable("White", "Colour", domain);
		addVariable("Yellow", "Colour", domain);
		// The norwegian lives next to the blue house
		addVariable("Blue", "Colour", secondHouseDomain); 

		addVariable("Dog", "Pet", domain);
		addVariable("Bird", "Pet", domain);
		addVariable("Cat", "Pet", domain);
		addVariable("Horse", "Pet", domain);
		addVariable("Fish", "Pet", domain);

		addVariable("Tea", "Beverage", domain);
		addVariable("Coffee", "Beverage", domain);
		// the man in the middle house likes drinking milk.
		addVariable("Milk", "Beverage", middleHouseDomain); 
		addVariable("Beer", "Beverage", domain);
		addVariable("Water", "Beverage", domain);

		addVariable("PallMall", "Cigarette", domain);
		addVariable("Dunhill", "Cigarette", domain);
		addVariable("Malboro", "Cigarette", domain);
		addVariable("Winfield", "Cigarette", domain);
		addVariable("Rothmanns", "Cigarette", domain);
	}

	private void addVariable(String name, String type, List<Integer> domain) {
		variables.put(name, new Variable(type, name, domain));
	}
}

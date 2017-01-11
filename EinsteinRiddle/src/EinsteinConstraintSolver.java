import java.util.*;
import java.util.stream.Collectors;

public class EinsteinConstraintSolver {

    private int reviseCount = 0;

    // Contains all Variables (25 in total for this riddle)
    private Map<String, Variable> variables;

    // Contains all Constraints. (Edges between variables)
    private Set<Constraint> constraints;

    /**
     * Initializes the constrains solver class.
     */
    public EinsteinConstraintSolver() {
        variables = new HashMap<>();
        initializeVariables();
        constraints = new HashSet<>();
    }

    public Variable getVariable(String name) {
        return variables.get(name);
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
        reviseCount++;

        boolean removed = false;

        // D(x)
        List<Integer> domain = constraint.getFromVar().getDomain();
        for (int value : new ArrayList<>(domain)) {

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
     * @param type - the Type of the Constraint. For example "equal" or "left to"
     * @param from - the Name of the Variable from where the constraint-Edge begins. For example "Britain"
     * @param to   - the Name of the Variable where the constraint-Edge ends. For example "Red"
     */
    public void addConstraint(ConstraintType type, String from, String to) {
        constraints.add(new Constraint(type, from, to, this));
    }

    public Map<String, Variable> getVariables() {
        return variables;
    }

    public Set<Constraint> getConstraints() {
        return constraints;
    }

    private void updateVariableOrdering() {
        List<Variable> list = new ArrayList<>(variables.values());

        list.sort((a, b) -> {
            int tmp = Integer.compare(a.getDomain().size(), b.getDomain().size());
            if (tmp != 0) {
                return tmp;
            }

            return a.getName().compareTo(b.getName());
        });

        int i = 0;
        for (Variable v : list) {
            v.index = i;
            i++;
        }
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
        List<Integer> domain = Arrays.asList(new Integer[] {1, 2, 3, 4, 5});
        List<Integer> firstHouseDomain = Arrays.asList(new Integer[] {1});
        List<Integer> secondHouseDomain = Arrays.asList(new Integer[] {2});
        List<Integer> middleHouseDomain = Arrays.asList(new Integer[] {3});

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

    public boolean ac3la(Variable variable) {

        // filter constraints so we only look at those that are affected by the current change
        Queue<Constraint> currentQueue = constraints.stream()
                .filter(x -> x.getFromVar().compareTo(variable) > 0 && x.getToVar().compareTo(variable) == 0)
                .collect(Collectors.toCollection(LinkedList::new));
        boolean isConsistent = true;

        while (!currentQueue.isEmpty() && isConsistent) {

            Constraint currentConstraint = currentQueue.poll();
            if (revise(currentConstraint)) {

                // add new entries to queue
                constraints.stream()

                        // get all constraints that target the start of the current constraint
                        .filter(x -> x.getToVar().equals(currentConstraint.getFromVar()))

                        // exclude cyclic constraints
                        .filter(x -> !x.getToVar().equals(x.getFromVar()))

                        // exclude the reverse contraint
                        .filter(x -> !x.getFromVar().equals(currentConstraint.getToVar()))

                        // only take those that affect variables we did not handle within soveRiddle()
                        .filter(x -> x.getFromVar().compareTo(variable) > 0)

                        // add entries to queue
                        .forEach(currentQueue::add);

                isConsistent = !currentConstraint.getFromVar().getDomain().isEmpty();
            }
        }

        return isConsistent;
    }

    public void solveRiddle() {

        // create edge consistency
        ac3();

        // create proper ordering
        updateVariableOrdering();

        List<Variable> sorted = new ArrayList<>(variables.values());
        sorted.sort(Variable::compareTo);

        for (int i = 0; i < sorted.size(); i++) {
            Variable currentVariable = sorted.get(i);

            List<Variable> variablesBackup = createVariableBackup(sorted);
            boolean valueFound = false;
            while (!valueFound) {
                if (currentVariable.getDomain().size() > 1) {
                    List<Integer> domainBackup = new ArrayList<>(currentVariable.getDomain());
                    List<Integer> testDomain = new ArrayList<>();

                    // try first possible value
                    testDomain.add(currentVariable.getDomain().get(0));
                    currentVariable.setDomain(testDomain);

                    // check if the currently set domain causes "trouble"
                    if (!ac3la(currentVariable)) {

                        // reset local & global state
                        sorted = variablesBackup;
                        variablesBackup.forEach(x -> variables.put(x.getName(), x));

                        // remove the value that failed from backup domain
                        domainBackup.remove(0);

                        // update local & global state
                        currentVariable.setDomain(domainBackup);
                        variables.put(currentVariable.getName(), currentVariable);
                    } else {
                        valueFound = true;
                    }
                } else if (currentVariable.getDomain().isEmpty()) {
                    System.out.println("No value possible for variable " + currentVariable.getName());
                    break;
                } else {
                    valueFound = true;
                }
            }
        }

        System.out.println("Revise count: " + reviseCount);
    }

    private List<Variable> createVariableBackup(List<Variable> variables) {
        List<Variable> backup = new ArrayList<>(variables.size());
        for (Variable current : variables) {
            backup.add(new Variable(current));
        }
        return backup;
    }
}

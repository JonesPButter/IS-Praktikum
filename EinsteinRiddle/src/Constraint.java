
/**
 * Implementation of an Edge.
 * 
 * @author abq307
 *
 */
public class Constraint {
	
	// the type of the Constraint between the two houses
	ConstraintType contraintType;

	// start
	String from;

	// end
	String to;

	EinsteinConstraintSolver solver;
	/**
	 * 
	 * @param contraintType the constraint
	 * @param from from
	 * @param to to
	 */
	public Constraint(ConstraintType contraintType, String from, String to, EinsteinConstraintSolver solver) {
		super();
		this.contraintType = contraintType;  
		this.from = from;
		this.to = to;
		this.solver = solver;
	}

	public ConstraintType getContraintType() {
		return contraintType;
	}


	public Variable getFromVar() {
		return solver.getVariable(from);
	}


	public Variable getToVar() {
		return solver.getVariable(to);
	}

	@Override
	public String toString() {
		return from + " " + contraintType + " " + to;
	}
	
	
}

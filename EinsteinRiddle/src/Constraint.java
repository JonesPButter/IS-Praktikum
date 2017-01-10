
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
	Variable fromVar;
	// end
	Variable toVar;
	
	/**
	 * 
	 * @param contraintType the constraint
	 * @param fromVar from
	 * @param toVar to
	 */
	public Constraint(ConstraintType contraintType, Variable fromVar, Variable toVar) {
		super();
		this.contraintType = contraintType;  
		this.fromVar = fromVar;
		this.toVar = toVar;
	}

	public ConstraintType getContraintType() {
		return contraintType;
	}

	public void setContraintType(ConstraintType contraintType) {
		this.contraintType = contraintType;
	}

	public Variable getFromVar() {
		return fromVar;
	}

	public void setFromVar(Variable fromVar) {
		this.fromVar = fromVar;
	}

	public Variable getToVar() {
		return toVar;
	}

	public void setToVar(Variable toVar) {
		this.toVar = toVar;
	}

	@Override
	public String toString() {
		return "Constraint [contraintType=" + contraintType + ", fromVar=" + fromVar + ", toVar=" + toVar + "]";
	}
	
	
}

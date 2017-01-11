import java.util.LinkedList;
import java.util.List;

public class Variable implements Comparable<Variable> {

    int index;

    private String componentType;

    private String name;

    private List<Integer> domain;

    /**
     * Inits the Variable
     *
     * @param componentType the name of the component, for example "Colour"
     * @param name          the Name of the Variable, for example "Blue"
     */
    public Variable(String componentType, String name, List<Integer> domain) {
        super();
        this.componentType = componentType;
        this.name = name;
        this.domain = new LinkedList<>(domain);
    }

    public Variable(Variable var) {
        this(var.getComponentType(), var.getName(), var.getDomain());
        index = var.index;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getDomain() {
        return domain;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setDomain(List<Integer> domain) {
        this.domain = domain;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((componentType == null) ? 0 : componentType.hashCode());
        result = prime * result + ((domain == null) ? 0 : domain.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Variable other = (Variable) obj;
        if (componentType == null) {
            if (other.componentType != null)
                return false;
        } else if (!componentType.equals(other.componentType))
            return false;
        if (domain == null) {
            if (other.domain != null)
                return false;
        } else if (!domain.equals(other.domain))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name + " " + domain;
    }

    @Override
    public int compareTo(Variable o) {
        return Integer.compare(index, o.index);
    }
}

/*
 * @author
 * Iraku Harry, School of Computing and Information Technology, College of Computing and Information Sciences,
 * Makerere University
 * iraqooh@gmail.com
 */
package harry;

/*
 * The Propositon class describes the attributes of a propositional variable 
 * such as the statement and its truth value.
 */
public class Proposition
{
    private String statement;
    private boolean truthValue;

    /*
     * Empty parameter constructor for creating a Proposition object
     * with default valued attributes
     */
    public Proposition()
    {
        this.statement = new String();
        this.truthValue = false;
    }

    /*
     * Constructor for creating Proposition objects with the truth value
     * attribute initialized with the parameter truthValue
     * 
     * @param   truthValue   the truth value of the Proposition object.
     */
    public Proposition(boolean truthValue)
    {
        this.statement = new String();
        this.truthValue = truthValue;
    }

    /**
     * Constructor to create a Propositional variable object with
     * the statement of fact and its truth value attributes initialized.
     * 
     * @param   statement   the propositional statement of the object.
     * @param   truthValue   the truth value of the Proposition object.
     */
    public Proposition(String statement, boolean truthValue)
    {
        this.statement = statement;
        this.truthValue = truthValue;
    }

    /**
     * Constructor to create a Propositional variable object with
     * the statement of fact attribute initialized.
     * 
     * @param   statement   the propositional statement of the object.
     */
    public Proposition(String statement)
    {
        this.statement = statement;
        this.truthValue = false;
    }

    /**
     * Instance method to retrieve the truth value of the Proposition
     * object variable.
     * 
     * @return   the truth value of the Proposition object.
     */
    public boolean getValue()
    {
        return this.truthValue;
    }

    /**
     * Instance method to retrieve the statement of fact of the Proposition
     * object variable.
     * 
     * @return   the statement of fact describing the Proposition object
     * variable.
     */
    public String getStatement()
    {
        return this.statement;
    }

    /*
     * Instance method to set the truth value of the this Proposition variable
     * 
     * @param   truthValue  the boolean value to set for this proposition variable object
     * @return  void
     */
    public void setValue(boolean truthValue)
    {
        this.truthValue = truthValue;
    }

    /*
     * Instance method to set the propositional statement of the this Proposition variable
     * 
     * @param   statement  the statement that describes this proposition variable object
     * @return  void
     */
    public void setStatement(String statement)
    {
        this.statement = statement;
    }

    /**
     * Instance method that combines this proposition with another proposition, q,
     * into a new proposition whose truth value is true if and only if the 
     * truth values of the calling proposition and q are true.
     * 
     * @param   q   the proposition to be combined with the calling proposition.
     * @return  the conjunction of this proposition and q.
     */
    public Proposition and(Proposition q)
    {
        Proposition conjunction = new Proposition();
        conjunction.truthValue = this.truthValue && q.truthValue;
        conjunction.statement = this.statement.concat("^").concat(q.statement);
        return conjunction;
    }

    /**
     * Instance method that combines this proposition with another proposition, q,
     * into a new proposition whose truth value is true if and only if either 
     * proposition is true.
     * 
     * @param   q   the proposition to be combined with the calling proposition.
     * @return  the disjunction of this proposition and q.
     */
    public Proposition or(Proposition q)
    {
        Proposition disjunction = new Proposition();
        disjunction.truthValue = this.truthValue || q.truthValue;
        disjunction.statement = this.statement.concat("v").concat(q.statement);
        return disjunction;
    }

    /**
     * Instance method that combines this proposition with another proposition, q,
     * into a new proposition whose truth value is true if and only if one of the 
     * proposition's truth value is true but not both.
     * 
     * @param   q   the proposition to be combined with the calling proposition.
     * @return  the exclusive disjunction of this proposition and q.
     */
    public Proposition xor(Proposition q)
    {
        Proposition exclusiveOr = new Proposition();
        exclusiveOr.truthValue = this.truthValue ^ q.truthValue;
        exclusiveOr.statement = this.statement.concat("x").concat(q.statement);
        return exclusiveOr;
    }

    /**
     * Instance method that returns a new copy of this proposition with
     * its truth value negated.
     * 
     * @return  the negation of this proposition.
     */
    public void not()
    {
        if(this.truthValue) this.truthValue = false;
        else this.truthValue = true;
    }

    /**
     * Instance method that combines this proposition with another proposition, q,
     * into a new proposition whose truth value captures the statement that
     * if this proposition is true, then q is true.
     * 
     * @param   q   the proposition to be combined with the calling proposition.
     * @return  the implication or conditional of this proposition and q.
     */
    public Proposition implies(Proposition q)
    {
        Proposition conditional = new Proposition();
        if(this.truthValue && !q.truthValue) conditional.truthValue = false;
        else conditional.truthValue = true;
        conditional.statement = this.statement.concat("->").concat(q.statement);
        return conditional;
    }

    /**
     * Instance method that combines this proposition with another proposition, q,
     * into a new proposition whose truth value captures the statement that
     * this proposition is true if, and only if, q is true.
     * 
     * @param   q   the proposition to be combined with the calling proposition.
     * @return  the bi-implication or biconditional of this proposition and q.
     */
    public Proposition iff(Proposition q)
    {
        Proposition biconditional = new Proposition();
        if(this.truthValue == q.truthValue) biconditional.truthValue = true;
        else biconditional.truthValue = false;
        biconditional.statement = this.statement.concat("<=>").concat(q.statement);
        return biconditional;
    }
}
/*
 * @author
 * Iraku Harry, School of Computing and Information Technology, College of Computing and Information Sciences,
 * Makerere University
 * iraqooh@gmail.com
 */
package harry;

import java.util.regex.*;

/*
 * The Logic class handles tasks related to propositional and predicate logic
 */
public class Logic
{
    /**
     * Checks if the string statement is a proposition i.e. whether it is
     * true or false.
     * 
     * @param   statement   the propositional statement to be tested.
     * @return  true if the statement is a proposition, false otherwise.
     */
    public static boolean isProposition(String statement)
    {
        String[] regExpressions = {
            ".* is .*", ".* are .*", ".*\\d ?=.*", ".* .*s ?.*+"
        };

        Pattern p = null;
        Matcher m = null;

        for(String regex : regExpressions)
        {
            p = Pattern.compile(regex);
            m = p.matcher(statement);
            if(m.find()) return true;
        }

        return false;
    }
}
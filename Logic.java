/*
 * @author
 * Iraku Harry, School of Computing and Information Technology, College of Computing and Information Sciences,
 * Makerere University
 * iraqooh@gmail.com
 */
package harry;

import java.util.regex.*;
import java.util.Arrays;

/*
 * The Logic class handles tasks related to propositional and predicate logic
 */
public class Logic extends Lang
{
    private String problemStatement;
    private String[] problem;
    public Proposition[] propositions;
    private String[] variables;
    private static final String[] replacements = {"a", "b", "c", "d", "e", "f",
        "g", "h", "i", "j", "k", "l", "m", "n", "o"};
    private int orderOfReplacement;
    public Proposition execution;
    private int frequency;

    public Logic(String proposition)
    {
        this.problemStatement = proposition;
        this.problem = new String[1];
        this.problem[0] = proposition;
        this.propositions = createVariables(proposition);
    }
    
    /**
     * Checks if the string statement is a proposition i.e. whether it is
     * true or false.
     * 
     * @param   statement   the propositional statement to be tested.
     * @return  true    if the statement is a proposition, false otherwise.
     */
    public static boolean isProposition(String statement)
    {
        String[] regExpressions = {
            ".* is .*", ".* are .*", ".*\\d ?=.*", ".* .*s ?.*+"
        };

        Pattern pattern = null;
        Matcher matcher = null;

        for(String regex : regExpressions)
        {
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(statement);
            if(matcher.find()) return true;
        }

        return false;
    }

    public void parseStatement(String[] statement)
    {
        if(statement[0].contains("("))
        {
            int numberOfBrackets = numberOfOccurrences(statement[0], '(', false);
            for(int i=0; i<numberOfBrackets; i++)
            {
                String bracketExpression = statement[0].substring(statement[0].lastIndexOf("("));
                bracketExpression = bracketExpression.substring(0, bracketExpression.indexOf(")") + 1);
                String expression = bracketExpression.substring(1, bracketExpression.indexOf(")"));
                String[] expressionReference = {expression};
                parseExpression(expressionReference);
                statement[0] = statement[0].replace(bracketExpression, replacements[orderOfReplacement]);
                orderOfReplacement++;
            }
        }
        parseExpression(statement);
    }

    public void parseExpression(String[] expression)
    {
        if(expression[0].contains("~"))
        {
            if(expression[0].length() > 2)
            {
                frequency = Lang.numberOfOccurrences(expression[0], '~', false);
                for(int i=0; i<frequency; i++)
                {
                    String negation = expression[0].substring(expression[0].indexOf("~"));
                    negation = negation.substring(0, 2);
                    Logic.getThisProposition(negation.substring(1), propositions).not();
                    Logic.getThisProposition(negation.substring(1), 
                        propositions).setStatement(replacements[orderOfReplacement]);
                    expression[0] = expression[0].replace(negation, replacements[orderOfReplacement]);
                    orderOfReplacement++;
                }
            }
            else
            {
                Logic.getThisProposition(expression[0].substring(1), propositions).not();;
                execution = Logic.getThisProposition(expression[0].substring(1), propositions);
            }
        }

        if(expression[0].contains("^"))
        {
            frequency = Lang.numberOfOccurrences(expression[0], '^', false);
            for(int i=0; i<frequency; i++)
            {
                String conjunction = expression[0].substring(expression[0].indexOf("^") - 1);
                conjunction = conjunction.substring(0, 3);
                Proposition preconjunct = extraction(conjunction.substring(0, 1));
                Proposition postconjunct = extraction(conjunction.substring(2));
                execution = preconjunct.and(postconjunct);
                execution.setStatement(replacements[orderOfReplacement]);
                expression[0] = expression[0].replace(conjunction, replacements[orderOfReplacement]);
                orderOfReplacement++;
            }
        }

        if(expression[0].contains("v"))
        {
            frequency = Lang.numberOfOccurrences(expression[0], 'v', false);
            for(int i=0; i<frequency; i++)
            {
                String disjunction = expression[0].substring(expression[0].indexOf("v") - 1);
                disjunction = disjunction.substring(0, 3);
                Proposition predisjunct = extraction(disjunction.substring(0, 1));
                Proposition postdisjunct = extraction(disjunction.substring(2));
                execution = predisjunct.or(postdisjunct);
                execution.setStatement(replacements[orderOfReplacement]);
                expression[0] = expression[0].replace(disjunction, replacements[orderOfReplacement]);
                orderOfReplacement++;
            }
        }

        if(expression[0].contains("x"))
        {
            frequency = Lang.numberOfOccurrences(expression[0], 'x', false);
            for(int i=0; i<frequency; i++)
            {
                String disjunction = expression[0].substring(expression[0].indexOf("x") - 1);
                disjunction = disjunction.substring(0, 3);
                Proposition predisjunct = extraction(disjunction.substring(0, 1));
                Proposition postdisjunct = extraction(disjunction.substring(2));
                execution = predisjunct.xor(postdisjunct);
                execution.setStatement(replacements[orderOfReplacement]);
                expression[0] = expression[0].replace(disjunction, replacements[orderOfReplacement]);
                orderOfReplacement++;
            }
        }

        if(expression[0].contains("->"))
        {
            frequency = Lang.numberOfOccurrences(expression[0], "->");
            for(int i=0; i<frequency; i++)
            {
                String implication = expression[0].substring(expression[0].indexOf("->") - 1);
                implication = implication.substring(0, 4);
                Proposition hypothesis = extraction(implication.substring(0, 1));
                Proposition conclusion = extraction(implication.substring(3));
                execution = hypothesis.implies(conclusion);
                execution.setStatement(replacements[orderOfReplacement]);
                expression[0] = expression[0].replace(implication, replacements[orderOfReplacement]);
                orderOfReplacement++;
            }
        }

        if(expression[0].contains("<=>"))
        {
            frequency = Lang.numberOfOccurrences(expression[0], "<=>");
            for(int i=0; i<frequency; i++)
            {
                String implication = expression[0].substring(expression[0].indexOf("<=>") - 1);
                implication = implication.substring(0, 5);
                Proposition hypothesis = extraction(implication.substring(0, 1));
                Proposition conclusion = extraction(implication.substring(4));
                execution = hypothesis.iff(conclusion);
                execution.setStatement(replacements[orderOfReplacement]);
                expression[0] = expression[0].replace(implication, replacements[orderOfReplacement]);
                orderOfReplacement++;
            }
        }
    }

    public Proposition[] createVariables(String statement)
    {
        int count = 0;
        this.variables = new String[statement.length()];
        for(int i=0; i<statement.length(); i++)
        {
            if(Character.isAlphabetic(statement.charAt(i)) && statement.charAt(i) != 'v'
                && statement.charAt(i) != 'x')
            {
                this.variables[count] = Character.toString(statement.charAt(i));
                count++;
            }
        }
        variables = Arrays.copyOfRange(variables, 0, count);
        Proposition[] props = new Proposition[count];
        for(int i=0; i<count; i++)
        {
            props[i] = new Proposition(variables[i]);
        }
        return props;
    }
    
    public boolean find(Proposition proposition)
    {
        if(proposition != null)
        {
            for(int i=0; i<propositions.length; i++)
            {
                if(propositions[i].getStatement().equals(proposition.getStatement())) return true;
            }
        }
        else return false;
        return false;
    }

    public Proposition extraction(String expression)
    {
        if(find(Logic.getThisProposition(expression, propositions)))
            return Logic.getThisProposition(expression, propositions);
        else return execution;
    }

    public static Proposition getThisProposition(String variable, Proposition[] propositionArray)
    {
        Proposition output = null;
        for(int i=0; i<propositionArray.length; i++)
        {
            if(propositionArray[i].getStatement().equals(variable))
            {
                output = propositionArray[i];
                break;
            }
        }
        return output;
    }

    public void drawTruthTable()
    {
        for(int k=0; k<propositions.length; k++)
        {
            System.out.print(propositions[k].getStatement() + "\t");
        }
        System.out.println(problem[0]);
        int[] progress = new int[propositions.length];
        for(int j=0; j < Math.pow(2, propositions.length); j++)
        {
            for(int i=0; i<propositions.length; i++)
            {
                int progressCap = (int)(Math.pow(2, propositions.length)/Math.pow(2, i+1));
                if(progress[i] < progressCap)
                {
                    propositions[i].setValue(false);
                    progress[i]++;
                }
                else
                {
                    propositions[i].setValue(true);
                    progress[i]++;
                }
                System.out.print(propositions[i].getValue() + "\t");
                if(progress[i] >= progressCap * 2) progress[i] = 0;
            }
            if(problem[0].contains("("))
            {
                int numberOfBrackets = numberOfOccurrences(problem[0], '(', false);
                for(int i=0; i<numberOfBrackets; i++)
                {
                    String bracketExpression = problem[0].substring(problem[0].lastIndexOf("("));
                    bracketExpression = bracketExpression.substring(0, bracketExpression.indexOf(")") + 1);
                    String expression = bracketExpression.substring(1, bracketExpression.indexOf(")"));
                    String[] expressionReference = {expression};
                    parseExpression(expressionReference);
                    problem[0] = problem[0].replace(bracketExpression, replacements[orderOfReplacement]);
                    orderOfReplacement++;
                }
            }
            parseExpression(problem);
            System.out.println(this.execution.getValue());
            this.problem[0] = this.problemStatement;
            for(int i=0; i<this.propositions.length; i++)
            {
                this.propositions[i].setStatement(this.variables[i]);
            }
        }
    }
}
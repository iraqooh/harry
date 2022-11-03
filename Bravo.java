package harry;

import java.util.Arrays;

public class Bravo extends Lang
{
    private Proposition[] propositions;
    private String[] variables;
    private static String[] replacements = {"a", "b", "c", "d", "e", "f",
        "g", "h", "i", "j", "k", "l", "m", "n", "o"};
    private int orderOfReplacement;
    private static final boolean[] VALUES = {false, true};
    private Proposition execution;
    private int frequency;

    public static void main(String[] args)
    {
        String s = "(pv(~q->r))^(~sx(t<=>u))";
        Bravo b = new Bravo();
        b.propositions = b.createVariables(s);
        String[] t = {s};
        prints(t[0]);
        b.parseStatement(t);
        prints(t[0]);
        prints(b.execution.getValue());
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
                execution = predisjunct.iff(postdisjunct);
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
}
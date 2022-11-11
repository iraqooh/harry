/*
 * @author
 * Iraku Harry, School of Computing and Information Technology, College of Computing and Information Sciences,
 * Makerere University
 * iraqooh@gmail.com
 */
package harry;

import java.util.Arrays;
import java.util.regex.*;
import java.lang.IllegalArgumentException;

/*
 * The Mathematics class contains functions necessary for facilitating mathematical 
 * operations for software development
 */
class Mathematics extends Lang
{
    /*
     * The multiples() method takes an Integer number object x and returns an Integer object array of 
     * the multiples of x
     */
    public static Integer[] factors(Integer x)
    {
        Integer[] output = new Integer[x];
        int index = 0;
        for(int i=1; i<=x; i++)
        {
            if(x%i == 0)
            {
                output[index] = i;
                index++;
            }
        }
        return Arrays.copyOf(output, index);
    }

    /**
     * The factorsCount takes an integer num and returns the number of integers
     * that divide num evenly or exactly.
     * 
     * @param   num   the integer whose number of divisors is to be determined.
     * @return  the number of divisors or factors of num.
     */
    public static int factorsCount(int num)
    {
        int count = 0;
        for(int i = 1; i <= num; i++)
        {
            if(num%i == 0) count++;
        }
        return count;
    }

    public static boolean isPrime(int num)
    {
        if(factorsCount(num) == 2) return true;
        else return false;
    }

    /*
     * The commonMultiples() method takes an Integer number object x and returns an Integer object array of 
     * the common multiples of x
     */
    public static Integer[] commonMultiples(Integer x, Integer y)
    {
        int index = 0, limit = (x < y) ? x : y;
        Integer[] output = new Integer[limit];
        for(int i=1; i<=limit; i++)
        {
            if(x%i == 0 && y%i == 0)
            {
                output[index] = i;
                index++;
            } 
        }
        return Arrays.copyOf(output, index);
    }
    
    /**
     * lcm returns the lowest common multiple of two integer numbers
     * 
     * @param   num1   the first integer.
     * @param   num2   the second integer.
     */
    public static int lcm(int num1, int num2) 
    {
        int max = (num1 > num2) ? num1 : num2;
        int min = (num1 < num2) ? num1 : num2;
        for(int i=1; i<=(num1*num2); i++)
        {
            for(int j=1; j<=(num1*num2); j++) if((min * j) == (max * i)) return (max * i);
        }
        return 0;
    }

    /*
     * Differentiation of expr, in terms of character x, with respect to x
     */
    public static String differentiate(String input)
    {
        String regex = "-?\\d*\\.?\\d*x?\\^?-?\\d*";
        String expr = Lang.shrink(input);
        String output = new String();
        if(Pattern.matches(regex, expr))
        {
            if(!expr.contains("x")) output = "0";
            else if(expr.contains("x"))
            {
                if(!expr.contains("^"))
                {
                    if(expr.length() == 1) output = "1";
                    else
                    {
                        output = expr.substring(0, expr.indexOf('x'));
                    }
                }
                else
                {
                    if(expr.indexOf('x') == 0)
                    {
                        if(expr.charAt(expr.indexOf('^') + 1) == '1') output = "1";
                    }
                    else
                    {
                        if(expr.charAt(expr.indexOf('^') + 1) == '1') output = expr.substring(0, expr.indexOf('x'));
                        else
                        {
                            double coefficient = Double.parseDouble(expr.substring(0, expr.indexOf('x')));
                            int power = Integer.parseInt(expr.substring(expr.indexOf('^') + 1));
                            if(expr.substring(0, expr.indexOf('x')).contains("."))
                            {
                                output = String.valueOf(coefficient * power) + "x^" + String.valueOf(power - 1);
                            }
                            else output = String.valueOf(Math.round(coefficient * power)) + "x^" + String.valueOf(power - 1); 
                        }
                    }
                }
            }
            
        }
        return output;
    }
    /*
     * The equationTerms() splits a String object s representing an equation of operands and operators and returns a
     * String array of the operands and operators
     */
    public static String[] equationTerms(String in)
    {
        String[] temp = new String[in.length()+1];
        in = Lang.shrink(in);
        int count = 0;
        String xyz = in;
        for(int i=0; i<in.length(); i++)
        {
            if(in.charAt(i)=='(')
            {
                temp[count] = "(";
                count++;
                xyz = xyz.substring(1);
            }
            else if(in.charAt(i) == ')')
            {
                temp[count] = xyz.substring(0, xyz.indexOf(')'));
                    count++;
                    xyz = xyz.substring(xyz.indexOf(')'));
            }
            else if(in.charAt(i)=='/')
            {
                temp[count] = xyz.substring(0, xyz.indexOf('/'));
                count++;
                temp[count] = "/";
                count++;
                xyz = in.substring(in.indexOf('/') + 1);
            }
            else if(in.charAt(i)=='*')
            {
                temp[count] = xyz.substring(0, xyz.indexOf('*'));
                count++;
                temp[count] = "*";
                count++;
                xyz = xyz.substring(xyz.indexOf('*') + 1);
            }
            else if(in.charAt(i)=='+')
            {
                temp[count] = xyz.substring(0, xyz.indexOf('+'));
                count++;
                temp[count] = "+";
                count++;
                xyz = xyz.substring(xyz.indexOf('+') + 1);
            }
            else if(in.charAt(i)=='-')
            {
                if(xyz.substring(0, xyz.indexOf('-')).equals("")) temp[count] = "0";
                else temp[count] = xyz.substring(0, xyz.indexOf('-'));
                count++;
                temp[count] = "-";
                count++;
                xyz = xyz.substring(xyz.indexOf('-') + 1);
            }
            else if(in.charAt(i)=='^')
            {
                temp[count] = xyz.substring(0, xyz.indexOf('^'));
                count++;
                temp[count] = "^";
                count++;
                xyz = xyz.substring(xyz.indexOf('^') + 1);
            }
        }
        temp[count] = xyz;
        return Arrays.copyOfRange(temp, 0, count+1);
    }
    /*
     * Numerical methods
     */
    public static String[] bisection(String function)
    {
        String[] output = {};
        return output;
    }
    public static Double[] gaussian(Double[] input)
    {
        Double[] output = new Double[2];
        if(input.length == 6)
        {
            //ax + by = c, dx + ey = f
            //y = f/e - dx/e
            //x= [c/a - b/a*(f/e - d/e)]/(1 - bd/ae)
            output[0] = (input[2]/input[0] - (input[1]/input[0])*(input[5]/input[4]-input[3]/input[4]))/
                (1-input[1]*input[3]/(input[0]*input[4]));
            output[1] = (input[5]/input[4]) - (input[3]*output[0]/input[4]);
        }
        return output;
    }

    
}

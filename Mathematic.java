/*
 * @author
 * Iraku Harry, School of Computing and Information Technology, College of Computing and Information Sciences,
 * Makerere University
 * iraqooh@gmail.com
 */
package harry;

import java.util.Arrays;

/*
 * The Mathematics class contains functions necessary for facilitating mathematical 
 * operations for software development
 */
class Mathematics
{
    /*
     * The multiples() method takes an Integer number object x and returns an Integer object array of 
     * the multiples of x
     */
    public static Integer[] multiples(Integer x)
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
    public int factorsCount(int num)
    {
        int count = 0;
        for(int i = 1; i <= num; i++)
        {
            if(num%i == 0) count++;
        }
        return count;
    }
    
    /**
     * The isPrime method checks whether the integer parameter num is a prime number.
     * 
     * @param   num   the integer to be tested.
     * @return  true if num is a prime number and false otherwise.
     */
    public boolean isPrime(int num)
    {
        return  (factorsCount(number) == 2) ? true : false;
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
}

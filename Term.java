import java.util.Scanner;
/**
 * This class makes Term objects that are put in a linkedlist from the Polynomial class.
 *
 * @author Michael R. Boykin
 * @version 2017-11-13
 */
public class Term
{
    public int coefficient, exponent;
    public Term next;
    private Scanner termInput;

    /**
     * Constructor for objects of class Term.  This constructor passes off the 
     *   construction to a method with the same string that it received.
     */
    public Term (String createTermString)
    {
        parseStringToTerm(createTermString);
        
    }//END OF CONSTRUCTOR: Term

    /**
     * This method separates the A (coefficient) and n (exponent) parts out of a passed 
     *   string in the form of Ax^n, x^n (A=1), Ax (n=1) & x (A&n=1).  It then sets the
     *   appropriate int values in this term.
     *
     * @param  stringToTerms  string passed in to parse out the integers from.
     * @return None.
     */
    private void parseStringToTerm (String stringToTerms)
    {
        // initialise instance variables
        int xCaretPos = stringToTerms.indexOf("x");
        int stringEnd = stringToTerms.length();
        
        //This IF-ELSE statement sets the coefficient of our term.
        if( xCaretPos == -1) {//If "x" is not in string, then term is a constant.
            //x is effectively x^0 (which = 1) and therefore is a constant.
            coefficient = Integer.parseInt(stringToTerms);
            exponent = 0;
            return;
            //Going further down these statements with a constant would cause an NPE.
            
        } else {
            if(xCaretPos == 0){//x is part of term; (no A), check if is "x" or "x^n"
                coefficient = 1;//coefficient is understood as 1 with "x" or "x^n".
                
            } else {//There is an x variable and it's written as Ax or Ax^#.
                coefficient = Integer.parseInt(stringToTerms.substring(0, xCaretPos));
                //System.out.println (coefficient);
            }
        }//END IF-ELSE BLOCK: set term coefficient
        
        //This IF-ELSE statement sets the exponent of our term.
        if( (xCaretPos + 1) == stringEnd) {
            //if the term's x variable is "x" vs. "x^#" then it is effectively x^1.
            exponent = 1;
            
        } else {//if not x^1 then find the integer after x^ to get the exponent.
            exponent = Integer.parseInt(stringToTerms.substring(
            		            (xCaretPos+2), stringEnd));
            //System.out.println (exponent);
        }//END IF-ELSE BLOCK: set term exponent
        
    }//END OF METHOD: parseStringToTerm
    
    /**
     * This method separates the A (coefficient) and n (exponent) parts out of the Term
     *   and returns it Ax^n, x^n (A=1), Ax (n=1) & x (A&n=1).
     *
     * @param  None.
     * @return the string representation of this term.
     * @overrides toString()
     */
    public String toString ()
    {
        String returnString = "";//instantiates and declares String
        
        //Checks whether or not A is to be returned as part of a string return.
        if(coefficient == 1) {
            if(exponent == 0) {
                //If coefficient = 1 & exponent = 0, we print 1 as a constant.
                returnString += "1";
            }
            /** if exponent is not a 0 then returnString does not get a mutator 
             *   statement.  Therefore returnString still = "" at this point.
             */
             
        } else {//if integer coefficient != 1, returnString changed to coefficient.
            returnString += Integer.toString(coefficient);
            
        }//END IF-ELSE BLOCK: Check coefficient
        
        if(exponent != 0) {//if exponent = 0, the term is a constant (no x in term)
            returnString +="x";
        }
        
        if(exponent > 1) {//if exponent = 1, returnString doesn't get ^ placed init.
            returnString += "^" + Integer.toString(exponent);
        }
        
        return returnString;
    }
}

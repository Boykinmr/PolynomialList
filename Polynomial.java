import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * This class builds the Polynomial list out of polynomial Terms that it makes from a 
 *   string passed in from the driver program.  
 * It does not use a tail reference to put the terms because the placeTerm method was 
 *   implemented to serve double duty by placing terms in order regardless of the order 
 *   received.  Even though we can assume the user will type the polynomials correctly,
 *   adding polynomials together could result in terms of polynomials with different 
 *   orders being added out of order if only assuming tail addition.
 * It does not use doubly-linked Terms because I see no benefit of going from one term 
 *   to the other, when algebra goes in order from highest to lowest order anyway.
 *
 * @author Michael R. Boykin
 * @version 2017-11-13
 */
public class Polynomial
{
    //Polynomial terms linked list variables.
    private Term head;//Every linked list needs one.
    private Term currentTerm, previousTerm;//These terms will help with list iteration.
    
    //Polynomial File and User operations variables.
    private Scanner userInput, readFile;
    public  String toReportString, buildString;
    private File outputFile;
    private BufferedWriter writer;
    
    /**
     * Constructor for objects of class Polynomial.  It checks first if the Polynomial 
     *   class is receiving a call from main, which would be signified by a [u], [f] or 
     *   [e] input, or another Polynomial object which would be in the form of:
     *   Ax^(n) + Ax^(n-1) + ... + Ax + constant.
     *   
     *   Assumes user doesn't enter polynomials with same exponent orders like: 
     *    12x^2 + 6x + 12 + 13x^2.  This should be handled by the add method in driver.
     *   Also, some term-orders may be skipped over, and will not be stored i.e.:
     *     25x^3 + 0x^2 + 6x + 12 will be input, printed and stored as 25x^3 + 6x + 12.
     *   
     * @param  stringToTerms could be a flag passed from main to the constructor or the
     *   String representation of a polynomial input from user or file with the form of:
     *   Ax^(n) + Ax^(n-1) + ... Ax + constant.
     */
    public Polynomial (String stringToTerms)
    {
        userInput = new Scanner(System.in);
        toReportString = "";
        
        //Check if this a flag sent to the constructor from main.
        if( stringToTerms.equals("u")){getUserInput(); return;}
        if( stringToTerms.equals("f")){getFileInput(); return;}
        if( stringToTerms.equals("e")){return;}
        
        //We got to here, so we were called by a Polynomial class constructor.
        buildString = stringToTerms;
        
        Scanner termInput = new Scanner(stringToTerms);
        while(termInput.hasNext()) {
            String termString = termInput.next();
            Term newTerm = null;
            
            if( !termString.equals("+") ) {//We will not make terms out of "+"s.
                //No operations besides "+" in the specs for this program.
                newTerm = new Term(termString);
                placeTerm(newTerm);
                
            }
            
        }
    }//END OF CONSTRUCTOR: Polynomial (String stringToTerms)
    
    /**
     * This method adds up 2 Polynomials sent from the calling class method, generates a
     *   new Polynomial object and returns it.  It concatenates the toString() values of
     *   poly1 & poly2 and then calls the Polynomial constructor to make poly3.
     *   
     * @param  poly1, poly2  Polynomials sent in to be added together.
     * @return the Polynomial result of addition of poly1 & poly2.
     */
    public void addPolynomials(Polynomial poly1, Polynomial poly2)
    {
        Polynomial poly3 = new Polynomial(poly1.toString() + " + " + poly2.toString());
        System.out.println ("Addition equals: " + poly3.toString());
            
        toReportString += ("add"                 +System.getProperty("line.separator")+
            "Poly1 input:   " +poly1.toString() +System.getProperty("line.separator")+
            "Poly2 input:   " +poly2.toString() +System.getProperty("line.separator")+
            "Result Output: " +poly3.toString()  +System.getProperty("line.separator")+
            System.getProperty("line.separator"));
            
    }//END OF METHOD: addPolynomials(poly1, poly2)
    
    /**
     * This method evaluates a Polynomial by substituting the xValue for x.  This method
     *   uses loops to simulate the power function, keeping the result an integer.
     *  
     *  @param  poly1  Polynomial sent in to be evaluated.
     *  @param  xValue int sent in to be substituted for every term's x variable.
     *  @return None.
     */
    public void evaluatePolynomials(Polynomial poly1, int xValue)
    {
        //
        int returnTotal = 0;
        currentTerm = poly1.head;
        
        while ( currentTerm != null ) {
            //I don't know if the specs allow doubles so I am using a loop.
            
            if ( currentTerm.exponent == 0 ) {//term is a constant.
                returnTotal += currentTerm.coefficient;
            } else {
                int tempProd = xValue;
                for(int loopNum=2; loopNum <= currentTerm.exponent; loopNum++) {
                    //With exponent = 1, this loop won't run, so tempProd is at xValue
                    tempProd *= xValue;
                }
                returnTotal += currentTerm.coefficient * tempProd;
            }//END IF-ELSE BLOCK: power loop
            
            currentTerm = currentTerm.next;
        }//END OF WHILE LOOP: for 
        System.out.println ("Evaluation Equals: " + returnTotal);
        
        toReportString += ("evaluate"           +System.getProperty("line.separator")+
            "Poly1:  " +poly1.toString()        +System.getProperty("line.separator")+
            "x =     " +Integer.toString(xValue)+System.getProperty("line.separator")+
            "Result: " +Integer.toString(returnTotal) + 
           System.getProperty("line.separator")+System.getProperty("line.separator"));
        
    }//END OF METHOD: evaluatePolynomials
    
    /**
     * This method solicites a file name from the user and then opens it for reading.  
     *   It then performs functions as specified in the specs.
     *   
     * @param  None.
     * @return None.
     */
    private void getFileInput()
    {
        //
        System.out.println ("Please type the filename you wish to read from. ");
        String fileName = userInput.next();
        
        //
        try{
            readFile = new Scanner(new File(fileName));
        }//
        catch (Exception e) {
            System.out.println ("Could not find the requested file in this path.");
            return;
        }
        
        while (readFile.hasNext()){
            //
            String fileLineStr = readFile.nextLine();
            
            if ( fileLineStr.trim().equals("add")) {
                Polynomial poly1 = new Polynomial (readFile.nextLine());
                Polynomial poly2 = new Polynomial (readFile.nextLine());
                addPolynomials(poly1, poly2);
                
                    
            }
            
            if ( fileLineStr.equals("evaluate")) {
                Polynomial poly1 = new Polynomial (readFile.nextLine());
                int xValue = Integer.parseInt(readFile.nextLine());
                
                evaluatePolynomials(poly1, xValue);
                 
                //
            }
            
        }
        
    }//END OF METHOD: getFileInput
    
    /**
     * This method solicites a input from the user with regards to the functions as 
     *   performed and polynomials entered as specified in the specs.
     *   
     * @param  None.
     * @return None.
     */
    private void getUserInput()
    {
        //Program will be operated by a user.
        System.out.println ("This program can: ");
        System.out.println ("1.  Add 2 polynomials to make 1 polynomial");
        System.out.println ("2.  Evaluate a polynomail by choosing a value for x");
        System.out.print ("Which option would you like to do? ");
        int userResponse = userInput.nextInt();
        System.out.println ();
        
        //clear the queue. Return key would be sent from prev. scanner, skipping next.
        userInput.nextLine();
        
        //This text will print for either choice 1 or 2.
        System.out.println ("Type a polynomial in the form Ax^n + ... + constant");
        System.out.print ("Poly1: ");
        
            
        if(userResponse == 1){//Adding polynomials together.
            Polynomial poly1 = new Polynomial(userInput.nextLine());
            System.out.print ("Poly2: ");//This text only prints for option 1 above.
            Polynomial poly2 = new Polynomial(userInput.nextLine());
            
            addPolynomials(poly1, poly2);
            
            
        }
        
        if(userResponse == 2){//Evaluating a polynomials for user-input value of x.
            Polynomial poly1 = new Polynomial(userInput.nextLine());
            System.out.print ("x = ?: ");//This text only prints for option 2 above.
            int xValue = userInput.nextInt();
            
            evaluatePolynomials(poly1, xValue);
            
            
        }
        
    }//END OF METHOD: getUserInput
    
    /**
     * This method inserts new terms to the linked list in exponent order.  It pulls 
     *   extra duty by adding the terms if the list exists as called from 
     *   addPolynomials method.  Coincidentally, it will combine like terms if typed in
     *   one or two polynomials.
     *
     * @param  newTerm  the term that is to be placed in the list.
     * @return None.
     */
    private void placeTerm (Term newTerm)
    {
        //If the list is empty, instantiate the list with newTerm and exit.
        if( head == null) {
            head = newTerm;
            return;
        }//END IF STATEMENT: instantiate an empty list
        
        //Checks if newTerm belongs at the head, so we don't ask X times in while loop.
        if ( newTerm.exponent > head.exponent ) {
            newTerm.next = head;
            head = newTerm;
            return;
        }//END IF STATEMENT: check if newTerm goes at the head.
        
        //If we get to here, then newTerm belongs between head and end.
        currentTerm = head;
        previousTerm = head;
        while ( currentTerm != null ) {
            //First loop will not affect head unless their orders are the same.
            if ( newTerm.exponent > currentTerm.exponent ) {
                previousTerm.next = newTerm;
                newTerm.next = currentTerm;
                return;
            } else {
                if ( newTerm.exponent == currentTerm.exponent ) {
                    //Adding something like "2x^2 and 3x^2".
                    currentTerm.coefficient += newTerm.coefficient;
                    return;
                }
            }//END IF-ELSE BLOCK: Place between head and end.
            
            //If we get to here, then the compared Term's orders weren't equal.
            previousTerm = currentTerm;
            currentTerm = currentTerm.next;
            
        }//END WHILE LOOP: Place between head and end.

        //If we get to here, then newTerm's goes to the end of the list.
        previousTerm.next = newTerm;
        
    }//END OF METHOD: placeTerm
    
    /**
     * This method is called from main to get the Polynomial's report data for writing 
     *   the final report file.
     * 
     * @param  None.
     * @return String report data saved in the Polynomial for writing.
     */
    public String reportData() {
        return toReportString;
    }//END OF METHOD: reportData
    
    /**
     * This method converts the Polynomial object to a text expression by calling the 
     *   overridden toString() methods so the object's text expression can be printed.
     *
     * @param  None.
     * @return the string representation of this term.
     * @overrides toString()
     */
    public String toString ()
    {
        //Run no part of this method if the list is empty.
        if(head == null) {return "";}
        
        String checkString = "";
        currentTerm = head;
        
        while(currentTerm != null) {
            if ( currentTerm.coefficient != 0) {//Don't print terms with 0 coefficient.
                checkString += currentTerm.toString();
                checkString += " + ";
            }
            //Will remove last " + " at end so don't have to check next term in loop.
            currentTerm = currentTerm.next;
        }
        
        //This returns checkString without the extra " + " on the end.
        return checkString.substring(0, checkString.length() - 3);
        
    }//END OF METHOD: toString
    
}

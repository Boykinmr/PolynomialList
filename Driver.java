import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * The Driver class manages file output operations for the polynomial program and allows
 *   the user a method to input data from user or file.  It also allows the user to exit
 *   the program.
 *
 * @author Michael R. Boykin
 * @version 2017-11-13
 */
public class Driver
{
    // instance variables - replace the example below with your own
    public Scanner userInput, readFile;
    public String toReportString;
    public File outputFile;
    public BufferedWriter writer;

    /**
     * Constructor for objects of class Driver
     */
    public Driver()
    {
        // initialise instance variables
        userInput = new Scanner(System.in);
        toReportString = "Michael R. Boykin"  + System.getProperty("line.separator") +
                         "Polynomial Program" + System.getProperty("line.separator") +
                         System.getProperty("line.separator");
        outputFile = new File("output.txt");
        try {
            writer = new BufferedWriter( new FileWriter( outputFile ));
            writer.write (toReportString);
            toReportString = "";
            writer.flush();
            
        }
        catch (Exception e) {
            System.out.println ("Error creating output file.  \n" + 
                "Check that it is not open in another program \n" + 
                "or that you have permissions to write to the path.");
        }
        
    }//END OF CONSTRUCTOR : Driver()
    
    /**
     * This is the main method that we start from.
     */
    public static void main(String args[])
    {
        Driver driver = new Driver();
        String inputFrom;
        
        do {
            inputFrom = driver.getInputMethod();
            Polynomial polyOps = new Polynomial(inputFrom);
            
            driver.toReportString = polyOps.reportData();
            
            try {
               driver.writer.write (driver.toReportString);
            }
            catch (Exception e) {
                //
            }
            
        } while (!inputFrom.equals("e"));
        
        try {
            driver.writer.flush();
            driver.writer.close();
        }
        catch (Exception e) {
            //
        }
    }//END OF MAIN METHOD
    
    /**
     * This method shows a menu with three options telling the program to [e]xit, get 
     *   [u]ser data or [f]ile data.  It waits for the user choice and then returns it.
     * 
     * @param  None.
     * @return String  user entered choice of [u], [f] or [e]
     */
    private String getInputMethod()
    {
        System.out.println ("Polynomial Program");
        System.out.println ("by: Michael R. Boykin");
        System.out.println ();
        System.out.println ("How would you like to enter data? ");
        System.out.println ("   Enter data from a [u]ser");
        System.out.println ("   Enter data from a [f]ile?");
        System.out.println ("   Neither.  I will  [e]xit the program.");
        System.out.print ("Please enter the letter of your choice. ");
        String userResponse = userInput.next().toLowerCase();
        System.out.println ();
        return userResponse;
        
    }//END OF METHOD: getInputMethod
    
}

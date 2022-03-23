import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.Stack;

public class RPNStacker{
    //Our program only works with int numbers (thus, it only computes integer operations)
    //Returns true if the str can be interpreted as a number
    public static boolean isNum(String str) { 
        try {  
            Double.parseDouble(str);  
            return true;
        } catch (NumberFormatException e){  
            return false;  
        }
    }

    private static int currentOp(int P, int Q, String op) throws Exception{
        //Decides which valid operation to use
        switch(op){
            case "+": 
                return (P + Q);
            case "-": 
                return (P - Q);
            case "*":
                return (P * Q);
            case "/": 
                return (P / Q);
            default :
                throw new Exception("Invalid Operation.");
        }
    }

    public static void main(String[] args){
        //First, we set the stack to handle Integers
        Stack<Integer> currentStack = new Stack<Integer>();  

        try {
            //We access and read the relative file
            File myObj = new File("./Calc1.stk");
            Scanner myReader = new Scanner(myObj);
            //We handle each data read individually (by line)
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(isNum(data) && currentStack.size() < 2){
                    currentStack.push(Integer.parseInt(data));
                }
                //The third element is always the "op"
                //If it's a number, throw exception
                else if(isNum(data) && currentStack.size() >= 2){
                    throw new Exception("Data model not recognized.");
                }
                else{
                    int Q = currentStack.pop();
                    int P = currentStack.pop();
                    String op = data;
                    int opResult = currentOp(P, Q, op);
                    //The op result is stored as the first element of the stack
                    currentStack.push(opResult);
                }
            }
            //We stop reading the file
            myReader.close();
            //The Stack has now only one  element (The final result)
            System.out.println(currentStack.pop());
            //The Stack in now empty
        }
        //Exceeption regarding file
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        //Exception regarding operation
        catch (Exception e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
    }
}

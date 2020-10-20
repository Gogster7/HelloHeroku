
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;
import java.lang.Math;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// adds servlet mapping annotation
import javax.servlet.annotation.WebServlet;
@WebServlet( name = "assignment5", urlPatterns = {"/assignment5"} )

class EquationVariables {
	
	private boolean state;
	private String name;
	
	/**
	 * Stores variables with a name and a binary state (1 or 0)
	 */
	public EquationVariables(String theName, boolean theState){
		name = theName;
		state = theState;
	}
	
	// Returns the binary state 
	public boolean getState(){
		return state;
	}
	
	// Returns the name 
	public String getName(){
		return name;
	}

	public void setState(boolean b){
		state = b;	
	}
	
	@Override
	public String toString() {
		return ("Variable: " + this.getName()+ " Value: " + this.getState());
	}
		
}

public class assignment5 extends HttpServlet{
	static enum Data {LOGICALOPERATION};
	
	// Location of servlet.
	static String Domain  = "";
	static String Path    = "";
	static String Servlet = "assignment5";
	
	// Button labels
	static String OperationSubmit = "Submit";
	
	static String Style ="https://www.cs.gmu.edu/~gterziys/public_html/style.css";
	
	/** *****************************************************
	 *  Overrides HttpServlet's doPost().
	 *  Converts the values in the form, performs the operation
	 *  indicated by the submit button, and sends the results
	 *  back to the client.
	********************************************************* */
	@Override
	public void doPost (HttpServletRequest request, HttpServletResponse response)
	   throws ServletException, IOException
	{
		//THINGS NEED TO BE DONE
		//COMPETION OF TRUTH TABLE
		//CHECK FOR VALID INPUT PREDICATE AND PRINT MESSAGE TO USER
		
		
		//get vars
		String logicalOperation = request.getParameter(Data.LOGICALOPERATION.name()); //"A & B"
		
		//Parse it into a structure that separates boolean variables and logical operators
		 ArrayList legalOps = new ArrayList(Arrays.asList("&&", "AND", "&","*", "^", "+", "||", "|", "OR", "V", "~", "NOT", "!", "==", "=", "EQUAL"));
	     ArrayList arrayEq = new ArrayList(Arrays.asList(logicalOperation.split(" "))); //split by space "A & B -> [A,&,B]"
	     ArrayList arrayOps = new ArrayList();
	     
	     ArrayList<EquationVariables> variableArray = new ArrayList<>();
	 	 ArrayList<Object> equationArray = new ArrayList<>();
	 	 String[][] Table = null;
	 	boolean makeTable = true;
	     
	     //loops through the equation and stores all variables in a variable array.
	     for(int i = 0; i< arrayEq.size(); i++){
	    	 if(!(legalOps.contains(arrayEq.get(i)))){
	    		 boolean alreadyExists = false; //Keeps track of duplicate variables
	    		 EquationVariables temp = new EquationVariables((String)arrayEq.get(i),true);
	    		 
	    		//checks for duplicate variables and doesn't add them to the array twice
				for (EquationVariables v : variableArray){
					if (v.getName()==temp.getName()){
						alreadyExists = true;
						temp = v;
					}
				}
				if (!alreadyExists){
					variableArray.add(temp); 
				}
				//stores the variable objects that are created in an equation array as well
				equationArray.add(temp);
	    	 }else {
	    		 // Operators get stored in an equation array
	    		 equationArray.add(arrayEq.get(i));
	    	 }
	     }
	     
	     //Creates an instance of the truth table with the proper parameters
	     if (variableArray.size() > 0){
	    	 Table = TruthTable(variableArray, equationArray);
	    	 // check if Final input has error
	    	 if (Table[Table.length-1][Table.length-1] == "E") {
	    		 makeTable = false;
	    	 }
	     }
		
		//remove nulls from arrayEq
		arrayEq.removeAll(Collections.singleton(null)); //now we have two array lists {A, B} and {&}
		
		//print the predicate they enetered
		//Print a complete truth table for the predicate, including a column with the result for each row
		//for loop for table size look online
		
		// Change Display Feature
	    String[][] temp = Table;
		String t = "TRUE";
		String f = "FALSE";
		changeDisplay(t, f, temp);
		
		//Echo the predicate to the user
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		PrintHead(writer);
 		PrintResponseBody(writer);
		writer.append("<!DOCTYPE html>")
			.append("<html>")
			.append("	<center>You typed: " + logicalOperation + "</center>")
			.append("</html>");
		
		if (makeTable) {
			for (int i = 0; i < Table.length; i++) {
				writer.append("<center>");
				for (int j = 0; j < Table[i].length; j++) {
					writer.append(Table[i][j] + "|");
					//System.out.print();
				}
				writer.append("</center>");
				//System.out.println();
			}
		}
		
						
		
		// print the table
		//printTable(temp);
	  }

	/**
	 * The method that constructs the truth table
	 * It goes through every possible binary combination for the variables, and calls parseEquation()
	 * for each one. If parseEquation returns false, the program stops executing
	 */
	private static String[][] TruthTable(ArrayList<EquationVariables> variables, ArrayList<Object> equation) {
		int width = variables.size();
		int length = (int) Math.pow(2, width);
		String[][] table = new String[length+1][width+2];
		table[0][0] = "Rows";
		
		//prints out the top row of the truth table
		for (int i = 0; i < variables.size(); i++){
			table[0][i+1] = variables.get(i).getName();
			System.out.print(" | " + variables.get(i).getName());
		}
		table[0][width+1] = "Result";
		
		for (int i=1; i<length+1; i++) {
			String value = "";
			String row = i + ".";
			table[i][0] = row;
			for (int j=width-1; j>=0; j--) {
				int v = (i-1)/(int) Math.pow(2, j)%2;
				if (v == 1){
					variables.get(j).setState(true);
					table[i][j+1] = "1";
	            }
				else {
					variables.get(j).setState(false);
					table[i][j+1] = "0";
				}
				//writer.append(row);
			}
			// Calculate current state of equation
			value = calculateBoolean(equation);
			table[i][width+1] = value;		
		}
		return table;
	}
	
	private static String calculateBoolean(ArrayList<Object> equation) {
		ArrayList AND = new ArrayList(Arrays.asList("&&", "AND", "&", "*"));
		ArrayList OR = new ArrayList(Arrays.asList("+", "^", "||", "|", "OR", "V"));
		ArrayList NOT = new ArrayList(Arrays.asList("~", "NOT", "!"));
		ArrayList<Object> temp = new ArrayList<>(equation);
		String result = "E";
		// change boolean values to ints
		for (int j = 0; j < temp.size();j++){
			if (temp.get(j).getClass().equals(EquationVariables.class)){
				temp.set(j, ((EquationVariables)temp.get(j)).getState() ? 1 : 0);
			}
		}
		for (int i = 0; i < temp.size(); i++){
			if (temp.get(i).getClass().equals(String.class)){
				// check is eligible for NOT operation
				if ((NOT.contains(temp.get(i))) && (temp.get(i+1).getClass().equals(Integer.class))){
					invertVal(i+1, temp);
					//System.out.println("NOT check:" + temp);
				}else if ((!(i == 0) && !(i == temp.size()-1)) && (temp.get(i-1).getClass().equals(Integer.class))){
					if ((temp.get(i+1).getClass().equals(Integer.class))){
						if (OR.contains(temp.get(i))){
							orValues(i-1, i+1, temp);
							//System.out.println("OR check:" + temp);
						}else if (AND.contains(temp.get(i))){
							andValues(i-1, i+1, temp);
							//System.out.println("AND check:" + temp);
						}
					}else if (NOT.contains(temp.get(i+1)) && (temp.get(i+2).getClass().equals(Integer.class))){
						invertVal(i+2,temp);
						if (OR.contains(temp.get(i))){
							orValues(i-1, i+2, temp);
						}else if (AND.contains(temp.get(i))){
							andValues(i-1, i+2, temp);
						}
						temp.set(i+1, "");
					}else{
						System.out.println("Invalid String");
						return result;
					}
				}else {
					System.out.println("Invalid String");
					return result;
				}
			//If two integers are next to each other, end the program for an improper equation
			}else if (temp.get(i).getClass().equals(Integer.class) && i < temp.size()-1 && temp.get(i+1).getClass().equals(Integer.class)){
				return result;
			}
		}
		result = temp.get(temp.size()-1).toString();
		return result;
	}
	
	public static void invertVal(int pos, ArrayList<Object> temp){
		if ((Integer)temp.get(pos)==0){
			temp.set(pos, 1);
		}else{
			temp.set(pos, 0);
		}
	}
	
	public static void orValues(int leftPos, int rightPos,  ArrayList<Object> temp){
		if ((Integer)temp.get(leftPos) == 1 || (Integer) temp.get(rightPos) == 1){
			temp.set(rightPos, 1);
		}else{
			temp.set(rightPos, 0);
		}
	}
	
	public static void andValues(int leftPos, int rightPos,  ArrayList<Object> temp){
		if ((Integer) temp.get(leftPos) == 1 && ((Integer) temp.get(rightPos)) == 1){
			temp.set(rightPos, 1);
		}else{
			temp.set(rightPos, 0);
		}
	}
	
	public static void changeDisplay(String t, String f, String[][] table) {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				if (table[i][j].equals("1")) {
					table[i][j] = t;
				}
				else if(table[i][j].equals("0"))
				{ 
					table[i][j] = f;
				}
			}
		}
	}
	
	public static void printTable(String[][] table) {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				System.out.print(table[i][j] + "|");
			}
			System.out.println();
		}
	}



	
	/** *****************************************************
	 *  Overrides HttpServlet's doGet().
	 *  Prints an HTML page with a blank form.
	********************************************************* */
	@Override
	public void doGet (HttpServletRequest request, HttpServletResponse response)
	       throws ServletException, IOException
	{
	   response.setContentType("text/html");
	   PrintWriter out = response.getWriter();
	   PrintHead(out);
	   PrintBody(out);
	   PrintTail(out);
	} // End doGet
	
	/** *****************************************************
	 *  Prints the <head> of the HTML page, no <body>.
	********************************************************* */
	private void PrintHead (PrintWriter out)
	{
	   out.println("<html>");
	   out.println("");
	   out.println("<head>");
	   out.println("<meta charset=\"utf-8\" />");
	   out.println("<title>Logical Predicates</title>");
	   out.println(" <link rel=\"stylesheet\" type=\"text/css\" href=\"" + Style + "\">");
	   out.println("</head>");
	   out.println("");
	} // End PrintHead 

	
	/** *****************************************************
	 *  Prints the <BODY> of the HTML page with the form data
	 *  values from the parameters.
	********************************************************* */
	private void PrintBody (PrintWriter out, String lhs, String rhs, String rslt)
	{
		out.println("<body>");
		out.println("    <h1><center>Predicate Logic Calculator</center></h1>");
		out.println("    <h2><center>Sonal Kumar * Angela Gentile * George Terziysky * SWE-432-001</center>  </h2>");
		out.println("	 <h3><center>Formatting/Syntax Instructions:</center></h3>");
		out.println("    <h4><center>In order to calculate the final value of the logical operation, type in the predicate with the following constraints:</center></h4>");
		out.println("");
		out.println("    <ul>");
		out.println("        <li>The entry should be typed in the format of (TRUE/FALSE)(LOGICAL OPERATOR)(TRUE/FALSE)</li>");
		out.println("        <li>");
		out.println("            The entry can also be an extention of the format described above. There can be multiple True/False statements with logical operations in between them. For example, you can enter");
		out.println("            \"TRUE OR FALSE\",which would give you TRUE, and you could also enter \"FALSE AND(FALSE AND TRUE)\", which would result in False.");
		out.println("        </li>");
		out.println("    </ul>");
		out.println("    <h4><center>Options for supported logical symbols:</center></h4>");
		out.println("    <ul>");
		out.println("        <li>Supported symbols for AND: \"&&\", \"AND\", \"&\", \"^\"</li>");
		out.println("        <li>Supported symbols for OR: \"||\", \"|\", OR, \"V\" </li>");
		out.println("        <li>Supported symbols for NOT: \"~\", \"NOT\", \"!\"</li>");
		out.println("        <li>Supported symbols for EQUAL: \"==\", \"=\", \"EQUAL\"</li>");
		out.println("    </ul>");
		out.println("    <br />    <br />    <br />");
		out.println("");
		out.println("    <form method=\"post\" action=\"\\assignment5\">");
		out.println("        <center>");
		out.println("            <label for=\"logicalOperation\">Enter Logical Operation:</label>");
		out.println("            <input type=\"text\" id=\"logicalOperation\" name=\"LOGICALOPERATION\"><br><br>");
		out.println("            <input type=\"submit\" value=\"Submit\" style=\"background-color: #80ced6\">");
		out.println("        </center>");
		out.println("    </form>");
		out.println("</body>");
		out.println("");
		out.println("");
		
	}
	
	/** *****************************************************
	 *  Overloads PrintBody (out,lhs,rhs,rslt) to print a page
	 *  with blanks in the form fields.
	********************************************************* */
	private void PrintBody (PrintWriter out)
	{
	   PrintBody(out, "", "", "");
	}
	
	/** *****************************************************
	 *  Prints the bottom of the HTML page.
	********************************************************* */
	private void PrintTail (PrintWriter out)
	{
	   out.println("");
	   out.println("</html>");
	} // End PrintTail
	
	
	
	
	private void PrintResponseBody (PrintWriter out)
	{
		out.println("<body>");
		out.println("    <h1><center>Predicate Logic Calculator</center></h1>");
		out.println("    <h2><center>Sonal Kumar * Angela Gentile * George Terziysky * SWE-432-001</center>  </h2>");
		out.println("	 <h3><center>Formatting/Syntax Instructions:</center></h3>");
		out.println("    <h4><center>In order to calculate the final value of the logical operation, type in the predicate with the following constraints:</center></h4>");
		out.println("");
		out.println("    <ul>");
		out.println("        <li>The entry should be typed in the format of (Variable A)(LOGICAL OPERATOR)(Variable B)</li>");
		out.println("        <li>");
		out.println("            The entry can also be an extention of the format described above. There can be multiple True/False statements with logical operations in between them. For example, you can enter");
		out.println("            \"TRUE OR FALSE\",which would give you TRUE, and you could also enter \"FALSE AND(FALSE AND TRUE)\", which would result in False.");
		out.println("        </li>");
		out.println("    </ul>");
		out.println("    <h4><center>Options for supported logical symbols:</center></h4>");
		out.println("    <ul>");
		out.println("        <li>Supported symbols for AND: \"&&\", \"AND\", \"&\", \"^\"</li>");
		out.println("        <li>Supported symbols for OR: \"||\", \"|\", OR, \"V\" </li>");
		out.println("        <li>Supported symbols for NOT: \"~\", \"NOT\", \"!\"</li>");
		out.println("        <li>Supported symbols for EQUAL: \"==\", \"=\", \"EQUAL\"</li>");
		out.println("    </ul>");
		out.println("    <br />    <br />    <br />");
		out.println("");
		out.println("    <form method=\"post\" action=\"\\assignment5\">");
		out.println("        <center>");
		out.println("            <label for=\"logicalOperation\">Enter Logical Operation:</label>");
		out.println("            <input type=\"text\" id=\"logicalOperation\" name=\"LOGICALOPERATION\"><br><br>");
		out.println("            <input type=\"submit\" value=\"Submit\" style=\"background-color: #80ced6\">");
		out.println("        </center>");
		out.println("    </form>");
		
	}
	
	
	private void PrintResponseBodyEnd (PrintWriter out){
		
		out.println("</body>");
		out.println("");
		
	}
	
	
	
	

}

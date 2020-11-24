//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;

import assignment5.Data;

//Import Java Libraries
import java.io.*;
import java.util.*;
import java.lang.*;

public class assignment9Class extends HttpServlet {
	
	   public void processRequest(HttpServletRequest request, HttpServletResponse response)
			      throws ServletException, IOException
			   {
//			      response.setContentType("TEXT/HTML"); // Not needed to respond to ajax

			      //get vars
			      String logicalOperation = request.getParameter("Expression"); //"A & B"
			      String displaySelection = request.getParameter("display"); //"TRUE/FALSE"
			      ArrayList displayOptions = new ArrayList();
			      if (displaySelection != null ) {
			    	  displayOptions = new ArrayList(Arrays.asList(displaySelection.split("/"))); //split by /
			      }
			      //Parse it into a structure that separates boolean variables and logical operators
			      ArrayList legalOps = new ArrayList(Arrays.asList("&&", "AND", "&","*", "^", "+", "||", "|", "OR", "V", "~", "NOT", "!", "==", "=", "EQUAL"));
			      ArrayList arrayEq = new ArrayList(Arrays.asList(logicalOperation.split(" "))); //split by space "A & B -> [A,&,B]"
			      ArrayList arrayOps = new ArrayList();
			    
			      ArrayList<EquationVars> variableArray = new ArrayList<>();
			      ArrayList<Object> equationArray = new ArrayList<>();
			      String[][] Table = null;
			      boolean makeTable = true;
			     
			      //loops through the equation and stores all variables in a variable array.
			      for(int i = 0; i< arrayEq.size(); i++){
			    	  if(!(legalOps.contains(arrayEq.get(i)))){
			    		  boolean alreadyExists = false; //Keeps track of duplicate variables
			    		  EquationVars temp = new EquationVars((String)arrayEq.get(i),true);
			    		 
			    		//checks for duplicate variables and doesn't add them to the array twice
						for (EquationVars v : variableArray){
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
			    	  if (Table[Table.length - 1][Table[Table.length-1].length - 1] == "E") {
			    		  makeTable = false;makeTable = false;
			    	  }
			    	  if (logicalOperation.length() <= 1 || logicalOperation == null) {
			    		  makeTable = false;
			    	  }
			      }
			      //remove nulls from arrayEq
			      arrayEq.removeAll(Collections.singleton(null)); //now we have two array lists {A, B} and {&}
					
			      //print the predicate they entered
			      //Print a complete truth table for the predicate, including a column with the result for each row
			      //for loop for table size look online
					
			      // Change Display Feature
			      String t = "1"; // Default setting
			      String f = "0";
			      String[][] temp = Table;
				  if (displayOptions.size() > 0) {
					  t = (String)displayOptions.get(0);
					  f = (String)displayOptions.get(1);
				  }
				  
				  
				  //Echo the predicate as a table to the user
				  PrintWriter writer = response.getWriter();
				  //PrintHead(writer);
				  //PrintResponseBody(writer);
			      
				  writer.append("	<center>Display selected: " + displaySelection + "</center>")
						.append("	<center>You typed: " + logicalOperation + "</center>");
				 

				  //writer.print(displaySelection);
			      writer.close();
				  // Print Table
//				  if (makeTable) {
//					  changeDisplay(t, f, temp);
//					  writer.append("<center>");
//					  writer.append("<table border=2 cellpadding=0 cellspacing=0>");
//					  for (int i = 0; i < Table.length; i++) {	
//						  writer.append("<tr>");
//						  for (int j = 0; j < Table[i].length; j++) {
//							  writer.append("<td>" + Table[i][j] + "</td>");
//						  }
//						  writer.append("</tr>");
//					  }
//					  writer.append("</table> </center>");
//				  }
//				  else{
//					  writer.append("<center> INVALID EQUATION!!! </center>");
//				  }	
			   }
	

			    // Method doPost - just calls processRequest
			    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			            throws ServletException, IOException
			    {
			       processRequest(request, response);
			    }

			    // Method doGet - just calls processRequest
			    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			            throws ServletException, IOException
			    {
			       processRequest(request, response);
			    }
			    
			    //public void createRequest() {
			
				/**
				 * The method that constructs the truth table
				 * It goes through every possible binary combination for the variables, and calls parseEquation()
				 * for each one. If parseEquation returns false, the program stops executing
				 */
				private static String[][] TruthTable(ArrayList<EquationVars> variables, ArrayList<Object> equation) {
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
						if (temp.get(j).getClass().equals(EquationVars.class)){
							temp.set(j, ((EquationVars)temp.get(j)).getState() ? 1 : 0);
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
}
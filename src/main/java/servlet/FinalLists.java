/** *****************************************************************
FinalLists.java   servlet
Create and Sort List

    @author George Terziysky
    December 2020, Final Exam
********************************************************************* */
// Import Java Libraries
import java.io.*;
import java.util.*;

//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.annotation.WebServlet;
@WebServlet( name = "FinalLists", urlPatterns = {"/FinalLists"} )

public class FinalLists extends HttpServlet{
	//Location of servlet
	static String Domain  = "gterziyshello.herokuapp.com";
	static String Path    = "";
	static String Servlet = "FinalLists";
	
	//Button labels
	static String Reset = "Reset";
	static String Sort = "SORT";
	// Style
	static String Style ="https://www.cs.gmu.edu/~gterziys/public_html/style.css";
	
	@Override
	public void doPost (HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		String result = "";
		ArrayList<String> list = new ArrayList<String>();
		String entered = request.getParameter("listEnter");
		String operation = request.getParameter("Operation");
		String direction = request.getParameter("orderOption");
		String sortOrder = request.getParameter("sorter");
		
		// create array from entered string
		Scanner sc = new Scanner(entered);
		while(sc.hasNext()) {
			String n = sc.next();
			// Unique: Check for duplicates	
			if (list.contains(n)) {
				continue;
			}
			list.add(n);
		}
		// Sorting Order Selection
		switch(sortOrder) {
		   case "String Order" :
			   Collections.sort(list);
			   break; 
		   case "Length" :
			  Collections.sort(list, new Comparator<String>() {
				   @Override
				   public int compare(String o1, String o2) {
				       return o2.length() - o1.length();
				   }
			  });
			  Collections.reverse(list);
		      break; 
		   case "Vowels" :
			  sortVowels(list);
		      break;
		   case "Consonants" :
			  sortConsonants(list);
		      break; 
		   default :
			   result = toList(list);   
		}
		// Ascending | Descending 
		if (direction.equals("Descending")) {
			Collections.reverse(list);
			//request.getElementById("Descending").checked = true;
		}
		// switch to string
		result = toList(list); 
		// Reset
		if (operation.contentEquals(Reset)) {
			entered = "";
			result = "";
		}
		// Return Response
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		PrintHead(out);
		PrintBody(out, entered, result);
		PrintTail(out);
	}
	
	public class pair
	{
	    int first;
	    String second;
	     
	    pair(int first,String second)
	    {
	        this.first = first;
	        this.second = second;
	    }
	}
	
	// Sort list related to number of vowels in a String
	public ArrayList<String> sortVowels(ArrayList<String> list) {
		// Vector to store the number of vowels per String
		ArrayList<pair> vp = new ArrayList<>();
	    ArrayList<String> temp = new ArrayList<>();
	    for(int i = 0; i < list.size(); i++){
	    	String str = list.get(i);
	    	vp.add(new pair(countVowels(str),str));
	    }
	    Collections.sort(vp, (a, b) -> a.first - b.first);
	    for(int i = 0; i < vp.size(); i++) {
	        temp.add(vp.get(i).second);
	    }
		return list;
	}
	
	// Sort list related to number of consonants in a String 
	public ArrayList<String> sortConsonants(ArrayList<String> list) {
		// Vector to store the number of consonants per String
	    ArrayList<pair> cp = new ArrayList<>();
	    ArrayList<String> temp = new ArrayList<>();
	    for(int i = 0; i < list.size(); i++){
	    	String str = list.get(i);
	    	cp.add(new pair((countConsonants(str)),str));
	    }
	    Collections.sort(cp, (a, b) -> a.first - b.first);
	    for(int i = 0; i < cp.size(); i++) {
	        temp.add(cp.get(i).second);
	    }
		return list;
	}
	
	// Function to check the Vowel
	public boolean isVowel(char ch)
	{
	    ch = Character.toUpperCase(ch);
	    return (ch == 'A' || ch == 'E' || 
	            ch == 'I' || ch == 'O' ||
	            ch == 'U');
	}
	
	// Returns count of vowels in string
	public int countVowels(String str)
	{
	    int count = 0;
	    for(int i = 0; i < str.length(); i++)	     
	        // Check for vowel
	        if (isVowel(str.charAt(i))) 
	            ++count;	             
	    return count;
	}
	
	// Returns count of consonants in string
		public int countConsonants(String str)
		{
		    int count = 0;
		    for(int i = 0; i < str.length(); i++)	
		    	// Check for number
		    	if (Character.isDigit(str.charAt(i)))
		    		continue;
		        // Check for vowel
		    	else if (!isVowel(str.charAt(i))) 
		            ++count;	             
		    return count;
		}
	
	// Sort list related to length of string 
	public String sortLength(ArrayList<String> list) {
		String result = "";
		return result;
	}
	
	// Turn list array to String
	public String toList(ArrayList<String> list) {
		String result = "";
		for (int i = 0; i < list.size();i++) {
			result += list.get(i) + "\n";
		}
		return result;
	}
	
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
	
	private void PrintHead (PrintWriter out)
	{
	out.println("<html>");
	out.println("");

	out.println("<head>");
	out.println("<title>Final</title>");
	out.println(" <link rel=\"stylesheet\" type=\"text/css\" href=\"" + Style + "\">");
	out.println("</head>");
	out.println("");
	} // End PrintHead
	
	private void PrintBody (PrintWriter out, String entered, String result)
	{
	out.println("<body>");
	out.println("<h1><center>Supreme List Sorter</center></h1>");
	//out.println("<p>");
	out.print  ("<form method=\"post\" action=\"/" + Servlet + "\">");
	out.println("<center>");
	out.println("");
	//
	out.println("<hr>");
	out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"70%\" align=\"center\">");
	out.println("<tr>");
	out.println("  <td width=\"90%\" colspan=\"8\">");
	out.println("  </td>");
	out.println("</tr>");
	out.println("<tr>");
	out.println("  <td bgcolor=\"#84D47C\" align=\"center\" width=\"35%\" colspan=\"2\"><b> Enter Strings or Numbers as a List </b></br>");
	out.println("    <i>*UNIQUE: duplicates will be discarded from result</i></br>");
	out.println("    <i>Place each string on a new line.</i>");
	out.println("  </td>");
	out.println("  <td bgcolor=\"#E8E47D\" align=\"center\" width=\"35%\" colspan=\"2\"><b>*Alternate Sort Options:</b></br>");
	out.println("    <i>String Order: Basic sort\n</i></br>");
	out.println("    <i>Length: String Length</i></br>");
	out.println("    <i>Vowels: Number of vowels</i></br>");
	out.println("    <i>Consonants: Number of those</i></br>");
	out.println("  </td>");
	out.println("");
	//71out.println("  <td width=\"2%\" colspan=\"1\">&nbsp;&nbsp;</td>");
	out.println("</tr>");
	out.println("<tr>");
	out.println("  <td bgcolor=\"#84D47C\" align=\"center\" width=\"35%\" colspan=\"2\">");
	out.println("    <textarea rows=\"20\" name=\"listEnter\" id=\"listEnter\" cols=\"25\" autofocus=true>");
	out.println("" + entered +"");
	out.println("</textarea>");
	out.println("  </td>");
	out.println("  <td bgcolor=\"#E8E47D\" align=\"center\" width=\"35%\" colspan=\"2\">");
	out.println("    <textarea rows=\"20\" name=\"listChosen\" id=\"listChosen\" cols=\"25\" readonly=\"readonly\" >");
	out.println("" + result +"");
	out.println("</textarea>");
	out.println("  </td>");
	out.println("  <td bgcolor=\"#CDD7E1\" width=\"20%\" colspan=\"2\" align=\"center\">");
	out.println("    <table border=\"0\">");
	out.println("      <tr>");
	out.println("        <td>");
	out.println("          <label><input checked type=\"radio\" value=\"Ascending\" id = \"orderOption\" name=\"orderOption\">Ascending</label>");
	out.println("        </td>");
	out.println("      </tr>");
	out.println("      <tr>");
	out.println("        <td>");
	out.println("          <label><input type=\"radio\" value=\"Descending\" id = \"orderOption\" name=\"orderOption\">Descending</label>");
	out.println("        </td>");
	out.println("      </tr>");
	out.println("      <tr cellpadding=\"2\">");
	out.println("        <td align=\"center\">");
	out.println("          <input type=\"submit\" value=\""+Sort+"\" name=\"Operation\" style=\"background-color: #20A1A1\">");
	out.println("        </td>");
	out.println("      </tr>");
	out.println("      <tr cellpadding=\"2\">");
	out.println("        <td align=\"center\">&nbsp;</td>");
	out.println("      </tr>");
	out.println("      <tr>");
	out.println("        <td align=\"center\">");
	out.println(" 			<input type=\"submit\" value=\""+Reset+"\" name=\"Operation\" style=\"background-color: #20A1A1\">");
	out.println("        </td>");
	out.println("      </tr>");
	out.println("      <tr cellpadding=\"2\">");
	out.println("        <td align=\"center\">&nbsp;</td>");
	out.println("      </tr>");
	out.println("      <tr>");
	out.println("        <td align=\"center\">");
	out.println("          <p>SORT OPTIONS:</p>");
	out.println("          <select name=\"sorter\">");
	out.println("          <option value=\"AS ENTERED\" selected=\"selected\">AS ENTERED</option>");
	out.println("          <option value=\"String Order\">String Order</option>");
	out.println("          <option value=\"Length\">Length</option>");
	out.println("          <option value=\"Vowels\">Vowels</option>");
	out.println("          <option value=\"Consonants\">Consonants</option>");
	out.println("          </select>");
	out.println("        </td>");
	out.println("      </tr>");
	out.println("    </table>");
	out.println("  </td>");
	out.println("</tr>");
	out.println("</table>");
	out.println("<hr>");
	out.println("</form>");
	out.println("</body>");
	out.println("");
	} // End PrintBody

	/** *****************************************************
	*  Overloads PrintBody (out,first,second,third,result) to print a page
	*  with blanks in the form fields.
	********************************************************* */
	private void PrintBody (PrintWriter out)
	{
	PrintBody(out, "", "");
	}

	/** *****************************************************
	*  Prints the bottom of the HTML page.
	********************************************************* */
	private void PrintTail (PrintWriter out)
	{
	out.println("");
	out.println("</html>");
	} // End PrintTail
}
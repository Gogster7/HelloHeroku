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

	static String Sort = "SORT";
	// Style
	static String Style ="https://www.cs.gmu.edu/~gterziys/public_html/style.css";
	
	@Override
	public void doPost (HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		String result = "";
		String operation = request.getParameter("Operation");
		String firstStr = request.getParameter("First");
		String secondStr = request.getParameter("Second");
		String thirdStr = request.getParameter("Third");
		String sep = request.getParameter("separator");

		switch(operation) {
		   case "A->B->C" :
			   result = firstStr + sep + secondStr + sep + thirdStr;
		      break;
		   case "A->C->B" :
			  result = firstStr + sep + thirdStr + sep + secondStr;
		      break; 
		   case "B->A->C" :
			  result = secondStr + sep + firstStr + sep + thirdStr;
		      break; 
		   case "B->C->A" :
			  result = secondStr + sep + thirdStr + sep + firstStr;
		      break;
		   case "C->A->B" :
			  result = thirdStr + sep + firstStr + sep + secondStr;
		      break; 
		   case "C->B->A" :
			  result = thirdStr + sep + secondStr + sep + firstStr;
		      break; 
		   case "REVERSE" :
			   firstStr = reverse(firstStr);
			   secondStr = reverse(secondStr);
			   thirdStr = reverse(thirdStr);
			   break;	   
		}
		
		result = request.getParameter("strEnter");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		PrintHead(out);
		PrintBody(out, firstStr, secondStr, thirdStr, result);
		PrintTail(out);
	}
	
	public String reverse(String input) {
	    if (input == null) {
	        return input;
	    }
	    String output = "";
	    for (int i = input.length() - 1; i >= 0; i--) {
	        output = output + input.charAt(i);
	    }
	    return output;
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
	
	private void PrintBody (PrintWriter out, String first, String second, String third, String result)
	{
	out.println("<body>");
	out.println("<h1><center>Supreme List Sorter</center></h1>");
	//out.println("<p>");
	out.print  ("<form method=\"post\" action=\"/" + Servlet + "\">");
	out.println("<center>");
	out.println("");
	//
	out.println("<table border=\"0\" cellpadding=\"2\" cellspacing=\"1\" width=\"70%\" align=\"center\">");
	out.println("<tr>");
	out.println("  <td width=\"90%\" colspan=\"8\">");
	out.println("    <h2 align=\"center\">String Randomization</h2>");
	out.println("    <hr>");
	out.println("  </td>");
	out.println("</tr>");
	out.println("<tr>");
	out.println("  <td bgcolor=\"#CCFFFF\" align=\"center\" width=\"35%\" colspan=\"2\"><b> Enter Strings or Numbers as a List </b></br>");
	out.println("    <i>*UNIQUE: duplicates will be discarded from result.</i>");
	out.println("  </td>");
	out.println("  <td bgcolor=\"#EEEEEE\" align=\"center\" width=\"35%\" colspan=\"2\"><b>Alternative Sort Options:</b></br>");
	out.println("    <i>1 & 2:Alphabetical and Numerical ordering</i>");
	out.println("    <i>Length:String Length</i>");
	out.println("    <i>Vowels: Number of vowels</i>");
	out.println("    <i>Consonants:Number of those</i>");
	out.println("  </td>");
	out.println("");
	//71out.println("  <td width=\"2%\" colspan=\"1\">&nbsp;&nbsp;</td>");
	out.println("</tr>");
	out.println("<tr>");
	out.println("  <td bgcolor=\"#CCFFFF\" align=\"center\" width=\"35%\" colspan=\"2\">");
	out.println("    <textarea rows=\"20\" name=\"strEnter\" id=\"strEnter\" cols=\"25\" autofocus=true>");
	out.println("</textarea>");
	out.println("  </td>");
	out.println("  <td width=\"2%\" colspan=\"1\">&nbsp;&nbsp;</td>");
	out.println("  <td bgcolor=\"#EEEEEE\" align=\"center\" width=\"35%\" colspan=\"2\">");
	out.println("    <textarea rows=\"20\" name=\"strChosen\" id=\"strChosen\" cols=\"25\" readonly=\"readonly\" >");
	out.println("    " + result +"");
	out.println("</textarea>");
	out.println("  </td>");
	out.println("  <td width=\"2%\" colspan=\"1\">&nbsp;&nbsp;</td>");
	out.println("  <td width=\"25%\" colspan=\"2\" align=\"center\">");
	out.println("    <table border=\"0\">");
	out.println("      <tr>");
	out.println("        <td>");
	out.println("          <label><input checked type=\"radio\" value=\"A\" id = \"orderOption\" name=\"replacementOption\">Ascending</label>");
	out.println("        </td>");
	out.println("      </tr>");
	out.println("      <tr>");
	out.println("        <td>");
	out.println("          <label><input type=\"radio\" value=\"D\" id = \"orderOption\" name=\"replacementOption\">Descending</label>");
	out.println("        </td>");
	out.println("      </tr>");
	out.println("      <tr cellpadding=\"2\">");
	out.println("        <td align=\"center\">");
	out.println("          <input type=\"submit\" value=\""+Sort+"\" name=\"Operation\" style=\"background-color: #3FD5A1\">");
	out.println("        </td>");
	out.println("      </tr>");
	out.println("      <tr cellpadding=\"2\">");
	out.println("        <td align=\"center\">&nbsp;</td>");
	out.println("      </tr>");
	out.println("      <tr cellpadding=\"2\">");
	out.println("        <td align=\"center\">&nbsp;</td>");
	out.println("      </tr>");
	out.println("      <tr>");
	out.println("        <td align=\"center\">");
	out.println(" 			<input type=\"reset\" value=\"Reset\" name=\"btn\" style=\"background-color: #3FD5A1\">");
	out.println("        </td>");
	out.println("      </tr>");
	out.println("      <tr>");
	out.println("        <td align=\"center\">");
	out.println("          <p>SEPARATOR:</p>");
	out.println("          <select name=\"separator\">");
	out.println("          <option value=\"AS ENTERED\" selected=\"selected\">AS ENTERED</option>");
	out.println("          <option value=\"Numbers First\">Numbers First</option>");
	out.println("          <option value=\"Strings First\">Strings First</option>");
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
	PrintBody(out, "", "", "", "");
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

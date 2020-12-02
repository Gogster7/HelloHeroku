/** *****************************************************************
practiceFinal.java   servlet example
Concatenate 3 Strings

    @author George Terziysky
********************************************************************* */
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.annotation.WebServlet;
@WebServlet( name = "practiceFinal", urlPatterns = {"/practiceFinal"} )


public class practiceFinal extends HttpServlet{
	//Location of servlet
	static String Domain  = "";
	static String Path    = "";
	static String Servlet = "practiceFinal";
	
	//Button labels
	static String ABC = "A->B->C";
	static String ACB = "A->C->B";
	static String BAC = "B->A->C";
	static String BCA = "B->C->A";
	static String CAB = "C->A->B";
	static String CBA = "C->B->A";
	
	static String Reverse = "REVERSE";
	
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

		switch(operation) {
		   case "A->B->C" :
			   result = firstStr + secondStr + thirdStr;
		      break;
		   case "A->C->B" :
			  result = firstStr + thirdStr + secondStr;
		      break; 
		   case "B->A->C" :
			  result = secondStr + firstStr + thirdStr;
		      break; 
		   case "B->C->A" :
			  result = secondStr + thirdStr + firstStr;
		      break;
		   case "C->A->B" :
			  result = thirdStr + firstStr + secondStr;
		      break; 
		   case "C->B->A" :
			  result = thirdStr + secondStr + firstStr;
		      break; 
		   case "REVERSE" :
			   firstStr = reverse(firstStr);
			   secondStr = reverse(secondStr);
			   thirdStr = reverse(thirdStr);
			   break;	   
		}

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
	out.println("<title>Practice Final</title>");
	out.println(" <link rel=\"stylesheet\" type=\"text/css\" href=\"" + Style + "\">");
	out.println("</head>");
	out.println("");
	} // End PrintHead
	
	private void PrintBody (PrintWriter out, String first, String second, String third, String result)
	{
	out.println("<body>");
	out.println("<h1><center>Triple String Concatenation</center></h1>");
	out.println("<p>");
	out.print  ("<form method=\"post\" action=\"/" + Servlet + "\">");
	out.println("<center>");
	out.println("");
	out.println(" <table>");
	out.println("  <tr>");
	out.println("   <td>First String:");
	out.println("   <td><input type=\"text\" name=\"First\" value=\"" + first + "\" size=5>");
	out.println("  </tr>");
	out.println("  <tr>");
	out.println("   <td>Second String:");
	out.println("   <td><input type=\"text\" name=\"Second\" value=\"" + second + "\" size=5>");
	out.println("  </tr>");
	out.println("  <tr>");
	out.println("   <td>Third String:");
	out.println("   <td><input type=\"text\" name=\"Third\" value=\"" + third + "\" size=5>");
	out.println("  </tr>");
	out.println("  <tr>");
	out.println("  </tr>");
	out.println("  <tr>");
	out.println("   <td>Result:");
	out.println("   <td><input type=\"text\" name=\"Result\" value=\"" + result + "\" size=6>");
	out.println("  </tr>");
	out.println(" </table>");
	out.println(" <br>");
	out.println(" <br>");
	out.println(" <input type=\"submit\" value=\"" + ABC + "\" name=\"Operation\">");
	out.println(" <input type=\"submit\" value=\"" + ACB + "\" name=\"Operation\">");
	out.println(" <input type=\"submit\" value=\"" + BAC + "\" name=\"Operation\">");
	out.println(" <input type=\"submit\" value=\"" + BCA + "\" name=\"Operation\">");
	out.println(" <input type=\"submit\" value=\"" + CAB + "\" name=\"Operation\">");
	out.println(" <input type=\"submit\" value=\"" + CBA + "\" name=\"Operation\">");
	out.println(" <br>");
	out.println(" <input type=\"submit\" value=\"" + Reverse + "\" name=\"Operation\" style=\"background-color: #3FD5A1\">");
	out.println(" <input type=\"reset\" value=\"Reset\" name=\"reset\" style=\"background-color: #3FD5A1\">");
	out.println("<center>");
	out.println("</form>");
	out.println("");
	out.println("</body>");
	} // End PrintBody

	/** *****************************************************
	*  Overloads PrintBody (out,lhs,rhs,result) to print a page
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

/** *****************************************************************
twoButtons.java   servlet example
Changed to in class 5 string concatenation

    @author Jeff Offutt
********************************************************************* */

import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//David: (1) adds servlet mapping annotation
import javax.servlet.annotation.WebServlet;
@WebServlet( name = "InClass5", urlPatterns = {"/InClass5"} )

//twoButtons class
//CONSTRUCTOR: no constructor specified (default)
//
//***************  PUBLIC OPERATIONS  **********************************
//public void doPost ()  --> prints a blank HTML page
//public void doGet ()  --> prints a blank HTML page
//private void PrintHead (PrintWriter out) --> Prints the HTML head section
//private void PrintBody (PrintWriter out) --> Prints the HTML body with
//          the form. Fields are blank.
//private void PrintBody (PrintWriter out, String lhs, String rhs, String result)
//          Prints the HTML body with the form.
//          Fields are filled from the parameters.
//private void PrintTail (PrintWriter out) --> Prints the HTML bottom
//***********************************************************************

public class StringToString extends HttpServlet
{

//Location of servlet.
//David: (5) adds the path of your form submit action
static String Domain  = "";
static String Path    = "";
static String Servlet = "InClass5";

//Button labels
static String OperationF2S = "First->Second";
static String OperationS2F = "Second->First";

//Other strings.
static String Style ="https://www.cs.gmu.edu/~offutt/classes/432/432-style.css";

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
String result = "";
String operation = request.getParameter("Operation");
String lhsStr = request.getParameter("LHS");
String rhsStr = request.getParameter("RHS");

if (operation.equals(OperationF2S))
{
	result = lhsStr + rhsStr;
}
else if (operation.equals(OperationS2F))
{
	result = rhsStr + lhsStr;
}


response.setContentType("text/html");
PrintWriter out = response.getWriter();
PrintHead(out);
PrintBody(out, lhsStr, rhsStr, result);
PrintTail(out);
}  // End doPost

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
out.println("<title>StringToString example</title>");
out.println(" <link rel=\"stylesheet\" type=\"text/css\" href=\"" + Style + "\">");
out.println("</head>");
out.println("");
} // End PrintHead

/** *****************************************************
*  Prints the <BODY> of the HTML page with the form data
*  values from the parameters.
********************************************************* */
private void PrintBody (PrintWriter out, String lhs, String rhs, String result)
{
out.println("<body>");
out.println("<p>");
out.println("A simple example that demonstrates how to concatenate two Strings");
out.println("in either order.");
out.println("</p>");
out.print  ("<form method=\"post\"");
//David: (4) changes  action's url to your own url using a relative path to the servlet.
//If left untouched, the operation buttons go to Prof. Offutt website, and
// if you provide an erroneous path you will see a 404 (Not Found) error.
//In the form action, you can specify an absolute or relative path to your URL
// and optionally the servlet that will respond to the action.
//However, the original line only works when your app is deployed
// and not when running locally (yourpage.com vs localhost:port).
// For simplicity, I used a relative path but it is strongly recommended
// to use absolute paths because they can cached by web servers and browsers
out.println(" action=\"/" + Servlet + "\">");
out.println("");
out.println(" <table>");
out.println("  <tr>");
out.println("   <td>First String:");
out.println("   <td><input type=\"text\" name=\"LHS\" value=\"" + lhs + "\" size=5>");
out.println("  </tr>");
out.println("  <tr>");
out.println("   <td>Second String:");
out.println("   <td><input type=\"text\" name=\"RHS\" value=\"" + rhs + "\" size=5>");
out.println("  </tr>");
out.println("  <tr>");
out.println("   <td>Result:");
out.println("   <td><input type=\"text\" name=\"RHS\" value=\"" + result + "\" size=6>");
out.println("  </tr>");
out.println(" </table>");
out.println(" <br>");
out.println(" <br>");
out.println(" <input type=\"submit\" value=\"" + OperationF2S + "\" name=\"Operation\">");
out.println(" <input type=\"submit\" value=\"" + OperationS2F + "\" name=\"Operation\">");
out.println(" <input type=\"reset\" value=\"Reset\" name=\"reset\">");
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

}  // End StringtoString

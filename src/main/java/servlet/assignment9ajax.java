// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;

// Import Java Libraries
import java.io.*;

// adds servlet mapping annotation
import javax.servlet.annotation.WebServlet;
@WebServlet( name = "assignment88", urlPatterns = {"/assignment88"} )

public class assignment9ajax extends HttpServlet{

	public void doGet (HttpServletRequest request, HttpServletResponse response)
		       throws ServletException, IOException
		{
		   response.setContentType("text/html");
		   PrintWriter out = response.getWriter();
		   
	       out.println("<!doctype html>");
	       out.println("<html lang=\"en-US\">");
	       out.println("");
		   out.println("<script type=\"text/javascript\" src=\"https://github.com/Gogster7/HelloHeroku/tree/master/src/main/java/servlet/assignment9Client.js\"></script>");
		   out.println("<script>");
		   out.println(" function setFocus()");
		   out.println(" {");
		   out.println("    document.tableForm.tableSpace.focus();");
		   out.println(" }");
		   out.println("</script>");
		   out.println("<html>");
		   out.println("<head>");
		   out.println("<meta charset=\"utf-8\" />");
		   out.println("<title>Logical Predicates</title>");
		   out.println("</head>");
		   out.println("<body onLoad=\"setFocus()\">");
		   out.println("    <h1><center>Predicate Logic Calculator</center></h1>");
		   out.println("    <h2><center>Sonal Kumar * Angela Gentile * George Terziysky * SWE-432-001</center>  </h2>");
		   out.println("	 <h3><center>Formatting/Syntax Instructions:</center></h3>");
		   out.println("    <h4><center>In order to calculate the final value of the logical operation, type in the predicate with the following constraints:</center></h4>");
		   out.println("");
		   out.println("    <ul>");
		   out.println("        <li>The entry should be typed in the format of (Variable A)(LOGICAL OPERATOR)(Variable B)</li>");
		   out.println("        <li>");
		   out.println("            The entry can also be an extention of the format described above. There can be multiple Variables with logical operations in between them. For example, you can enter");
		   out.println("            \"Apple OR Orange\",which would give you its Truth Table Values.");
		   out.println("            Variables can be any letter or Name. Each Variable and Operand must be separated by a space.");
		   out.println("        </li>");
		   out.println("    </ul>");
		   out.println("    <h4><center>Options for supported logical symbols:</center></h4>");
		   out.println("    <ul>");
		   out.println("        <li>Supported symbols for AND: \"&&\", \"AND\", \"&\", \"^\", \"*\"</li>");
		   out.println("        <li>Supported symbols for OR: \"||\", \"|\", OR, \"V\", \"+\" </li>");
		   out.println("        <li>Supported symbols for NOT: \"~\", \"NOT\", \"!\"</li>");
		   out.println("        <li>Supported symbols for EQUAL: \"==\", \"=\", \"EQUAL\"</li>");
		   out.println("    </ul>");
		   out.println("    <br />    <br />    <br />");
		   out.println("");
		   out.println("    <form method=\"post\" action=\"\\assignment88\" name=\"tableForm\">");
		   out.println("        <center>");
		   out.println("			<select name=\"display\">");
		   out.println("		  	<option value=\"1/0\" selected=“selected”>1/0</option>");
		   out.println("			<option value=\"T/F\">T/F</option>");
		   out.println("			<option value=\"t/f\">t/f</option>");
		   out.println("			<option value=\"X/O\">X/O</option>");
		   out.println("			<option value=\"TRUE/FALSE\">TRUE/FALSE</option>");
		   out.println("		</select>");
		   out.println("		</center>");
		   out.println("    <br />");
		   out.println("        <center>");
		   out.println("            <label for=\"logicalOperation\">Enter Logical Operation:</label>");
		   out.println("            <input onkeyup =\"getTable()\" type=\"text\" id=\"logicalOperation\" name=\"LOGICALOPERATION\"><br><br>");
		   //out.println("            <input type=\"submit\" value=\"Submit\" style=\"background-color: #80ced6\">");
		   out.println("			<p id=\"typed\"></p>");
		   out.println("			<p id=\"selected\"></p>");
		   out.println("			<table id=\"tableSpace\">Table: </table>");
		   out.println("        </center>");
		   out.println("    </form>");
		   out.println("<p><center>Collaboration Summary: All group members worked on different parts of the assignment and brought the pieces together in the end. Sonal worked on fixing up the previous html page used for assignment 3 and recreated the page to fulfill the requirements for the assignment. Angela worked on grabbing the input passed through from the user and using the doGet and doPost methods to manipulate the outcome. George calculated the outcome of the input text and created a table to visualize the results. George also made sure to include error checking to ensure that the user was only entering values that could be evaluated using logical predicates, and then dynamically implemented the truth table. </center></p>");
		   out.println("</body>");
		   out.println("</html>");
		   out.close();
		   } // End PrintHead 
}

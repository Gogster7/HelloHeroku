// From "Professional Java Server Programming", Patzer et al.,

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

// Import Java Libraries
import java.io.*;
import java.util.Enumeration;

@WebServlet(name = "attributeServlet", urlPatterns = {"/attribute"})
public class AttributeServlet extends HttpServlet
{
	// The link will have a parameter builtin
    String lifeCycleURL = "/attribute";
public void doGet (HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException
{
	// Get session object
	HttpSession session = request.getSession();
	String action = request.getParameter("action");
	if (action != null && action.equals("invalidate"))
	   {  // Called from the invalidate button, kill the session.
	      // Get session object
	      session = request.getSession();
	      session.invalidate();

	      response.setContentType("text/html");
	      PrintWriter out = response.getWriter();

	      out.println("<html>");
	      out.println("<head>");
	      out.println(" <title>In Class Assignment</title>");
	      out.println("</head>");
	      out.println("");
	      out.println("<body>");

	      out.println("<p>Your session has been invalidated.</P>");

	      // Create a link so the user can create a new session.
	    
	      out.println("<a href=\"" + lifeCycleURL + "?action=newSession\">");
	      out.println("Create new session</A>");

	      out.println("</body>");
	      out.println("</html>");
	      out.close();
	   } //end if
	else { 
   String name   = request.getParameter("attrib_name");
   String value  = request.getParameter("attrib_value");
   String remove = request.getParameter("attrib_remove");
   String album  = request.getParameter("attrib_album");
   String song = request.getParameter("attrib_song");

   if (remove != null && remove.equals("on"))
   {
      session.removeAttribute(name);
   }
   else
   {
      if ((name != null && name.length() > 0) && (value != null && value.length() > 0))
      {
         session.setAttribute(name, value);
      }
      if ((album != null && album.length() > 0) && (song != null && song.length() > 0))
      {
         session.setAttribute(album, song);
      }

   }
   response.setContentType("text/html");
   PrintWriter out = response.getWriter();

   out.println("<html>");
   // no-cache lets the page reload by clicking on the reload link
   out.println("<meta http-equiv=\"Pragma\" content=\"no-cache\">");
   out.println("<head>");
   out.println(" <title>Session lifecycle</title>");
   out.println("</head>");
   out.println("");

   out.println("<body>");
   out.println("<h1><center>Session attributes</center></h1>");

   out.println("Enter name and value of an attribute");


   String url = response.encodeURL("attribute");
   out.println("<form action=\"" + url + "\" method=\"GET\">");
   out.println(" Name: ");
   out.println(" <input type=\"text\" size=\"10\" name=\"attrib_name\">");

   out.println(" Value: ");
   out.println(" <input type=\"text\" size=\"10\" name=\"attrib_value\">");

   out.println(" Album: ");
   out.println(" <input type=\"text\" size=\"10\" name=\"attrib_album\">");

   out.println(" Song: ");
   out.println(" <input type=\"text\" size=\"10\" name=\"attrib_song\">");

   out.println(" <br><input type=\"checkbox\" name=\"attrib_remove\">Remove");
   out.println(" <input type=\"submit\" name=\"update\" value=\"Update\">");
   out.println("</form>");
   out.println("<hr>");
   
   out.print  ("<br><br><a href=\"" + lifeCycleURL + "?action=invalidate\">");
   out.println("Invalidate the session</a>");

   out.println("Attributes in this session:");
   Enumeration e = session.getAttributeNames();
   while (e.hasMoreElements())
   {
      String att_name  = (String) e.nextElement();
      String att_value = (String) session.getAttribute(att_name);
      String att_album  = (String) e.nextElement();
      String att_song = (String) session.getAttribute(att_album);

      out.print  ("<br><b>Name:</b> ");
      out.println(att_name);
      out.print  ("<br><b>Value:</b> ");
      out.println(att_value);
      out.print  ("<br><b>Album:</b> ");
      out.println(att_album);
      out.print  ("<br><b>Song:</b> ");
      out.println(att_song);
   } //end while

   out.println("</body>");
   out.println("</html>");
   out.close();
   } //end else
} // End doGet
} //End  SessionLifeCycle

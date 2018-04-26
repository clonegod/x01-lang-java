package clonegod.tomcat.usage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns="/demo")
public class HelloServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String message = req.getParameter("message");
		
		System.out.println("message=" + message);
		
		resp.getWriter().println(new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()) + " - " + message);
		
	}
	
	
}

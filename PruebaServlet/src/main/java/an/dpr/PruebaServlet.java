package an.dpr;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class PruebaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(PruebaServlet.class);

    public void doPost(HttpServletRequest req, HttpServletResponse res)
	    throws ServletException, IOException {
	log.debug("recibido un post!");
	doTema(req, res);
    }

    private void doTema(HttpServletRequest req, HttpServletResponse res)
	    throws IOException {
	// Obtenemos un objeto Print Writer para enviar respuesta
	res.setContentType("text/html");
	PrintWriter pw = res.getWriter();
	pw.println("<HTML><HEAD><TITLE>Leyendo parametros</TITLE></HEAD>");
	pw.println("<H2>Leyendo parámetros get</H2><P>");
	pw.println("<UL>\n");
	pw.println("Te llamas " + req.getParameter("nombre") + "<BR>");
	pw.println("y tienes " + req.getParameter("edad") + " anyos<BR>");
	pw.println("</BODY></HTML>");
	pw.close();

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
	    throws ServletException, IOException {
	log.debug("recibido un get!");
	doTema(req, res);
    }
}

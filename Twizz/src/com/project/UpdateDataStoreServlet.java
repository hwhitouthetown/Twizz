package com.project;

import java.io.IOException;
import javax.servlet.http.*;

import com.exceptions.InvalidEntityException;
import com.tools.CSVReader;

@SuppressWarnings("serial")
public class UpdateDataStoreServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// TODO : Securiser WS //
		try {
			CSVReader update = new CSVReader("WEB-INF/ressources/theme.csv","Theme");
		} catch (InvalidEntityException e) {
			e.printStackTrace();
		}
		
		try {
			CSVReader update = new CSVReader("WEB-INF/ressources/persoTheme.csv","Personnality");
		} catch (InvalidEntityException e) {
			e.printStackTrace();
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}

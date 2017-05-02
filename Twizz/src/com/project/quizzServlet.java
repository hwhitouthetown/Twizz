package com.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.*;

import com.entities.Question;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.tools.Util;

@SuppressWarnings("serial")
public class quizzServlet extends HttpServlet {

	Logger logger = Logger.getLogger("Logger");

	public static int NB_QUESTIONS = 5;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		String theme = req.getHeader("theme");

		List<Entity> list = Util.getQuizzFromTheme(theme);

		List<Question> questions = new ArrayList<Question>();

		List<Integer> out = new ArrayList<Integer>();

		for (int i = 0; i < NB_QUESTIONS; i++) {

			int index = Util.random(0, list.size()-1);
			
			if (out.contains(index)) {
				i--;
			} else {
				Question ques = new Question();
				ques = Util.convertToQuestion(list.get(index));
				out.add(index);
				questions.add(ques);
			}

		}
		
		String json = new Gson().toJson(questions);
		

		resp.getWriter().write(json);
	}

}

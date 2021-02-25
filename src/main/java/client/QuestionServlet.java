package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import dao.CandidateDao;
import dao.QuestionDao;
import persist.Ehdokkaat;
import persist.Kysymykset;
import persist.Vastaukset;

/**
 * Servlet implementation class QuestionClient
 */
@WebServlet("/questionservlet")
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static List<Ehdokkaat> candidateList = CandidateDao.getAllCandidates();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuestionServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String questionId = request.getParameter("kysymys_id");

		// delete question part
		deleteQuestion(Integer.parseInt(questionId));
		deleteAnswerOfOneQuestion(Integer.parseInt(questionId));
		out.print("<script>alert('Question is deleted successfully!')</script>");
		out.print("<script>location.replace('edit.jsp')</script>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// add question part
		String questionId = request.getParameter("id");
		if (questionId == null) {
			String newQuestionId = request.getParameter("lastquestionid");
			String newQuestion = request.getParameter("question");
			Kysymykset newQuestionObject = new Kysymykset(Integer.parseInt(newQuestionId), newQuestion);
			addQuestion(newQuestionObject); // add new question

			// add answers for new question
			for (int i = 1; i <= candidateList.size(); i++) {
				Vastaukset answer = new Vastaukset(i, Integer.parseInt(newQuestionId));
				answer.setVastaus(3);
				answer.setKommentti("ehdokkaan vastaus kysymykseen");
				addAnswerForNewQuestion(answer);
			}

			out.print("<script>alert('Question is added successfully!')</script>");
			out.print("<script>location.replace('edit.jsp')</script>");

		} else {
			// update question part
			String updatedQuestion = request.getParameter("kysymys");
			Kysymykset updatedQuestionObject = new Kysymykset(Integer.parseInt(questionId), updatedQuestion);
			updateQuestion(updatedQuestionObject);

			out.print("<script>alert('Question is updated successfully!')</script>");
			out.print("<script>location.replace('edit.jsp')</script>");

		}
	}
	
	//all the methods come here
	// method for adding question
	private void addQuestion(Kysymykset newQuestionObject) {
		String addURL = "http://localhost:8080/rest/questionservice/addquestion";
		Client c = ClientBuilder.newClient();
		WebTarget wt = c.target(addURL);
		Builder b = wt.request();

		Entity<Kysymykset> e = Entity.entity(newQuestionObject, MediaType.APPLICATION_JSON);

		b.post(e);
	}

	// method for updating question
	private void updateQuestion(Kysymykset updatedQuestionObject) {
		String updateURL = "http://localhost:8080/rest/questionservice/updatequestion";
		Client c = ClientBuilder.newClient();
		WebTarget wt = c.target(updateURL);
		Builder b = wt.request();

		Entity<Kysymykset> e = Entity.entity(updatedQuestionObject, MediaType.APPLICATION_JSON);
		b.post(e);
	}

	// method for deleting question
	private boolean deleteQuestion(int questionId) {
		String deleteURL = "http://localhost:8080/rest/questionservice/deletequestion/" + questionId;
		Client c = ClientBuilder.newClient();
		WebTarget wt = c.target(deleteURL);
		Builder b = wt.request();

		boolean delete = b.delete(Boolean.class);
		return delete;
	}

	// method for deleting all answer of one question
	private boolean deleteAnswerOfOneQuestion(int questionId) {
		String deleteURL = "http://localhost:8080/rest/answerservice/deletequestionanswer/" + questionId;
		Client c = ClientBuilder.newClient();
		WebTarget wt = c.target(deleteURL);
		Builder b = wt.request();

		boolean delete = b.delete(Boolean.class);
		return delete;
	}

	// method for adding default answers for new question
	private void addAnswerForNewQuestion(Vastaukset answer) {
		String addURL = "http://localhost:8080/rest/answerservice/addanswerfornewquestion";
		Client c = ClientBuilder.newClient();
		WebTarget wt = c.target(addURL);
		Builder b = wt.request();

		Entity<Vastaukset> e = Entity.entity(answer, MediaType.APPLICATION_JSON);

		b.post(e);
	}

}

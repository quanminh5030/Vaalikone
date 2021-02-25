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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import dao.QuestionDao;

import javax.ws.rs.client.Invocation.Builder;

import persist.Ehdokkaat;
import persist.Kysymykset;
import persist.Vastaukset;

/**
 * Servlet implementation class CandidateClient
 */
@WebServlet("/CandidateClient")
public class CandidateClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static List<Kysymykset> questionList = QuestionDao.getAllQuestions();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CandidateClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String uri = "http://127.0.0.1:8080/rest/candidateservice/addcandidate";

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		// get the form from add.jsp
		Integer newId = Integer.parseInt(request.getParameter("lastcandidateid"));
		String newSuku = request.getParameter("suku");
		String newEtu = request.getParameter("etu");
		Integer newIka = Integer.parseInt(request.getParameter("ika"));
		String newKoti = request.getParameter("koti");
		String newMiksi = request.getParameter("miksi");
		String newMita = request.getParameter("mita");
		String newAma = request.getParameter("ama");
		String newPuo = request.getParameter("puo");

		// Add more candidate part
		Ehdokkaat newCandidateObject = new Ehdokkaat(newId, newSuku, newEtu, newPuo, newKoti, newIka, newMiksi, newMita,
				newAma);
		Client c = ClientBuilder.newClient();
		WebTarget wt = c.target(uri);
		Builder b = wt.request();

		Entity<Ehdokkaat> e = Entity.entity(newCandidateObject, MediaType.APPLICATION_JSON);
		b.post(e);

		// add answer for new candidate
		for (int i = 1; i <= questionList.size(); i++) {
			Vastaukset answer = new Vastaukset(newId, i);
			answer.setVastaus(3);
			answer.setKommentti("ehdokkaan vastaus kysymykseen");
			addAnswerForNewCandidate(answer);
		}

//		out.print("<script>alert('Candidate is added successfully')</script>");
//		out.print("<script>location.replace('rtest.jsp')</script>");

		out.println("Candidate is added successfully! <h3><i>The default answers for all questions are 3</i></h3>");
		out.println("<br>");
		out.println("<a href='candidate.html'>Please Update The Answers</a>");
		out.println("<a href='rtest.jsp'>Back to Candidate Table</a>");

	}

	// method relating vastaukset table
	// method for adding default answers for new candidate
	private void addAnswerForNewCandidate(Vastaukset answer) {
		String addURL = "http://localhost:8080/rest/answerservice/addanswerfornewcandidate";
		Client c = ClientBuilder.newClient();
		WebTarget wt = c.target(addURL);
		Builder b = wt.request();

		Entity<Vastaukset> e = Entity.entity(answer, MediaType.APPLICATION_JSON);
		b.post(e);
	}

}

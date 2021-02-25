package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import dao.CandidateDao;
import persist.Ehdokkaat;
import rest.UploadService;

/**
 * Servlet implementation class CandidateServlet
 */
@WebServlet("/candidateservlet")
public class CandidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CandidateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String fname = request.getParameter("firstname");
		String lname = request.getParameter("lastname");
		List<Ehdokkaat> candidateList = CandidateDao.getAllCandidates();

		int n = 0;
		for (int i = 1; i <= candidateList.size(); i++) {
			Ehdokkaat candidate = CandidateDao.getOneCandidate(i);

			if (candidate.getEtunimi().equals(fname) && candidate.getSukunimi().equals(lname)) {
				session.setAttribute("candidateId", candidate.getEhdokasId());

				RequestDispatcher rqd = request.getRequestDispatcher("candidateTable.jsp");
				rqd.include(request, response);
			} else {
				n++;
			}
		}

		// if all the information in for-loop is wrong
		if (n == candidateList.size()) {
			response.getWriter().print("<script>alert('Wrong Information!')</script>");
			response.getWriter().print("<script>location.replace('candidate.html')</script>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}



}

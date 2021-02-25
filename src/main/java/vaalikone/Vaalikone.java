/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package vaalikone;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import static java.lang.Integer.parseInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.appengine.api.utils.SystemProperty;
import persist.Ehdokkaat;
import persist.Kysymykset;
import persist.Vastaukset;

/**
 * Vaalikone-servlet, vastaa vaalikoneen varsinaisesta toiminnallisuudesta
 *
 * @author Jonne
 */
public class Vaalikone extends HttpServlet {

	private int numOfQuestion;
	private int numOfCandidate;

	// get the java logger instance
	private final static Logger logger = Logger.getLogger(Loki.class.getName());

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int kysymys_id;

		// retrieve the http session and create a new one if the old one does not yet
		// exist
		HttpSession session = request.getSession(true);

		// retrieve the user object from the http session
		User usr = (User) session.getAttribute("usrobj");

		// if the user object is not found in the session, creating one
		if (usr == null) {
			usr = new User();
			logger.log(Level.FINE, "New user object created");
			session.setAttribute("usrobj", usr);
		}
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("vaalikones");
			em = emf.createEntityManager();
		} catch (Exception e) {
			response.getWriter().println("EMF+EM EI Onnistu");

			e.printStackTrace(response.getWriter());

			return;
		}

		// get the url parameter func which specifies the function you want to do.
		// func=haeEhdokas: retrieve information for a specific candidate and compare it
		// to the user's answers
		// If not specified, questions will be asked.
		String strFunc = request.getParameter("func");

		if (strFunc == null) { // for the questions part

			// retrieve the number of the previous question imported as a parameter
			String strKysymys_id = request.getParameter("q");

			// retrieve the answer to the previous question imported by the parameter
			String strVastaus = request.getParameter("vastaus");

			// If a question number (kysId) is set, that question is retrieved
			// otherwise, claim no. 1
			if (strKysymys_id == null) {
				kysymys_id = 1;
			} else {
				kysymys_id = parseInt(strKysymys_id);
				// if the answer is set, save it to the session user object
				if (strVastaus != null) {
					usr.addVastaus(kysymys_id, parseInt(strVastaus));
				}

				// determine the question to be searched next
				kysymys_id++;
			}

			Query qQ = em.createQuery("SELECT k FROM Kysymykset k");
			List<Kysymykset> questionList = qQ.getResultList();

			// the number of question
//			numOfQuestion = questionList.size();

			// get all the candidates
//			Query qC = em.createQuery("SELECT e FROM Ehdokkaat e");
//			List<Ehdokkaat> candidateList = qC.getResultList();

			// the number of candidate
//			numOfCandidate = candidateList.size();

			// if there are still questions left, get the next one
			if (kysymys_id <= questionList.size()) {
				try {
					// Search the database for the desired question
					Query q = em.createQuery("SELECT k FROM Kysymykset k WHERE k.kysymysId=?1");
					q.setParameter(1, kysymys_id);
					// Read the desired question in the list
					List<Kysymykset> kysymysList = q.getResultList();
					request.setAttribute("kysymykset", kysymysList);
					request.setAttribute("questionList", questionList); // get the list of all question
					request.getRequestDispatcher("/vastaus.jsp") // for the questions
							.forward(request, response);

				} finally {
					// Close the database connection
					if (em.getTransaction().isActive()) {
						em.getTransaction().rollback();
					}
					em.close();
				}

				// if the questions run out, the result is calculated!
			} else {

				// Clear the point array so that the points do not double when a possible
				// refresher occurs
				for (int i = 0; i <= questionList.size(); i++) {
					usr.scores.set(i, new Tuple<>(0, 0));
				}

				// Get a list of candidates
				Query qE = em.createQuery("SELECT e.ehdokasId FROM Ehdokkaat e");
				List<Integer> ehdokasList = qE.getResultList();

				// iterate through the candidate list
				for (int i = 1; i <= ehdokasList.size(); i++) {

					// Get a list of candidates' answers
					Query qV = em.createQuery("SELECT v FROM Vastaukset v WHERE v.vastauksetPK.ehdokasId=?1");
					qV.setParameter(1, i);
					List<Vastaukset> vastausList = qV.getResultList();

					// iterate through the answer list
					for (Vastaukset eVastaus : vastausList) {
						int scores;

						// search for User Candidate Score
						scores = usr.getScore(i);

						// calculate your score based on your and the candidate's answer
						scores += laskePisteet(usr.getVastaus(i), eVastaus.getVastaus());
						// user's answer ; candidate's answer

						logger.log(Level.INFO, "eID: {0} / k: {1} / kV: {2} / eV: {3} / p: {4}",
								new Object[] { i, eVastaus.getVastauksetPK().getKysymysId(), usr.getVastaus(i),
										eVastaus.getVastaus(), scores });
						usr.addPisteet(i, scores);
					}

				}

				// let’s move on to finding the best candidate
				strFunc = "haeEhdokas"; // apply candidate
			}

		}

		// if the strFunc value is "haeEhdokas", the desired person is searched for the
		// most suitable candidates for the user
		if ("haeEhdokas".equals(strFunc)) {
			// read from the url parameter "top list sequence number". If not specified, the
			// BEST option is sought.
			String strJarjestysnumero = request.getParameter("numero");
			Integer jarjestysnumero = 0;
			if (strJarjestysnumero != null) {
				jarjestysnumero = Integer.parseInt(strJarjestysnumero);
			}

			// Read the most suitable candidates for the user on the interim Tuple list.
			List<Tuple<Integer, Integer>> tpl = usr.haeParhaatEhdokkaat(); // search for Best Candidates

			// retrieve the details of a specific candidate
			Query q = em.createQuery("SELECT e FROM Ehdokkaat e WHERE e.ehdokasId=?1");
			q.setParameter(1, tpl.get(jarjestysnumero).ehdokasId);
			List<Ehdokkaat> parasEhdokas = q.getResultList();

			// hae ko. ehdokkaan vastaukset ;retrieve the answer of a specific candidate
			q = em.createQuery("SELECT v FROM Vastaukset v WHERE v.vastauksetPK.ehdokasId=?1");
			q.setParameter(1, tpl.get(jarjestysnumero).ehdokasId);
			List<Vastaukset> parhaanEhdokkaanVastaukset = q.getResultList();

			// get all the questions
			q = em.createQuery("SELECT k FROM Kysymykset k");
			List<Kysymykset> kaikkiKysymykset = q.getResultList();

			// get all the candidates
			q = em.createQuery("SELECT e FROM Ehdokkaat e");
			List<Ehdokkaat> candidateList = q.getResultList();

			// get the resutls
			ArrayList<Double> rl = new ArrayList<Double>();
			Double d = (Double) request.getAttribute("result");
			rl.add(d);
			session.setAttribute("resultList", rl);
			// still ...

			// redirects the data to the results presentation page
			request.setAttribute("candidateList", candidateList);
			request.setAttribute("kaikkiKysymykset", kaikkiKysymykset);
			request.setAttribute("kayttajanVastaukset", usr.getAnswerList());
			request.setAttribute("parhaanEhdokkaanVastaukset", parhaanEhdokkaanVastaukset);
			request.setAttribute("parasEhdokas", parasEhdokas);
			request.setAttribute("pisteet", tpl.get(jarjestysnumero).pisteet);
			request.setAttribute("jarjestysnumero", jarjestysnumero);
			request.getRequestDispatcher("/tulokset.jsp") // for the scores
					.forward(request, response);

			// Close the database connection
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();

		}

	}

	public int getNumOfQuestion() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("vaalikones");
			em = emf.createEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Query qQ = em.createQuery("SELECT k FROM Kysymykset k");
		List<Kysymykset> questionList = qQ.getResultList();

		// the number of question
		numOfQuestion = questionList.size();

		return numOfQuestion;
	}

	public int getNumOfCandidate() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("vaalikones");
			em = emf.createEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Query qC = em.createQuery("SELECT e FROM Ehdokkaat e");
		List<Ehdokkaat> candidateList = qC.getResultList();
		
		//the number of candidate
		numOfCandidate = candidateList.size();
		return numOfCandidate;		
	}

	// calculate score
	private Integer laskePisteet(Integer kVastaus, Integer eVastaus) {
		int pisteet = 0;
		if (kVastaus - eVastaus == 0) {
			pisteet = 3;
		}
		if (kVastaus - eVastaus == 1 || kVastaus - eVastaus == -1) {
			pisteet = 2;
		}
		if (kVastaus - eVastaus == 2 || kVastaus - eVastaus == -2 || kVastaus - eVastaus == 3
				|| kVastaus - eVastaus == -3) {
			pisteet = 1;
		}

//		if (kVastaus - eVastaus == 4 || kVastaus - eVastaus == -4) {
//			pisteet = 0;
//		}
		return pisteet;

	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}

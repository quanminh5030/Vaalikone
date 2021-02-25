package client;

import java.io.IOException;
import java.io.PrintWriter;

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
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Invocation.Builder;

import dao.CandidateDao;
import persist.Ehdokkaat;
/**
 * Servlet implementation class UpdateCandidate
 */
@WebServlet("/UpdateCandidate")
public class UpdateCandidate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCandidate() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//get the information from rtest.jsp
		Integer id = Integer.parseInt(request.getParameter("ehdokas_id"));
		
		deleteCandidate(id);
		deleteAnswerOfOneCandidate(id);
		out.println("<script>alert('Deleted successfully')</script>");
		out.println("<script>location.replace('rtest.jsp')</script>");
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//get the form from updateCan.jsp
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		 Integer id = Integer.parseInt(request.getParameter("id"));
			//Integer id =3;
		   String upSuku = request.getParameter("sukunimi");
		   String upEtu = request.getParameter("etunimi");
		   String upPuo = request.getParameter("puolue");
		   String upKoti = request.getParameter("koti");
		   Integer upIka = Integer.parseInt(request.getParameter("ika"));
		   String upMiksi = request.getParameter("miksi");
		   String upMita = request.getParameter("mita");
		   String upAma = request.getParameter("ammatti");
		   
		   //Update Candidate part
		   
		   Ehdokkaat updatedCandidateObject = new Ehdokkaat(id,upSuku,upEtu,upPuo,upKoti,upIka,upMiksi,upMita,upAma);
		   String uriUp = "http://127.0.0.1:8080/rest/candidateservice/updatecandidate";
		
		   Client c = ClientBuilder.newClient();
		   WebTarget wt = c.target(uriUp);
		   Builder b = wt.request();
		
		   Entity <Ehdokkaat> e = Entity.entity(updatedCandidateObject, MediaType.APPLICATION_JSON);
		
		 try {
			   b.post(e);
			   out.print("<script>alert('Updated successfully')</script>");
			   out.print("<script>location.replace('rtest.jsp')</script>");
		   }catch(Exception ex) {
			   ex.printStackTrace();
		   }
	}
	
	//all methods
	//delete candidate part 
		private boolean deleteCandidate(Integer id) {
			// TODO Auto-generated method stub
			String deleteUrl = "http://127.0.0.1:8080/rest/candidateservice/deletecandidate/"+id;
			
			Client c = ClientBuilder.newClient();
			WebTarget wt = c.target(deleteUrl);
			Builder b = wt.request();
			
			boolean delete = b.delete(Boolean.class);
			return delete;
		}
		
	//method relating vastaukset table
	// method for deleting all answer of one candidate
	private boolean deleteAnswerOfOneCandidate(int candidateId) {
		String deleteURL = "http://localhost:8080/rest/answerservice/deletecandidateanswer/" + candidateId;
		Client c = ClientBuilder.newClient();
		WebTarget wt = c.target(deleteURL);
		Builder b = wt.request();
		
		boolean delete = b.delete(Boolean.class);
		return delete;		
	}
}



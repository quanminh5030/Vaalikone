package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.AnswerDao;
import dao.QuestionDao;
import persist.Kysymykset;
import persist.Vastaukset;

@Path("/questionservice")
public class QuestionService {

	@Path("/addquestion")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addQuestion(Kysymykset question) {
		QuestionDao.addQuestion(question);
	}

	@Path("/deletequestion/{id}")
	@DELETE
	public boolean deleteQuestion(@PathParam("id") int id) {
		return QuestionDao.deleteQuestion(id);
	}

	@Path("/getonequestion")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getOneQuestion(int id) {
		return QuestionDao.getOneQuestion(id).getKysymys();
	}

	@Path("/getallquestions")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Kysymykset> getQuestions() {
		return QuestionDao.getAllQuestions();
	}

	@Path("/updatequestion")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateQuestion(Kysymykset question) {
		QuestionDao.updateQuestion(question);
	}
}

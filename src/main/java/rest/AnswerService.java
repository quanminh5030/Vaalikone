package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import dao.AnswerDao;
import persist.Vastaukset;

@Path("/answerservice")
public class AnswerService {

	@Path("/updateanswer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateAnswer(Vastaukset newAnswer) {
		AnswerDao.updateAnswer(newAnswer);
	}

	//add default answers for new question
	@Path("/addanswerfornewquestion")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addAnswerForNewQuestion(Vastaukset answer) {
		AnswerDao.addAnswerForNewQuestion(answer);
	}
	
	//add default answers for new candidate
	@Path("/addanswerfornewcandidate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addAnswerForNewCandidate(Vastaukset answer) {
		AnswerDao.addAnswerForNewCandidate(answer);
	}

	// delete all answers of one question
	@Path("/deletequestionanswer/{id}")
	@DELETE
	public boolean deleteAnswersOfOneQuestion(@PathParam("id") int questionId) {
		return AnswerDao.deleteAnswersOfOneQuestion(questionId);
	}

	// delete all answers of one candidate
	@Path("/deletecandidateanswer/{id}")
	@DELETE
	public boolean deleteAnswerOfOneCandidate(@PathParam("id") int candidateId) {
		return AnswerDao.deleteAnswersOfOneCandidate(candidateId);
	}
}

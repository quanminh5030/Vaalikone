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

import dao.CandidateDao;
import persist.Ehdokkaat;

@Path("/candidateservice")
public class CandidateService {

	@Path("/getallcandidates")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ehdokkaat> getAllCandidates() {
		return CandidateDao.getAllCandidates();
	}

	@Path("/addcandidate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addCandidate(Ehdokkaat candidate) {
		CandidateDao.addCandidate(candidate);
	}

	@Path("/updatecandidate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCandidate(Ehdokkaat newCan) {

		CandidateDao.updateCandidate(newCan);
	}

	@Path("/getonecandidate")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Ehdokkaat getonecandidate(int ehdokasId) {
		return CandidateDao.getOneCandidate(ehdokasId);
	}

	@DELETE
	@Path("/deletecandidate/{id}")
	public boolean deleteCandidate(@PathParam("id") int id) {
		return CandidateDao.deleteCandidate(id);
	}
	
	@Path("/addimage")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addImageDir(Ehdokkaat candidate) {
		CandidateDao.addCandidateImage(candidate);
	}

}

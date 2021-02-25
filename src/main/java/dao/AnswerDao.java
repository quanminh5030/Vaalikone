package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import persist.Ehdokkaat;
import persist.Kysymykset;
import persist.Vastaukset;
import persist.VastauksetPK;

public class AnswerDao {
	private static List<Kysymykset> questionList = QuestionDao.getAllQuestions();
	private static List<Ehdokkaat> candidateList = CandidateDao.getAllCandidates();

	private static EntityManagerFactory emf;

	private static EntityManager getEntityManager() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("vaalikones");
		}
		return emf.createEntityManager();
	}

	public static void updateAnswer(Vastaukset newAnswer) {
		EntityManager em = getEntityManager();
		Vastaukset answer = em.find(Vastaukset.class, new VastauksetPK(newAnswer.getVastauksetPK().getEhdokasId(),
				newAnswer.getVastauksetPK().getKysymysId()));
		if (answer != null) {
			em.getTransaction().begin();
			answer.setVastaus(newAnswer.getVastaus());
			em.getTransaction().commit();
			em.close();
		} else {
			System.out.println("not exist!");
		}
	}

	public static List<Vastaukset> getAllAnswerOfOneCandidate(int candidateId) {
		EntityManager em = getEntityManager();
		String q = "SELECT v FROM Vastaukset v WHERE v.vastauksetPK.ehdokasId=" + candidateId;
		List<Vastaukset> answerList = em.createQuery(q).getResultList();
		em.close();
		return answerList;
	}

	// when adding new question, the default answers of this question for all
	// candidates should be added too
	public static void addAnswerForNewQuestion(Vastaukset answer) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.persist(answer);
		em.getTransaction().commit();
		em.close();
	}

	// when adding new candidate, the default answers for that candidate should be
	// added too
	public static void addAnswerForNewCandidate(Vastaukset answer) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.persist(answer);
		em.getTransaction().commit();
		em.close();
	}

	// when deleting one candidate, all of his or her answers should be deleted too
	public static boolean deleteAnswersOfOneCandidate(int candidateId) {
		EntityManager em = getEntityManager();
		Vastaukset answer;
		for (int i = 1; i <= questionList.size(); i++) {
			answer = em.find(Vastaukset.class, new VastauksetPK(candidateId, i));
			em.getTransaction().begin();
			em.remove(answer);
			em.getTransaction().commit();
		}
		em.close();
		return true;
	}

	// when deleting one question, all candidates' answers for the question should
	// be deleted too
	public static boolean deleteAnswersOfOneQuestion(int questionId) {
		EntityManager em = getEntityManager();
		Vastaukset answer;
		for (int i = 1; i <= candidateList.size(); i++) {
			answer = em.find(Vastaukset.class, new VastauksetPK(i, questionId));
			em.getTransaction().begin();
			em.remove(answer);
			em.getTransaction().commit();
		}
		em.close();
		return true;
	}

}

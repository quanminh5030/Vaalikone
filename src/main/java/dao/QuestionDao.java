package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import persist.Kysymykset;

public class QuestionDao {
	private static EntityManagerFactory emf;

	private static EntityManager getEntityManager() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("vaalikones");
		}

		return emf.createEntityManager();
	}

	public static void addQuestion(Kysymykset question) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.persist(question);
		em.getTransaction().commit();
		em.close();
	}

	public static boolean deleteQuestion(int id) {
		EntityManager em = getEntityManager();
		Kysymykset question = em.find(Kysymykset.class, id);
		if (question != null) {
			em.getTransaction().begin();
			em.remove(question);
			em.getTransaction().commit();
			em.close();
			return true;
		}
		return false;
	}
	
	public static Kysymykset getOneQuestion(int id) {
		EntityManager em = getEntityManager();
		Kysymykset question = em.find(Kysymykset.class, id);
		em.close();
		return question;
	}
	

	public static List<Kysymykset> getAllQuestions() {
		EntityManager em = getEntityManager();
		Query q = em.createQuery("SELECT k FROM Kysymykset k");
		List<Kysymykset> questionList = q.getResultList();
		em.close();
		return questionList;
	}
	
	public static void updateQuestion(Kysymykset newQuestion) {
		EntityManager em = getEntityManager();
		Kysymykset question = em.find(Kysymykset.class, newQuestion.getKysymysId());
		em.getTransaction().begin();
		question.setKysymys(newQuestion.getKysymys());
		em.getTransaction().commit();
		em.close();
	}
}

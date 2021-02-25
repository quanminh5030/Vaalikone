package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import persist.Ehdokkaat;

public class CandidateDao {
	private static EntityManagerFactory emf;

	private static EntityManager getEntityManager() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("vaalikones");
		}
		return emf.createEntityManager();
	}

	public static List<Ehdokkaat> getAllCandidates() {
		EntityManager em = getEntityManager();
		List<Ehdokkaat> candidateList = em.createQuery("SELECT e FROM Ehdokkaat e").getResultList();
		em.close();
		return candidateList;
	}

	public static Ehdokkaat getOneCandidate(int id) {
		EntityManager em = getEntityManager();
		Ehdokkaat candidate = em.find(Ehdokkaat.class, id);
		em.close();
		return candidate;
	}

	// new
	public static void addCandidate(Ehdokkaat candidate) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.persist(candidate);
		em.getTransaction().commit();
		em.close();
	}

	public static void updateCandidate(Ehdokkaat newCan) {
		EntityManager em = getEntityManager();
		Ehdokkaat Can = em.find(Ehdokkaat.class, newCan.getEhdokasId());
		em.getTransaction().begin();
		Can.setSukunimi(newCan.getSukunimi());
		Can.setEtunimi(newCan.getEtunimi());
		Can.setIka(newCan.getIka());
		Can.setKotipaikkakunta(newCan.getKotipaikkakunta());
		Can.setAmmatti(newCan.getAmmatti());
		Can.setMiksiEduskuntaan(newCan.getMiksiEduskuntaan());
		Can.setMitaAsioitaHaluatEdistaa(newCan.getMitaAsioitaHaluatEdistaa());
		Can.setPuolue(newCan.getPuolue());
		em.getTransaction().commit();
		em.close();
	}

	public static boolean deleteCandidate(int ehdokasId) {
		EntityManager em = getEntityManager();
		Ehdokkaat e = em.find(Ehdokkaat.class, ehdokasId);
		if (e != null) {
			em.getTransaction().begin();
			em.remove(e);

			em.getTransaction().commit();
			em.close();
			return true;
		}
		return false;
	}

	// add image into ehdokkaat table
	public static void addCandidateImage(Ehdokkaat candidate) {
		EntityManager em = getEntityManager();
		Ehdokkaat e = em.find(Ehdokkaat.class, candidate.getEhdokasId());
		em.getTransaction().begin();
		e.setPicture(candidate.getPicture());
		em.getTransaction().commit();
		em.close();		
	}
	
	
}

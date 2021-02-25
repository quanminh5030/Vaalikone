/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaalikone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonne
 */
public class User implements Serializable {

	/**
	 * 
	 */
//	private static final long serialVersionUID = 3262098698622771486L;
	/**
	 * 
	 */
	Vaalikone vaa = new Vaalikone();
//	int numOfQuestion = vaa.getNumOfQuestion();
	int numOfCandidate = vaa.getNumOfCandidate();

//	private final ArrayList<Integer> vastaus = new ArrayList<>(20);
	private ArrayList<Integer> answer = new ArrayList<>();
	ArrayList<Tuple<Integer, Integer>> scores = new ArrayList<>();
	private final static Logger logger = Logger.getLogger(Loki.class.getName());

	/**
	 * The user object stores the information of the user of the election machine.
	 */
//    public Kayttaja() {
//
//        //täytelläänhän listat valmiiksi
//        for (int i = 0; i < 20; i++) {
//            this.vastaus.add(0);
//            this.pisteet.add(new Tuple<>(0, 0));
//        }
//
//    }

	public void taytaVastauksetJaPisteet() { // fill in the Answers and Score

		// after all, the lists are completed
		// num: number of questions (19) now
		for (int i = 0; i <= numOfCandidate; i++) {
			this.answer.add(0);
			this.scores.add(new Tuple<>(0, 0));
		}

	}

	/**
	 *
	 * @return Integer list of user responses
	 */
	public ArrayList<Integer> getAnswerList() {
		return this.answer;
	}

	/**
	 * Search for individual points in the score list
	 *
	 * @param ehdokasId candidate id number
	 * @return score relative to the candidate
	 */
	public Integer getScore(int ehdokasId) {
		if (this.scores.size() >= ehdokasId) {
			return this.scores.get(ehdokasId).pisteet;
		} else {
			return 0;
		}
	}

	/**
	 * Set a score for a specific candidate
	 *
	 * @param ehdokasId candidate id number
	 * @param pisteet   The value that is added
	 */
	public void addPisteet(Integer ehdokasId, Integer pisteet) {
		this.scores.set(ehdokasId, new Tuple<>(ehdokasId, pisteet));
	}

	/**
	 * Search for a single user answer to a question
	 *
	 * @param index question number
	 * @return A single integer response from the user's response list
	 */
	public Integer getVastaus(int index) {
		return this.answer.get(index);
	}

	/**
	 * Add an answer
	 *
	 * @param index   question number
	 * @param vastaus the value of the answer
	 */
	public void addVastaus(Integer index, Integer vastaus) {
		if (this.answer.size() == 0) {
			taytaVastauksetJaPisteet();
		}
		this.answer.set(index, vastaus);
	}

	/**
	 * Get the best candidates sorted by score
	 *
	 * @return Tuple-lista, (ehdokkaan id, pisteet)
	 */
	public ArrayList<Tuple<Integer, Integer>> haeParhaatEhdokkaat() { // search for Best Candidates

		/*
		 * Arrange the score containing Tuple. Java Collections.sort by default Organize
		 * lists from smallest to largest Collections.reverseOrder reverses the order
		 */
//		Collections.sort(this.scores, Collections.reverseOrder(comparator));

		this.scores.stream().forEach((tpl) -> {
			logger.log(Level.INFO, "Ehdokas ID={0} pisteet={1}", new Object[] { tpl.ehdokasId, tpl.pisteet });
		});

		return this.scores;
	}

	// Creating a comparator to organize a double
	// source:
	// http://stackoverflow.com/questions/5690537/sorting-a-tuple-based-on-one-of-the-fields
//	 Comparator<Tuple<Integer, Integer>> comparator = (Tuple<Integer, Integer> o1,
	// Tuple<Integer, Integer> o2) -> o1.pisteet.compareTo(o2.pisteet);
	transient Comparator<Tuple<Integer, Integer>> comparator = new Comparator<Tuple<Integer, Integer>>() {
		@Override
		public int compare(Tuple<Integer, Integer> o1, Tuple<Integer, Integer> o2) {
			return o1.pisteet.compareTo(o2.pisteet);
		}
	};

}

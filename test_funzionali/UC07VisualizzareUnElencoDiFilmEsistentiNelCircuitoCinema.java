package test_funzionali;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sistema.*;

public class UC07VisualizzareUnElencoDiFilmEsistentiNelCircuitoCinema {

	ApplicazioneAmministratoreSistema adminApp;
	ApplicazioneGestoreCinema managerApp;
	Calendar adminBirthday;
	Calendar managerBirthday;
	
	static ArrayList<String> actors;
	static ArrayList<String> genre;
	static String plot;
	static ArrayList<String> tags;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		actors = new ArrayList<String>();
		actors.add("Roberto Benigni");
		actors.add("Nicoletta Braschi");
		actors.add("Giorgio Cantarini");
		actors.add("Giustino Durano");
		genre = new ArrayList<String>();
		genre.add("Drammatico");
		genre.add("Commedia");
		plot = "Seconda guerra mondiale. Guido, sua moglie Dora e suo figlio Giosuè vengono rinchiusi in un campo nazista. Guido dice al figlio che si trovano in un lagher per partecipare ad un gioco a premi, dove chi fa più punti vince un carrarmato. In questo modo riesce a proteggere il figlio dall'orrore che stanno vivendo.";
		tags = new ArrayList<String>();
		tags.add("olocausto");
		tags.add("guerra");
		tags.add("oscar");
		tags.add("amore");
	}

	@Before
	public void setUp() throws Exception {
		adminBirthday = Calendar.getInstance();
		adminBirthday.set(1975, 2, 5);
		managerBirthday = Calendar.getInstance();
		managerBirthday.set(1980, 0, 1);
		adminApp = new ApplicazioneAmministratoreSistema("Anna",
				"Bianchi", "BNCNNA75C45D969Q", adminBirthday, "AnnaBianchi", "0000",
				"anna.bianchi@gmail.com");
		adminApp.login("AnnaBianchi", "0000");
		adminApp.resetApplication();
		// Add a new film to the list of film of the CircuitoCinema
		adminApp.inserisciNuovoFilm("10.5240/5A58-58D4-01CB-C41D-6902-K",
				"La vita è bella", "Roberto Benigni", actors, 120, 1997, genre,
				"Melampo Cinematografica", plot, tags);
		// Register the manager
		adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com");
		managerApp = new ApplicazioneGestoreCinema();
		managerApp.login("RSSLCU80A01D969P", "0000");
	}

	// Scenario principale: l'attore visualizza l'elenco di tutti i film nel sistema
	@Test
	public void UC7test1() {
		// 2. Il sistema mostra all’attore l’elenco di tutti i film esistenti nel
		// Circuito Cinema
		assertTrue(ApplicazioneAmministratoreSistema.printAllFilm());
		// 3. Il sistema chiede all’attore come intende proseguire
		// 4. L’attore comunica al sistema di voler terminare l’operazione
		return;
	}

	// Scenario alternativo 2a: Il sistema non trova alcun film e lo comunica all’attore
	@Test
	public void UC7test2() {
		// Remove the film in order to execute correctly this test
		adminApp.eliminaFilm("10.5240/5A58-58D4-01CB-C41D-6902-K");
		
		// 2a. Il sistema non trova alcun film
		assertFalse(ApplicazioneAmministratoreSistema.printAllFilm());
		return;
	}
	
	// Scenario alternativo 4a: L’attore comunica al sistema di voler effettuare una ricerca
	// Questo scenario prevede l'esecuzione dello scenario secondario
	// SS3: Cercare un film
	// Il testing dello scenario secondario è implementato nel test case
	// 				SS3CercareUnFilm.java
	// per una maggiore chiarezza
	@Test
	public void UC7test3() {
		// 2. Il sistema mostra all’attore l’elenco di tutti i film esistenti nel
		// Circuito Cinema
		assertTrue(ApplicazioneAmministratoreSistema.printAllFilm());
		// 3. Il sistema chiede all’attore come intende proseguire
		// 4a. L’attore comunica al sistema di voler effettuare una ricerca
		
		// Inizio dello scenario secondario SS3
	}

}

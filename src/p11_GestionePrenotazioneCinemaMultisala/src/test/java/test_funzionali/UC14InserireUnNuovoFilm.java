package test_funzionali;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sistema.*;

public class UC14InserireUnNuovoFilm {
	
	ApplicazioneAmministratoreSistema adminApp;
	Calendar adminBirthday;
	
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
		adminApp = new ApplicazioneAmministratoreSistema("Anna",
				"Bianchi", "BNCNNA75C45D969Q", adminBirthday, "AnnaBianchi", "0000",
				"anna.bianchi@gmail.com");
		adminApp.login("AnnaBianchi", "0000");
		adminApp.resetApplication();
	}

	// Scenario principale: Inserire un nuovo film
	@Test
	public void UC14test1() {
		// 2. L’applicazione Amministratore Sistema chiede all’Amministratore Sistema
		// di inserire le informazioni film
		// 3. L’Amministratore Sistema immette i dati richiesti
		// 4. L’applicazione Amministratore Sistema valida i dati inseriti
		// (non implementato)
		// 6. L'Amministratore Sistema conferma di voler procedere con l'inserimento
		// 7. L’applicazione Amministratore Sistema inserisce il nuovo film
		assertTrue(adminApp.inserisciNuovoFilm("10.5240/5A58-58D4-01CB-C41D-6902-K",
				"La vita è bella", "Roberto Benigni", actors, 120, 1997, genre,
				"Melampo Cinematografica", plot, tags));
	}

	// Scenario alternativo 3a: L’Amministratore Sistema decide di annullare l’operazione
	@Test
	public void UC14test2() {
		// 2. L’applicazione Amministratore Sistema chiede all’Amministratore Sistema
		// di inserire le informazioni film
		// 3a. L’Amministratore Sistema decide di annullare l’operazione
		return;
	}
	
	// Scenario alternativo 4a: L’applicazione Amministratore Sistema non valida i dati
	// immessi perché non completi e/o non formattati correttamente
	@Test
	public void UC14test3() {
		// 2. L’applicazione Amministratore Sistema chiede all’Amministratore Sistema
		// di inserire le informazioni film
		// 3. L’Amministratore Sistema immette i dati richiesti
		// 4a. L’applicazione Amministratore Sistema non valida i dati immessi
		// (non implementato; i dati non sono validi se non completi e/o non formattati
		// correttamente)
		// Andare al passo 2 dello scenario principale
	}

	// Scenario alternativo 6a: L'Amministratore Sistema non conferma l'operazione
	@Test
	public void UC14test4() {
		// 2. L’applicazione Amministratore Sistema chiede all’Amministratore Sistema
		// di inserire le informazioni film
		// 3. L’Amministratore Sistema immette i dati richiesti
		// 4. L’applicazione Amministratore Sistema valida i dati inseriti
		// (non implementato)
		// 6a. L'Amministratore Sistema non conferma l'operazione
		return;
	}
	
	// Scenario alternativo 7a: L’Applicazione Amministratore Sistema non inserisce
	// il film perché esiste già un film nel sistema con tale id
	@Test
	public void UC14test() {
		// Insert a film in order to execute correctly this test
		assertTrue(adminApp.inserisciNuovoFilm("10.5240/5A58-58D4-01CB-C41D-6902-K",
				"La vita è bella", "Roberto Benigni", actors, 120, 1997, genre,
				"Melampo Cinematografica", plot, tags));
		
		// 2. L’applicazione Amministratore Sistema chiede all’Amministratore Sistema
		// di inserire le informazioni film
		// 3. L’Amministratore Sistema immette i dati richiesti
		// 4. L’applicazione Amministratore Sistema valida i dati inseriti
		// (non implementato)
		// 6. L'Amministratore Sistema conferma di voler procedere con l'inserimento
		// 7a. L’applicazione Amministratore Sistema non inserisce il nuovo film
		assertFalse(adminApp.inserisciNuovoFilm("10.5240/5A58-58D4-01CB-C41D-6902-K",
				"La vita è bella", "Roberto Benigni", actors, 120, 1997, genre,
				"Melampo Cinematografica", plot, tags));
		// Andare al passo 2 dello scenario principale
	}
	
}

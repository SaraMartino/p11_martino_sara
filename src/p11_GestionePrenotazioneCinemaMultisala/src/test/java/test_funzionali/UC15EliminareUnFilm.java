package test_funzionali;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sistema.*;

public class UC15EliminareUnFilm {

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
		// Inserimento nuovo film
		assertTrue(adminApp.inserisciNuovoFilm("10.5240/5A58-58D4-01CB-C41D-6902-K",
				"La vita è bella", "Roberto Benigni", actors, 120, 1997, genre,
				"Melampo Cinematografica", plot, tags));
	}

	// Scenario principale: Eliminare un film
	@Test
	public void UC15test1() {
		// 2. L’applicazione Amministratore Sistema chiede all’Amministratore Sistema
		// di inserire l'id del film
		// 3. L’Amministratore Sistema immette i dati richiesti
		// 4. L’Applicazione Amministratore Sistema chiede conferma
		// 5. L'Amministratore Sistema conferma di voler eliminare il film
		// 6. L’Applicazione Amministratore Sistema effettua la cancellazione del film
		assertTrue(adminApp.eliminaFilm("10.5240/5A58-58D4-01CB-C41D-6902-K"));
	}
	
	// Scenario alternativo 3a: L’Amministratore Sistema decide di annullare l’operazione
	@Test
	public void UC15test2() {
		// 2. L’applicazione Amministratore Sistema chiede all’Amministratore Sistema
		// di inserire l'id del film
		// 3a. L’Amministratore Sistema decide di annullare l’operazione
		return;
	}
	
	// Scenario alternativo 5a: L'Amministratore Sistema non conferma l'operazione
	@Test
	public void UC15test3() {
		// 2. L’applicazione Amministratore Sistema chiede all’Amministratore Sistema
		// di inserire l'id del film
		// 3. L’Amministratore Sistema immette i dati richiesti
		// 4. L’Applicazione Amministratore Sistema chiede conferma
		// 5a. L'Amministratore Sistema non conferma l'operazione
		return;
	}
	
	// Scenario alternativo 6a: L’Applicazione Amministratore Sistema non cancella
	// il film perché l’id inserito non è valido
	@Test
	public void UC14test() {
		// 2. L’applicazione Amministratore Sistema chiede all’Amministratore Sistema
		// di inserire l'id del film
		// 3. L’Amministratore Sistema immette i dati richiesti
		// 4. L’Applicazione Amministratore Sistema chiede conferma
		// 5. L'Amministratore Sistema conferma di voler eliminare il film
		// 6a. L’Applicazione Amministratore Sistema non cancella il film perché
		// l’id inserito non è valido
		String wrongId = "wrongId";
		assertFalse(adminApp.eliminaFilm(wrongId));
		// Andare al passo 2 dello scenario principale
	}
}

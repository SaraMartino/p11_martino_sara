package test_funzionali;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sistema.*;

public class UC04VisualizzareLaProgrammazioneDiUnaSalaDiUnoDeiPropriCinema {

	ApplicazioneAmministratoreSistema adminApp;
	ApplicazioneGestoreCinema managerApp;
	Calendar adminBirthday;
	Calendar managerBirthday;
	
	static ArrayList<String> actors;
	static ArrayList<String> genre;
	static String plot;
	static ArrayList<String> tags;
	Cinema cinema;
	int salaId;
	
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
		// Add a cinema and a sala for the manager
		cinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		adminApp.addNewCinema("RSSLCU80A01D969P", cinema);
		salaId = managerApp.inserisciNuovaSala(cinema.getId(), "Sala A", 10, 10, 10);
		assertNotEquals(-1, salaId);
	}

	// Scenario principale: Il Gestore Cinema visualizza la programmazione di una delle sale
	// di uno dei propri cinema
	@Test
	public void UC4test1() {
		// Insert a new show in order to complete this use case correctly
		Calendar date = Calendar.getInstance();
		date.set(2018, 6, 1, 10, 00);
		date.add(Calendar.DAY_OF_MONTH, 5);
		Film film = ApplicazioneAmministratoreSistema.cercaFilmPerId("10.5240/5A58-58D4-01CB-C41D-6902-K");
		Spettacolo show = new Spettacolo(film, date, 10.0f);
		assertTrue(managerApp.addShows(cinema.getId(), show, 5, salaId));
		
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema desiderato
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		// 6. L’Applicazione Gestore Cinema mostra al Gestore Cinema l’elenco delle sale
		// registrate nel cinema selezionato
		assertTrue(managerApp.printRooms(cinema.getId()));
		// 7. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id della sala
		// 8. Il Gestore Cinema inserisce l’id della sala
		// 9. L’Applicazione Gestore Cinema valida i dati inseriti
		assertTrue(managerApp.printAllShows(cinema.getId(), salaId));
	}
	
	// Scenario alternativo 4a: il Gestore Cinema decide di annullare l'operazione
	@Test
	public void UC4test2() {
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4a. Il Gestore Cinema decide di annullare l'operazione
		return;
	}
	
	// Scenario alternativo 5a: L'Applicazione Gestore Cinema non valida i dati inseriti
	@Test
	public void UC4test3() {
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema desiderato
		// 5a. L'Applicazione Gestore Cinema non valida i dati immessi
		int wrongId = -10000;
		assertFalse(managerApp.hasCinema(wrongId));
		// Andare al passo 3 dello scenario principale
	}
	
	// Scenario alternativo 6a: L’Applicazione Gestore Cinema non trova sale registrate
	@Test
	public void UC4test4() {
		// Remove the room in order to complete this use case correctly
		assertTrue(managerApp.eliminaSala(cinema.getId(), salaId));
		
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema desiderato
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		// 6. 6a: L’Applicazione Gestore Cinema non trova sale registrate
		assertFalse(managerApp.printRooms(cinema.getId()));
		return;
	}
	
	// Scenario alternativo 8a: Il Gestore Cinema decide di annullare l’operazione
	@Test
	public void UC4test5() {
		// Insert a new show in order to complete this use case correctly
		Calendar date = Calendar.getInstance();
		date.set(2018, 6, 1, 10, 00);
		date.add(Calendar.DAY_OF_MONTH, 5);
		Film film = ApplicazioneAmministratoreSistema.cercaFilmPerId("10.5240/5A58-58D4-01CB-C41D-6902-K");
		Spettacolo show = new Spettacolo(film, date, 10.0f);
		assertTrue(managerApp.addShows(cinema.getId(), show, 5, salaId));
		
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema desiderato
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		// 6. L’Applicazione Gestore Cinema mostra al Gestore Cinema l’elenco delle sale
		// registrate nel cinema selezionato
		assertTrue(managerApp.printRooms(cinema.getId()));
		// 7. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id della sala
		// 8a. Il Gestore Cinema decide di annullare l’operazione
		return;
	}
	
	// Scenario alternativo 9a: L’Applicazione Gestore Cinema non valida i dati inseriti
	@Test
	public void UC4test6() {
		// Insert a new show in order to complete this use case correctly
		Calendar date = Calendar.getInstance();
		date.set(2018, 6, 1, 10, 00);
		date.add(Calendar.DAY_OF_MONTH, 5);
		Film film = ApplicazioneAmministratoreSistema.cercaFilmPerId("10.5240/5A58-58D4-01CB-C41D-6902-K");
		Spettacolo show = new Spettacolo(film, date, 10.0f);
		assertTrue(managerApp.addShows(cinema.getId(), show, 5, salaId));
		
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema desiderato
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		// 6. L’Applicazione Gestore Cinema mostra al Gestore Cinema l’elenco delle sale
		// registrate nel cinema selezionato
		assertTrue(managerApp.printRooms(cinema.getId()));
		// 7. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id della sala
		// 8. Il Gestore Cinema inserisce l’id della sala
		// 9a. L’Applicazione Gestore Cinema non valida i dati inseriti
		int wrongId = -10000;
		assertFalse(managerApp.printAllShows(cinema.getId(), wrongId));
		// Andare al passo 7 dello scenario principale
	}
	
	// Scenario alternativo 10a: L’Applicazione Gestore Cinema non trova spettacoli
	@Test
	public void UC4test7() {
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema desiderato
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		// 6. L’Applicazione Gestore Cinema mostra al Gestore Cinema l’elenco delle sale
		// registrate nel cinema selezionato
		assertTrue(managerApp.printRooms(cinema.getId()));
		// 7. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id della sala
		// 8. Il Gestore Cinema inserisce l’id della sala
		// 10a. L’Applicazione Gestore Cinema non trova spettacoli
		assertFalse(managerApp.printAllShows(cinema.getId(), salaId));
		return;
	}

}

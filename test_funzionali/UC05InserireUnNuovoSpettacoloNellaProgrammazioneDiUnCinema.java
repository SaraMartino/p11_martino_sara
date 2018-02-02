package test_funzionali;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sistema.*;

public class UC05InserireUnNuovoSpettacoloNellaProgrammazioneDiUnCinema {
	
	ApplicazioneAmministratoreSistema adminApp;
	ApplicazioneGestoreCinema managerApp;
	Calendar adminBirthday;
	Calendar managerBirthday;
	
	static ArrayList<String> actors;
	static ArrayList<String> genre;
	static String plot;
	static ArrayList<String> tags;
	Cinema cinema;
	
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
		int id = managerApp.inserisciNuovaSala(cinema.getId(), "Sala A", 10, 10, 10);
		assertNotEquals(-1, id);
	}

	// Scenario principale: Il Gestore Cinema inserisce un nuovo spettacolo
	@Test
	public void UC5test1() {
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema in cui vuole aggiungere
		// uno spettacolo in programmazione
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		
		// 6. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del film
		// 7. Il Gestore Cinema inserisce l’id del film di cui vuole aggiungere uno spettacolo
		// in programmazione
		Film film = ApplicazioneAmministratoreSistema.cercaFilmPerId("10.5240/5A58-58D4-01CB-C41D-6902-K");
		// 8. L’Applicazione Gestore Cinema valida i dati inseriti
		assertNotNull(film);
		
		// 9. L’Applicazione Gestore Cinema chiede al Gestore Cinema i dati
		// necessari all'inserimento
		// 12. L’Applicazione Gestore Cinema mostra al Gestore Cinema le sale
		// disponibili per i giorni specificati e nella fascia oraria richiesta
		// basandosi sulla durata del film e sul tempo di attrezzaggio della sala
		Calendar date = Calendar.getInstance();
		date.set(2018, 6, 1, 10, 00);
		date.add(Calendar.DAY_OF_MONTH, 5);
		Spettacolo show = new Spettacolo(film, date, 10.0f);
		ArrayList<Sala> rooms = null;
		rooms = managerApp.verifyRoomsAvailability(cinema.getId(), show, 5);
		assertNotNull(rooms);
		managerApp.printRoomsFromList(rooms);
		int salaId = rooms.get(0).getId();
		// 16. L’Applicazione Gestore Cinema inserisce il nuovo spettacolo
		assertTrue(managerApp.addShows(cinema.getId(), show, 5, salaId));
	}
	
	// Scenario alternativo 2a: L’Applicazione Gestore Cinema non trova alcun cinema registrato
	@Test
	public void UC5test2() {
		// Remove the cinema in order to complete this use case correctly
		adminApp.removeCinema("RSSLCU80A01D969P", cinema.getId());
		
		// 2a. L’Applicazione Gestore Cinema non trova alcun cinema registrato
		assertFalse(managerApp.printAllCinema());
		return;
	}
	
	// Scenario alternativo 4a: Il Gestore Cinema decide di annullare l’operazione
	@Test
	public void UC5test3() {
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema decide di annullare l'operazione
		return;
	}
	
	// Scenario alternativo 5a: L’Applicazione Gestore Cinema non valida i dati inseriti
	@Test
	public void UC5test4() {
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema in cui vuole aggiungere
		// uno spettacolo in programmazione
		// 5a. L'Applicazione Gestore Cinema non valida i dati immessi
		int wrongId = -36732839;
		assertFalse(managerApp.hasCinema(wrongId));
		// Andare al passo 3 dello scenario principale
	}
	
	// Scenario alternativo 7a: Il Gestore Cinema decide di annullare l’operazione
	@Test
	public void UC5test5() {
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema in cui vuole aggiungere
		// uno spettacolo in programmazione
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		// 6. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del film
		// 7a. Il Gestore Cinema decide di annullare l’operazione
		return;
	}
	
	// Scenario alternativo 8a: L’Applicazione Gestore Cinema non valida i dati inseriti
	@Test
	public void UC5test6() {
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema in cui vuole aggiungere
		// uno spettacolo in programmazione
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		
		// 6. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del film
		// 7. Il Gestore Cinema inserisce l’id del film di cui vuole aggiungere uno spettacolo
		// in programmazione
		Film film = ApplicazioneAmministratoreSistema.cercaFilmPerId("WrongId");
		// 8a. L’Applicazione Gestore Cinema non valida i dati inseriti
		assertNull(film);
		// Andare al passo 6 dello scenario principale
	}
	
	// Scenario alternativo 10a: Il Gestore Cinema decide di annullare l’operazione
	@Test
	public void UC5test7() {
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema in cui vuole aggiungere
		// uno spettacolo in programmazione
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		
		// 6. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del film
		// 7. Il Gestore Cinema inserisce l’id del film di cui vuole aggiungere uno spettacolo
		// in programmazione
		Film film = ApplicazioneAmministratoreSistema.cercaFilmPerId("10.5240/5A58-58D4-01CB-C41D-6902-K");
		// 8. L’Applicazione Gestore Cinema valida i dati inseriti
		assertNotNull(film);
		// 9. L’Applicazione Gestore Cinema chiede al Gestore Cinema i dati
		// necessari all'inserimento
		// 10a. Il Gestore Cinema decide di annullare l'operazione
		return;
	}
	
	// Scenario alternativo 11a e 12a: L’Applicazione Gestore Cinema non valida i dati inseriti
	// o non trova sale disponibili per la fascia oraria richiesta per i giorni specificati
	@Test
	public void UC5test8() {
		// Insertion of a show in schedule in order to execute this use case
		Calendar date = Calendar.getInstance();
		date.set(2018, 6, 1, 10, 00);
		date.add(Calendar.DAY_OF_MONTH, 5);
		Spettacolo show = new Spettacolo(ApplicazioneAmministratoreSistema.cercaFilmPerId("10.5240/5A58-58D4-01CB-C41D-6902-K"), date, 10.0f);
		ArrayList<Sala> rooms = null;
		rooms = managerApp.verifyRoomsAvailability(cinema.getId(), show, 5);
		assertFalse(rooms.isEmpty());
		int salaId = rooms.get(0).getId();
		assertTrue(managerApp.addShows(cinema.getId(), show, 5, salaId));
		
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema in cui vuole aggiungere
		// uno spettacolo in programmazione
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		
		// 7. Il Gestore Cinema inserisce l’id del film di cui vuole aggiungere uno spettacolo
		// in programmazione
		Film film = ApplicazioneAmministratoreSistema.cercaFilmPerId("10.5240/5A58-58D4-01CB-C41D-6902-K");
		// 8. L’Applicazione Gestore Cinema valida i dati inseriti
		assertNotNull(film);
		
		// 9. L’Applicazione Gestore Cinema chiede al Gestore Cinema i dati
		// necessari all'inserimento
		// 11a/12a. L’Applicazione Gestore Cinema non valida i dati inseriti
		// o non trova sale disponibili per la fascia oraria richiesta per i giorni specificati
		show = new Spettacolo(film, date, 10.0f);
		rooms = null;
		rooms = managerApp.verifyRoomsAvailability(cinema.getId(), show, 5);
		assertTrue(rooms.isEmpty());
		// Andare al passo 9 dello scenario principale
	}
	
	// Scenario alternativo 15a: Il Gestore Cinema decide di non confermare l'operazione
	@Test
	public void UC5test9() {
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema in cui vuole aggiungere
		// uno spettacolo in programmazione
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		
		// 7. Il Gestore Cinema inserisce l’id del film di cui vuole aggiungere uno spettacolo
		// in programmazione
		Film film = ApplicazioneAmministratoreSistema.cercaFilmPerId("10.5240/5A58-58D4-01CB-C41D-6902-K");
		// 8. L’Applicazione Gestore Cinema valida i dati inseriti
		assertNotNull(film);
		
		// 9. L’Applicazione Gestore Cinema chiede al Gestore Cinema i dati
		// necessari all'inserimento
		// 12. L’Applicazione Gestore Cinema mostra al Gestore Cinema le sale
		// disponibili per i giorni specificati e nella fascia oraria richiesta
		// basandosi sulla durata del film e sul tempo di attrezzaggio della sala
		Calendar date = Calendar.getInstance();
		date.set(2018, 6, 1, 10, 00);
		date.add(Calendar.DAY_OF_MONTH, 5);
		Spettacolo show = new Spettacolo(film, date, 10.0f);
		ArrayList<Sala> rooms = null;
		rooms = managerApp.verifyRoomsAvailability(cinema.getId(), show, 5);
		assertNotNull(rooms);
		managerApp.printRoomsFromList(rooms);
		// 15a. Il Gestore Cinema decide di non confermare l'operazione
		return;
	}

}

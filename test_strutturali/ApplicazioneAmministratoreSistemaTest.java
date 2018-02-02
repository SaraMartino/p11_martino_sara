package test_strutturali;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sistema.*;

public class ApplicazioneAmministratoreSistemaTest {
	
	ApplicazioneAmministratoreSistema app;
	Calendar managerBirthday;
	static ArrayList<String> actorsA;
	static ArrayList<String> actorsB;
	static ArrayList<String> genreA;
	static ArrayList<String> genreB;
	static String plotA;
	static String plotB;
	static ArrayList<String> tagsA;
	static ArrayList<String> tagsB;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		actorsA = new ArrayList<String>();
		actorsA.add("Roberto Benigni");
		actorsA.add("Nicoletta Braschi");
		actorsA.add("Giorgio Cantarini");
		actorsA.add("Giustino Durano");
		genreA = new ArrayList<String>();
		genreA.add("Drammatico");
		genreA.add("Commedia");
		plotA = "Seconda guerra mondiale. Guido, sua moglie Dora e suo figlio Giosuè vengono rinchiusi in un campo nazista. Guido dice al figlio che si trovano in un lagher per partecipare ad un gioco a premi, dove chi fa più punti vince un carrarmato. In questo modo riesce a proteggere il figlio dall'orrore che stanno vivendo.";
		tagsA = new ArrayList<String>();
		tagsA.add("olocausto");
		tagsA.add("guerra");
		tagsA.add("oscar");
		tagsA.add("amore");
		actorsB = new ArrayList<String>();
		actorsB.add("Leonardo DiCaprio");
		actorsB.add("Joseph Gordon-Levitt");
		actorsB.add("Marion Cotillard");
		actorsB.add("Ellen Page");
		actorsB.add("Michael Caine");
		genreB = new ArrayList<String>();
		genreB.add("Fantascienza");
		genreB.add("Thriller");
		plotB = "Dom Cobb (Leonardo DiCaprio) è un abile ladro, il migliore in assoluto nella pericolosa arte dell’estrazione, ovvero il furto di importanti segreti dal profondo subconscio durante lo stato onirico, quando la mente è maggiormente vulnerabile. La rara abilità di Cobb ne ha fatto una figura molto ricercata nell’ambiente del nuovo spionaggio industriale, ma anche un ricercato internazionale, facendogli perdere tutto ciò che ha amato. Ora a Cobb è stata offerta la chance di redimersi. Un ultimo lavoro potrebbe restituirgli la sua vita, se solo saprà ottenere l’impossibile – “inception”. Invece del furto perfetto, Cobb e la sua squadra di specialisti dovranno riuscire nell’opposto: il loro compito non sarà rubare un’idea ma impiantarne una. Se avranno successo, potrebbe trattarsi del crimine perfetto. Ma nessun livello di pianificazione rigorosa e di esperienza possono preparare la squadra al pericoloso nemico che sembra prevedere ogni loro mossa. Un nemico che solo Cobb avrebbe potuto aspettarsi.";
		tagsB = new ArrayList<String>();
		tagsB.add("sogno");
		tagsB.add("innesto");
		tagsB.add("subconscio");
		tagsB.add("tempo");
		tagsB.add("trottola");
	}

	@Before
	public void setUp() throws Exception {
		Calendar c = Calendar.getInstance();
		c.set(1975, 2, 5);
		app = new ApplicazioneAmministratoreSistema("Anna",
				"Bianchi", "BNCNNA75C45D969Q", c, "AnnaBianchi", "0000",
				"anna.bianchi@gmail.com");
		managerBirthday = Calendar.getInstance();
		managerBirthday.set(1980, 0, 1);
	}

	@Test
	public void testConstructor() {
		assertNotNull(app);
	}
	
	@Test
	public void testResetApplication() {
		assertFalse(app.resetApplication());
		app.login("AnnaBianchi", "0000");
		app.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com");
		Cinema cinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		app.addNewCinema("RSSLCU80A01D969P", cinema);
		app.inserisciNuovoFilm("10.5240/5A58-58D4-01CB-C41D-6902-K", "La vita è bella",
				"Roberto Benigni", actorsA, 120, 1997, genreA, "Melampo Cinematografica",
				plotA, tagsA);
		assertTrue(app.resetApplication());
	}

	@Test
	public void testLoginAndLogout() {
		assertFalse(app.logout());
		assertFalse(app.login(null, null));
		assertFalse(app.login("AnnaBianchi", null));
		assertFalse(app.login("WrongUsername", "0000"));
		assertFalse(app.login("AnnaBianchi", "WrongPassword"));
		assertTrue(app.login("AnnaBianchi", "0000"));
		assertTrue(app.isAdminLogged());
		assertFalse(app.login("AnnaBianchi", "0000"));
		assertTrue(app.logout());
		assertFalse(app.isAdminLogged());
	}
	
	@Test
	public void testGetRegisteredGestoreCinema() {
		app.login("AnnaBianchi", "0000");
		app.resetApplication();
		assertNull(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P"));
		app.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com");
		assertNotNull(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P"));
	}
	
	@Test
	public void testRegistraNuovoGestoreCinemaAndRemoveGestoreCinema() {
		assertFalse(app.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com"));
		assertFalse(app.removeGestoreCinema("RSSLCU80A01D969P"));
		
		app.login("AnnaBianchi", "0000");
		app.resetApplication();
		assertFalse(app.removeGestoreCinema("RSSLCU80A01D969P"));
		assertTrue(app.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com"));
		assertTrue(app.registraNuovoGestoreCinema("Marco", "Verdi", "VRDMRC80A01D969X",
				managerBirthday, "luca.rossi@gmail.com"));
		assertFalse(app.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com"));
		assertTrue(app.removeGestoreCinema("RSSLCU80A01D969P"));
	}
	
	@Test
	public void testModifyEmailAndPasswordGestoreCinema() {
		assertFalse(app.modifyEmailGestoreCinema("RSSLCU80A01D969P", "luca.rossi@alice.it"));
		assertFalse(app.modifyPasswordGestoreCinema("RSSLCU80A01D969P", "1111"));

		app.login("AnnaBianchi", "0000");
		app.resetApplication();
		assertFalse(app.modifyEmailGestoreCinema("RSSLCU80A01D969P", "luca.rossi@alice.it"));
		assertFalse(app.modifyPasswordGestoreCinema("RSSLCU80A01D969P", "1111"));
		app.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com");
		assertTrue(app.modifyEmailGestoreCinema("RSSLCU80A01D969P", "luca.rossi@alice.it"));
		assertTrue(app.modifyPasswordGestoreCinema("RSSLCU80A01D969P", "1111"));
		assertFalse(app.modifyPasswordGestoreCinema("RSSLCU80A01D969P", "2"));
		assertFalse(app.modifyPasswordGestoreCinema("RSSLCU80A01D969P", "p a s s"));
	}
	
	@Test
	public void testAddAndRemoveCinema() {
		Cinema cinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		assertFalse(app.addNewCinema("RSSLCU80A01D969P", cinema));
		assertFalse(app.removeCinema("RSSLCU80A01D969P", cinema.getId()));
		
		app.login("AnnaBianchi", "0000");
		app.resetApplication();
		assertFalse(app.addNewCinema("RSSLCU80A01D969P", cinema));
		assertFalse(app.removeCinema("RSSLCU80A01D969P", cinema.getId()));
		assertTrue(app.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com"));
		assertTrue(app.addNewCinema("RSSLCU80A01D969P", cinema));
		assertTrue(app.removeCinema("RSSLCU80A01D969P", cinema.getId()));
		
	}
	
	@Test
	public void testInserisciNuovoFilmAndEliminaFilm() {
		assertFalse(app.inserisciNuovoFilm("10.5240/5A58-58D4-01CB-C41D-6902-K",
				"La vita è bella", "Roberto Benigni", actorsA, 120, 1997, genreA,
				"Melampo Cinematografica", plotA, tagsA));
		assertFalse(app.eliminaFilm("10.5240/5A58-58D4-01CB-C41D-6902-K"));
		
		app.login("AnnaBianchi", "0000");
		app.resetApplication();
		assertTrue(app.inserisciNuovoFilm("10.5240/5A58-58D4-01CB-C41D-6902-K",
				"La vita è bella", "Roberto Benigni", actorsA, 120, 1997, genreA,
				"Melampo Cinematografica", plotA, tagsA));
		assertFalse(app.inserisciNuovoFilm("10.5240/5A58-58D4-01CB-C41D-6902-K",
				"La vita è bella", "Roberto Benigni", actorsA, 120, 1997, genreA,
				"Melampo Cinematografica", plotA, tagsA));
		assertTrue(app.inserisciNuovoFilm("10.5240/0EF3-54F9-2642-0B49-6829-R",
				"Inception", "Christopher Nolan", actorsB, 142, 2010, genreB,
				"Legendary Pictures", plotB, tagsB));
		assertTrue(app.eliminaFilm("10.5240/5A58-58D4-01CB-C41D-6902-K"));
		assertFalse(app.eliminaFilm("WrongId"));
	}
	
	@Test
	public void testPrint() {
		ArrayList<Film> film = new ArrayList<Film>();
		film.add(new Film("10.5240/5A58-58D4-01CB-C41D-6902-K",
				"La vita è bella", "Roberto Benigni", actorsA, 120, 1997, genreA,
				"Melampo Cinematografica", plotA, tagsA));
		ApplicazioneAmministratoreSistema.printFilmFromList(film);		

		assertFalse(app.printProfilo());
		app.login("AnnaBianchi", "0000");
		app.resetApplication();
		assertTrue(app.printProfilo());
		assertFalse(ApplicazioneAmministratoreSistema.printAllFilm());
		assertFalse(ApplicazioneAmministratoreSistema.printSchedaFilm("10.5240/5A58-58D4-01CB-C41D-6902-K"));
		app.inserisciNuovoFilm("10.5240/5A58-58D4-01CB-C41D-6902-K",
				"La vita è bella", "Roberto Benigni", actorsA, 120, 1997, genreA,
				"Melampo Cinematografica", plotA, tagsA);
		assertTrue(ApplicazioneAmministratoreSistema.printAllFilm());
		assertTrue(ApplicazioneAmministratoreSistema.printSchedaFilm("10.5240/5A58-58D4-01CB-C41D-6902-K"));
	}
	
	@Test
	public void testPrintAllManagers() {
		assertFalse(app.printAllManagers());
		app.login("AnnaBianchi", "0000");
		app.resetApplication();
		assertFalse(app.printAllManagers());
		
		app.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com");
		assertTrue(app.printAllManagers());
		
	}
	
	@Test
	public void testChangeEmailUsernameAndPassword() {
		assertFalse(app.changeEmail("anna.bianchi@alice.it"));
		assertFalse(app.changeUsername("AnnaB"));
		assertFalse(app.changePassword("1111"));

		app.login("AnnaBianchi", "0000");
		app.resetApplication();
		assertTrue(app.changeEmail("anna.bianchi@alice.it"));
		assertTrue(app.changeUsername("AnnaB"));
		assertFalse(app.changeUsername("AB"));
		assertFalse(app.changeUsername("u s e r"));
		assertTrue(app.changePassword("1111"));
		assertFalse(app.changePassword("2"));
		assertFalse(app.changePassword("p a s s"));
	}
	
	@Test
	public void testGetListaCinemaIterator() {
		app.login("AnnaBianchi", "0000");
		app.resetApplication();
		
		assertNull(ApplicazioneAmministratoreSistema.getListaCinemaIterator("RSSLCU80A01D969P"));
		app.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com");
		assertNotNull(ApplicazioneAmministratoreSistema.getListaCinemaIterator("RSSLCU80A01D969P"));
		
	}
	
	@Test
	public void testSearchFilm() {
		app.login("AnnaBianchi", "0000");
		app.resetApplication();
		
		Film film = ApplicazioneAmministratoreSistema.cercaFilmPerId("10.5240/5A58-58D4-01CB-C41D-6902-K");
		assertNull(film);
		
		app.inserisciNuovoFilm("10.5240/5A58-58D4-01CB-C41D-6902-K",
				"La vita è bella", "Roberto Benigni", actorsA, 120, 1997, genreA,
				"Melampo Cinematografica", plotA, tagsA);
		app.inserisciNuovoFilm("10.5240/0EF3-54F9-2642-0B49-6829-R",
				"Inception", "Christopher Nolan", actorsB, 142, 2010, genreB,
				"Legendary Pictures", plotB, tagsB);

		ArrayList<Film> list;
		list = ApplicazioneAmministratoreSistema.cercaFilmPerAnno(1997);
		assertFalse(list.isEmpty());
		list = ApplicazioneAmministratoreSistema.cercaFilmPerAttore("Benigni");
		assertFalse(list.isEmpty());
		list = ApplicazioneAmministratoreSistema.cercaFilmPerCasaDiProduzione("Melampo");
		assertFalse(list.isEmpty());
		list = ApplicazioneAmministratoreSistema.cercaFilmPerGenere("Drammatico");
		assertFalse(list.isEmpty());
		list = ApplicazioneAmministratoreSistema.cercaFilmPerRegista("Roberto");
		assertFalse(list.isEmpty());
		list = ApplicazioneAmministratoreSistema.cercaFilmPerTag("Guerra olocausto");
		assertFalse(list.isEmpty());
		list = ApplicazioneAmministratoreSistema.cercaFilmPerTitolo("La vita è bella");
		assertFalse(list.isEmpty());
		film = ApplicazioneAmministratoreSistema.cercaFilmPerId("10.5240/5A58-58D4-01CB-C41D-6902-K");
		assertNotNull(film);
	}
	
	@Test
	public void testSendEmail() {
		app.sendEmail("luca.rossi@gmail.com", "text");
	}

}

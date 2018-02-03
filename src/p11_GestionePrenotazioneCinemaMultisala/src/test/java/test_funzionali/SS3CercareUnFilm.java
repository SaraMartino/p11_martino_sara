package test_funzionali;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sistema.*;

//Questo Scenario Secondario viene chiamato in seguito all'esecuzione dello use case UC7
public class SS3CercareUnFilm {

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
		// Inserimento di un nuovo film alla lista dei film del CircuitoCinema
		adminApp.inserisciNuovoFilm("10.5240/5A58-58D4-01CB-C41D-6902-K",
				"La vita è bella", "Roberto Benigni", actors, 120, 1997, genre,
				"Melampo Cinematografica", plot, tags);
		// Registrazione di un nuovo gestore
		adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com");
		managerApp = new ApplicazioneGestoreCinema();
		managerApp.login("RSSLCU80A01D969P", "0000");
		
		// Questo Scenario Secondario viene chiamato in seguito all'esecuzione
		// dello use case UC7
		// Scenario alternativo 4a di UC7:
		// 2. Il sistema mostra all’attore l’elenco di tutti i film esistenti nel
		// Circuito Cinema
		assertTrue(ApplicazioneAmministratoreSistema.printAllFilm());
		// 3. Il sistema chiede all’attore come intende proseguire
		// 4a. L’attore comunica al sistema di voler effettuare una ricerca
	}

	// Scenario secondario: cercare un film
	@Test
	public void SS4test1() {
		// 1. Il sistema richiede all’attore il tipo di ricerca film e i
		// parametri di ricerca
		// 2. L’attore inserisce i dati richiesti
		// 3. Il sistema effettua la ricerca e mostra il risultato
		// Test all type of search
		ArrayList<Film> list;
		list = ApplicazioneAmministratoreSistema.cercaFilmPerAnno(1997);
		assertFalse(list.isEmpty());
		list = ApplicazioneAmministratoreSistema.cercaFilmPerAttore("Nicoletta Braschi");
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
		Film film = ApplicazioneAmministratoreSistema.cercaFilmPerId("10.5240/5A58-58D4-01CB-C41D-6902-K");
		assertNotNull(film);
		// Print only the last results
		ApplicazioneAmministratoreSistema.printFilmFromList(list);
		film.printBaseInfo();
	}
	
	// Scenario alternativo 2a: L'attore decide di annullare l’operazione
	@Test
	public void SS4test2() {
		// 1. Il sistema richiede all’attore il tipo di ricerca film e i
		// parametri di ricerca
		// 2a. L'attore decide di annullare l’operazione
		return;
	}
	
	// Scenario alternativo 3a: Il sistema effettua la ricerca, ma non trova alcun risultato
	@Test
	public void SS4test3() {
		// 1. Il sistema richiede all’attore il tipo di ricerca film e i
		// parametri di ricerca
		// 2. L’attore inserisce i dati richiesti
		// 3a. Il sistema effettua la ricerca, ma non trova alcun risultato
		// Test all type of search
		ArrayList<Film> list;
		int wrongYear = 2018;
		list = ApplicazioneAmministratoreSistema.cercaFilmPerAnno(wrongYear);
		assertTrue(list.isEmpty());
		String wrongParam = "DiCaprio";
		list = ApplicazioneAmministratoreSistema.cercaFilmPerAttore(wrongParam);
		assertTrue(list.isEmpty());
		wrongParam = "Legendary Pictures";
		list = ApplicazioneAmministratoreSistema.cercaFilmPerCasaDiProduzione(wrongParam);
		assertTrue(list.isEmpty());
		wrongParam = "Fantascienza";
		list = ApplicazioneAmministratoreSistema.cercaFilmPerGenere(wrongParam);
		assertTrue(list.isEmpty());
		wrongParam = "Nolan";
		list = ApplicazioneAmministratoreSistema.cercaFilmPerRegista(wrongParam);
		assertTrue(list.isEmpty());
		wrongParam = "iceberg";
		list = ApplicazioneAmministratoreSistema.cercaFilmPerTag(wrongParam);
		assertTrue(list.isEmpty());
		wrongParam = "Titanic";
		list = ApplicazioneAmministratoreSistema.cercaFilmPerTitolo(wrongParam);
		assertTrue(list.isEmpty());
		wrongParam = "-1";
		Film film = ApplicazioneAmministratoreSistema.cercaFilmPerId(wrongParam);
		assertNull(film);
		// Andare al passo 1 dello scenario secondario
	}

}

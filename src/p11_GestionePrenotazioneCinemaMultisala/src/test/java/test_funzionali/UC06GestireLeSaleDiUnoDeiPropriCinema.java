package test_funzionali;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import sistema.*;

public class UC06GestireLeSaleDiUnoDeiPropriCinema {

	ApplicazioneAmministratoreSistema adminApp;
	ApplicazioneGestoreCinema managerApp;
	Calendar adminBirthday;
	Calendar managerBirthday;
	
	Cinema cinema;
	int salaId;

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
		// Registerazione gestore
		adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com");
		managerApp = new ApplicazioneGestoreCinema();
		managerApp.login("RSSLCU80A01D969P", "0000");
		// Inserimento cinema e sala
		cinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		adminApp.addNewCinema("RSSLCU80A01D969P", cinema);
		salaId = managerApp.inserisciNuovaSala(cinema.getId(), "Sala A", 10, 10, 10);
		assertNotEquals(-1, salaId);
	}

	// Scenario principale: il Gestore Cinema gestisce le sale di uno dei propri cinema
	// (visualizza sale)
	@Test
	public void UC6test1() {
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema desiderato
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		// 6. L’Applicazione Gestore Cinema mostra al Gestore Cinema l’elenco delle sale
		// registrate nel cinema selezionato
		assertTrue(managerApp.printRooms(cinema.getId()));
		// 8. L’Applicazione Gestore Cinema chiede al Gestore Cinema come intende proseguire
		// 9. Il Gestore Cinema comunica al sistema di voler terminare l’operazione
		return;
	}

	// Scenario alternativo 2a: L’Applicazione Gestore Cinema non trova alcun cinema registrato
	@Test
	public void UC6test2() {
		// Remove the cinema in order to complete this use case correctly
		adminApp.removeCinema("RSSLCU80A01D969P", cinema.getId());
		
		// 2a. L’Applicazione Gestore Cinema non trova alcun cinema registrato
		assertFalse(managerApp.printAllCinema());
		return;
	}
	
	// Scenario alternativo 4a: il Gestore Cinema decide di annullare l'operazione
	@Test
	public void UC6test3() {
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4a. Il Gestore Cinema decide di annullare l'operazione
		return;
	}
	
	// Scenario alternativo 5a: L'Applicazione Gestore Cinema non valida i dati inseriti
	@Test
	public void UC6test4() {
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
	public void UC6test5() {
		// Rimozione sala per completare correttamente lo scenario alternativo
		assertTrue(managerApp.eliminaSala(cinema.getId(), salaId));
		
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema desiderato
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		// 6. 6a: L’Applicazione Gestore Cinema non trova sale registrate
		assertFalse(managerApp.printRooms(cinema.getId()));
		// Andare al passo 8 dello scenario principale
	}
	
	// Scenario alternativo 9a: Il Gestore Cinema comunica all’Applicazione
	// Gestore Cinema di voler eliminare una sala
	// Questo scenario prevede l'esecuzione dello scenario secondario
	// SS1: Eliminare una sala di uno dei propri cinema
	// Il testing dello scenario secondario è implementato nel test case
	// 				SS1EliminareUnaSalaDiUnoDeiPropriCinema.java
	// per una maggiore chiarezza
	@Test
	public void UC6test6() {
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema desiderato
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		// 6. L’Applicazione Gestore Cinema mostra al Gestore Cinema l’elenco delle sale
		// registrate nel cinema selezionato
		assertTrue(managerApp.printRooms(cinema.getId()));
		// 8. L’Applicazione Gestore Cinema chiede al Gestore Cinema come intende proseguire
		// 9a. Il Gestore Cinema comunica all’Applicazione Gestore Cinema di voler eliminare una sala
		
		// Inizio dello scenario secondario SS1
	}
	
	// Scenario alternativo 9b: Il Gestore Cinema comunica all’Applicazione
	// Gestore Cinema di voler inserire una nuova sala
	// Questo scenario prevede l'esecuzione dello scenario secondario
	// SS2: Inserire una nuova sala in uno dei propri cinema
	// Il testing dello scenario secondario è implementato nel test case
	// 				SS2InserireUnaNuovaSalaInUnoDeiPropriCinema.java
	// per una maggiore chiarezza
	@Test
	public void UC6test7() {
		// 2. L’Applicazione Gestore Cinema mostra al Gestore Cinema la lista dei cinema
		assertTrue(managerApp.printAllCinema());
		// 3. L’Applicazione Gestore Cinema chiede al Gestore Cinema di inserire l’id del cinema
		// 4. Il Gestore Cinema inserisce l’id del cinema desiderato
		// 5. L'Applicazione Gestore Cinema valida i dati immessi
		assertTrue(managerApp.hasCinema(cinema.getId()));
		// 6. L’Applicazione Gestore Cinema mostra al Gestore Cinema l’elenco delle sale
		// registrate nel cinema selezionato
		assertTrue(managerApp.printRooms(cinema.getId()));
		// 8. L’Applicazione Gestore Cinema chiede al Gestore Cinema come intende proseguire
		// 9b. Il Gestore Cinema comunica all’Applicazione Gestore Cinema di voler inserire una sala
		
		// Inizio dello scenario secondario SS2
	}
	
}

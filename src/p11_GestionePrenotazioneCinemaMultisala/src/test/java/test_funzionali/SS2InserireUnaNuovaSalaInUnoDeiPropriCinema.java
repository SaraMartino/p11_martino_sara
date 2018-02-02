package test_funzionali;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import sistema.*;

//Questo Scenario Secondario viene chiamato in seguito all'esecuzione dello use case UC6
public class SS2InserireUnaNuovaSalaInUnoDeiPropriCinema {

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
		
		// Questo Scenario Secondario viene chiamato in seguito all'esecuzione
		// dello use case UC6
		// Scenario alternativo 9b di UC6:
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
	}

	// Scenario Secondario: Inserire una nuova sala in uno dei propri cinema
	@Test
	public void SS3test1() {
		// 1. L'Applicazione Gestore Cinema chiede al Gestore Cinema i dati per la creazione
		// della nuova sala
		// 2. Il Gestore Cinema inserisce i dati richiesti
		// 3. L’Applicazione Gestore Cinema valida i dati inseriti
		// 5. Il Gestore Cinema conferma di voler procedere con l’inserimento
		// 6. L'Applicazione Gestore Cinema inserisce la nuova sala
		salaId = managerApp.inserisciNuovaSala(cinema.getId(), "Sala B", 10, 10, 10);
		assertNotEquals(-1, salaId);
		assertTrue(managerApp.hasRoom(cinema.getId(), salaId));
	}
	
	// Scenario alternativo 2a: Il Gestore Cinema decide di annullare l'operazione
	@Test
	public void SS3test2() {
		// 1. L'Applicazione Gestore Cinema chiede al Gestore Cinema i dati per la creazione
		// della nuova sala
		// 2a. Il Gestore Cinema decide di annullare l'operazione
		return;
	}
	
	// Scenario alternativo 3a: L'Applicazione Gestore Cinema non valida i dati inseriti
	@Test
	public void SS3test3() {
		// 1. L'Applicazione Gestore Cinema chiede al Gestore Cinema i dati per la creazione
		// della nuova sala
		// 2. Il Gestore Cinema inserisce i dati richiesti
		// 3a. L'Applicazione Gestore Cinema non valida i dati inseriti
		// (si intende valori non validi per numero di file, numero di posti per fila
		// e tempo attrezzaggio)
		// Andare al passo 1 dello scenario secondario
	}
	
	// Scenario alternativo 5a: Il Gestore Cinema non conferma l'operazione
	@Test
	public void SS3test4() {
		// 1. L'Applicazione Gestore Cinema chiede al Gestore Cinema i dati per la creazione
		// della nuova sala
		// 2. Il Gestore Cinema inserisce i dati richiesti
		// 3. L'Applicazione Gestore Cinema valida i dati inseriti
		// 4. L'Applicazione Gestore Cinema chiede conferma al Gestore Cinema
		// 5a. Il Gestore Cinema non conferma di voler eliminare la sala
		return;
	}
	
	// Scenario alternativo 6a: L’Applicazione Gestore Cinema non riesce ad inserire la nuova
	// sala perché esiste già una sala con lo stesso nome
	@Test
	public void SS3test5() {
		// Add a room in order to execute this test correctly
		managerApp.inserisciNuovaSala(cinema.getId(), "Sala A", 10, 10, 10);
		
		// 1. L'Applicazione Gestore Cinema chiede al Gestore Cinema i dati per la creazione
		// della nuova sala
		// 2. Il Gestore Cinema inserisce i dati richiesti
		// 3. L’Applicazione Gestore Cinema valida i dati inseriti
		// 5. Il Gestore Cinema conferma di voler procedere con l’inserimento
		// 6a. L'Applicazione Gestore Cinema non riesce ad inserire la nuova sala
		salaId = managerApp.inserisciNuovaSala(cinema.getId(), "Sala A", 10, 10, 10);
		assertEquals(-1, salaId);
		// Andare al passo 1 dello scenario secondario
	}

}

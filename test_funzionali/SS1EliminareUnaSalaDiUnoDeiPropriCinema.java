package test_funzionali;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import sistema.*;

// Questo Scenario Secondario viene chiamato in seguito all'esecuzione dello use case UC6
public class SS1EliminareUnaSalaDiUnoDeiPropriCinema {

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
		
		// Questo Scenario Secondario viene chiamato in seguito all'esecuzione dello
		// use case UC6
		// Scenario alternativo 9a di UC6:
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
	}

	// Scenario Secondario: Eliminare una sala di uno dei propri cinema
	@Test
	public void SS2test1() {
		// 1. L'Applicazione Gestore Cinema chiede al Gestore Cinema l’id della sala
		// 2. Il Gestore Cinema inserisce i dati richiesti
		// 3. L'Applicazione Gestore Cinema valida i dati inseriti
		assertTrue(managerApp.hasRoom(cinema.getId(), salaId));
		// 4. L'Applicazione Gestore Cinema chiede conferma al Gestore Cinema
		// 5. Il Gestore Cinema conferma di voler eliminare la sala
		// 6. L'Applicazione Gestore Cinema effettua la cancellazione della sala
		assertTrue(managerApp.eliminaSala(cinema.getId(), salaId));
		assertFalse(managerApp.hasRoom(cinema.getId(), salaId));
	}
	
	// Scenario alternativo 2a: Il Gestore Cinema decide di annullare l'operazione
	@Test
	public void SS2test2() {
		// 1. L'Applicazione Gestore Cinema richiede al Gestore Cinema l’id della sala
		// 2a. Il Gestore Cinema decide di annullare l'operazione
		return;
	}
	
	// Scenario alternativo 3a: L'Applicazione Gestore Cinema non valida i dati inseriti
	@Test
	public void SS2test3() {
		// 1. L'Applicazione Gestore Cinema chiede al Gestore Cinema l’id della sala
		// 2. Il Gestore Cinema inserisce i dati richiesti
		// 3a. L'Applicazione Gestore Cinema non valida i dati inseriti
		int wrongId = -10000;
		assertFalse(managerApp.hasRoom(cinema.getId(), wrongId));
		// Andare al passo 1 dello scenario secondario
	}
	
	// Scenario alternativo 5a: Il Gestore Cinema non conferma l'operazione
	@Test
	public void SS2test4() {
		// 1. L'Applicazione Gestore Cinema chiede al Gestore Cinema l’id della sala
		// 2. Il Gestore Cinema inserisce i dati richiesti
		// 3. L'Applicazione Gestore Cinema valida i dati inseriti
		assertTrue(managerApp.hasRoom(cinema.getId(), salaId));
		// 4. L'Applicazione Gestore Cinema chiede conferma al Gestore Cinema
		// 5a. Il Gestore Cinema non conferma di voler eliminare la sala
		return;
	}

}

package test_funzionali;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import sistema.*;

public class SS4AggiungereUnCinemaPerUnGestoreCinema {

	ApplicazioneAmministratoreSistema adminApp;
	Calendar adminBirthday;
	Calendar managerBirthday;

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
		
		// Questo Scenario Secondario viene chiamato in seguito all'esecuzione
		// dello use case UC13
		// Scenario alternativo 6a di UC13:
		// 2. L'Applicazione Amministratore Sistema chiede all'Amministratore Sistema
		// lo username del Gestore Cinema
		// 3. L'Amministratore Sistema inserisce i dati richiesti
		// 4. L'Applicazione Amministratore Sistema valida i dati inseriti
		assertNotNull(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P"));
		// 6a: L'Amministratore Sistema comunica di voler aggiungere un cinema alla
		// lista del Gestore Cinema
	}

	// Scenario Secondario: Aggiungere un cinema per un Gestore Cinema
	@Test
	public void SS4test1() {
		// 1. L'Applicazione Amministratore Sistema chiede di inserire il
		// nome e l’indirizzo del cinema
		// 2. L'Amministratore Sistema inserisce i dati richiesti
		// 3. L’Applicazione Amministratore Sistema valida i dati inseriti
		// (non implementato; ad esempio indirizzo esistente)
		// 5. L’Amministratore Sistema conferma di voler procedere con l’inserimento
		// 6. L'Applicazione Amministratore Sistema inserisce il nuovo cinema
		Cinema cinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		assertTrue(adminApp.addNewCinema("RSSLCU80A01D969P", cinema));
	}
	
	// Scenario alternativo 2a: L'Amministratore Sistema decide di annullare l'operazione
	@Test
	public void SS4test2() {
		// 1. L'Applicazione Amministratore Sistema chiede di inserire il
		// nome e l’indirizzo del cinema
		// 2a. Il Gestore Cinema decide di annullare l'operazione
		return;
	}
	
	// Scenario alternativo 3a: L'Applicazione Amministratore Sistema non valida
	// i dati inseriti
	@Test
	public void SS4test3() {
		// 1. L'Applicazione Amministratore Sistema chiede di inserire il
		// nome e l’indirizzo del cinema
		// 2. L'Amministratore Sistema inserisce i dati richiesti
		// 3a. L'Applicazione Amministratore Sistema non valida i dati inseriti
		// (valore non valido per l'indirizzo)
		// Andare al passo 1 dello scenario secondario
	}
	
	// Scenario alternativo 5a: L'Amministratore Sistema non conferma l'operazione
	@Test
	public void SS4test4() {
		// 1. L'Applicazione Amministratore Sistema chiede di inserire il
		// nome e l’indirizzo del cinema
		// 2. L'Amministratore Sistema inserisce i dati richiesti
		// 3. L’Applicazione Amministratore Sistema valida i dati inseriti
		// (non implementato; ad esempio indirizzo esistente)
		// 5a. L'Amministratore Sistema non conferma l'operazione
		return;
	}
	
	// Scenario alternativo 6a: L’Applicazione Amministratore Sistema non riesce ad inserire
	// il nuovo cinema perché esiste già un cinema con lo stesso nome e indirizzo
	@Test
	public void SS4test5() {
		// Add a cinema in order to execute this test correctly
		Cinema cinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		adminApp.addNewCinema("RSSLCU80A01D969P", cinema);
		
		// 1. L'Applicazione Amministratore Sistema chiede di inserire il
		// nome e l’indirizzo del cinema
		// 2. L'Amministratore Sistema inserisce i dati richiesti
		// 3. L’Applicazione Amministratore Sistema valida i dati inseriti
		// (non implementato; ad esempio indirizzo esistente)
		// 5. L’Amministratore Sistema conferma di voler procedere con l’inserimento
		// 6. L'Applicazione Amministratore Sistema non inserisce il nuovo cinema
		assertFalse(adminApp.addNewCinema("RSSLCU80A01D969P", cinema));
		// Andare al passo 1 dello scenario secondario
	}

}

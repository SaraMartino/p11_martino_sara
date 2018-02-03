package test_funzionali;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import sistema.*;

public class UC13ModificareUnGestoreCinema {
	
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
		// Registrazione gestore
		adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com");
	}

	// Scenario principale: Modificare un Gestore Cinema
	@Test
	public void UC13test1() {
		// 2. L'Applicazione Amministratore Sistema chiede all'Amministratore Sistema
		// lo username del Gestore Cinema
		// 3. L'Amministratore Sistema inserisce i dati richiesti
		// 4. L'Applicazione Amministratore Sistema valida i dati inseriti
		assertNotNull(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P"));
		// 6. L'Amministratore Sistema comunica di voler modificare password e/o email
		// 7. L'Applicazione Amministratore Sistema chiede di inserire le modifiche
		// 8. L’Amministratore Sistema inserisce i dati richiesti
		// 9. L'Applicazione Amministratore Sistema effettua le modifiche
		// (La validazione della mail non è implementata.
		// Questa validazione è lasciata ad una futura implementazione)
		assertTrue(adminApp.modifyEmailGestoreCinema("RSSLCU80A01D969P", "luca.rossi@alice.it"));
		assertTrue(adminApp.modifyPasswordGestoreCinema("RSSLCU80A01D969P", "1111"));
	}
	
	// Scenario alternativo 3a: L'Amministratore Sistema decide di annullare l’operazione
	@Test
	public void UC13test2() {
		// 2. L'Applicazione Amministratore Sistema chiede all'Amministratore Sistema
		// lo username del Gestore Cinema
		// di inserire i parametri registrazione Gestore Cinema
		// 3a. L'Amministratore Sistema decide di annullare l’operazione
		return;
	}
	
	// Scenario alternativo 4a: L'Applicazione Amministratore Sistema non valida i dati
	@Test
	public void UC13test3() {
		// 2. L'Applicazione Amministratore Sistema chiede all'Amministratore Sistema
		// lo username del Gestore Cinema
		// 3. L'Amministratore Sistema inserisce i dati richiesti
		// 4a. L'Applicazione Amministratore Sistema non valida i dati
		String wrongUsername = "wrongUsername";
		assertNull(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema(wrongUsername));
		// Andare al passo 2 dello scenario principale
	}
	
	// Scenario alternativo 8a: L'Amministratore Sistema decide di annullare l’operazione
	@Test
	public void UC13test4() {
		// 2. L'Applicazione Amministratore Sistema chiede all'Amministratore Sistema
		// lo username del Gestore Cinema
		// 3. L'Amministratore Sistema inserisce i dati richiesti
		// 4. L'Applicazione Amministratore Sistema valida i dati inseriti
		assertNotNull(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P"));
		// 6. L'Amministratore Sistema comunica di voler modificare password e/o email
		// 7. L'Applicazione Amministratore Sistema chiede di inserire le modifiche
		// 8a. L'Amministratore Sistema decide di annullare l’operazione
		return;
	}

	// Scenario alternativo 9a: L'Applicazione Amministratore Sistema non
	// effettua le modifiche
	@Test
	public void UC13test5() {
		// 2. L'Applicazione Amministratore Sistema chiede all'Amministratore Sistema
		// lo username del Gestore Cinema
		// 3. L'Amministratore Sistema inserisce i dati richiesti
		// 4. L'Applicazione Amministratore Sistema valida i dati inseriti
		assertNotNull(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P"));
		// 6. L'Amministratore Sistema comunica di voler modificare password e/o email
		// 7. L'Applicazione Amministratore Sistema chiede di inserire le modifiche
		// 8. L’Amministratore Sistema inserisce i dati richiesti
		// 9a. L'Applicazione Amministratore Sistema non effettua le modifiche
		// (La validazione della mail non è implementata.
		// Questa validazione è lasciata ad una futura implementazione)
		assertFalse(adminApp.modifyPasswordGestoreCinema("RSSLCU80A01D969P", "p a s s"));
		// Andare al passo 7 dello scenario principale
	}
	
	// Scenario alternativo 6a: L'Amministratore Sistema comunica di voler aggiungere
	// un cinema alla lista del Gestore Cinema
	// Questo scenario prevede l'esecuzione dello scenario secondario
	// SS4: Aggiungere un cinema per un Gestore Cinema
	// Il testing dello scenario secondario è implementato nel test case
	// 				SS4AggiungereUnCinemaPerUnGestoreCinema.java
	// per una maggiore chiarezza
	@Test
	public void UC13test6() {
		// 2. L'Applicazione Amministratore Sistema chiede all'Amministratore Sistema
		// lo username del Gestore Cinema
		// 3. L'Amministratore Sistema inserisce i dati richiesti
		// 4. L'Applicazione Amministratore Sistema valida i dati inseriti
		assertNotNull(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P"));
		// 6a: L'Amministratore Sistema comunica di voler aggiungere un cinema alla
		// lista del Gestore Cinema
		
		// Inizio dello scenario secondario SS4
	}
	
	// Scenario alternativo 6b: L'Amministratore Sistema comunica di voler rimuovere
	// un cinema dalla lista del Gestore Cinema
	// Questo scenario prevede l'esecuzione dello scenario secondario
	// SS5: Rimuovere un cinema di un Gestore Cinema
	// Il testing dello scenario secondario è implementato nel test case
	// 				SS5RimuovereUnCinemaDiUnGestoreCinema.java
	// per una maggiore chiarezza
	@Test
	public void UC13test7() {
		// 2. L'Applicazione Amministratore Sistema chiede all'Amministratore Sistema
		// lo username del Gestore Cinema
		// 3. L'Amministratore Sistema inserisce i dati richiesti
		// 4. L'Applicazione Amministratore Sistema valida i dati inseriti
		assertNotNull(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P"));
		// 6b: L'Amministratore Sistema comunica di voler rimuover un cinema dalla
		// lista del Gestore Cinema		
		
		// Inizio dello scenario secondario SS5
	}
	
}

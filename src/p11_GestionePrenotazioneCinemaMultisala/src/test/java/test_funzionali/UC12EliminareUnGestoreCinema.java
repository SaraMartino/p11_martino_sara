package test_funzionali;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import sistema.*;

public class UC12EliminareUnGestoreCinema {

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

	
	// Scenario principale: Eliminare un Gestore Cinema
	@Test
	public void UC12test1() {
		// 2. L'Applicazione Amministratore Sistema chiede all'Amministratore Sistema
		// lo username del Gestore Cinema
		// 3. L'Amministratore Sistema inserisce i dati richiesti
		// 4. L'Applicazione Amministratore Sistema valida i dati inseriti
		assertNotNull(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P"));
		// 6. L'Amministratore Sistema conferma di voler eliminare il profilo
		// 7. L'Applicazione Amministratore Sistema effettua la cancellazione del profilo
		assertTrue(adminApp.removeGestoreCinema("RSSLCU80A01D969P"));
	}
	
	// Scenario alternativo 3a: L'Amministratore Sistema decide di annullare l’operazione
	@Test
	public void UC12test2() {
		// 2. L'Applicazione Amministratore Sistema chiede all'Amministratore Sistema
		// lo username del Gestore Cinema
		// di inserire i parametri registrazione Gestore Cinema
		// 3a. L'Amministratore Sistema decide di annullare l’operazione
		return;
	}
	
	// Scenario alternativo 4a: L'Applicazione Amministratore Sistema non valida i dati
	@Test
	public void UC12test3() {
		// 2. L'Applicazione Amministratore Sistema chiede all'Amministratore Sistema
		// lo username del Gestore Cinema
		// 3. L'Amministratore Sistema inserisce i dati richiesti
		// 4a. L'Applicazione Amministratore Sistema non valida i dati
		String wrongUsername = "wrongUsername";
		assertNull(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema(wrongUsername));
		// Andare al passo 2 dello scenario principale
	}
	
	// Scenario alternativo 6a: L'Amministratore non conferma l'operazione
	@Test
	public void UC12test4() {
		// 2. L'Applicazione Amministratore Sistema chiede all'Amministratore Sistema
		// lo username del Gestore Cinema
		// 3. L'Amministratore Sistema inserisce i dati richiesti
		// 4. L'Applicazione Amministratore Sistema valida i dati inseriti
		assertNotNull(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P"));
		// 6. L'Amministratore Sistema non conferma di voler eliminare il profilo
		return;
	}

}

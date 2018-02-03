package test_funzionali;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import sistema.*;

public class UC10VisualizzareProfiliDeiGestoriCinema {

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

	// Scenario principale: Visualizzare profili dei Gestori Cinema
	@Test
	public void UC10test1() {
		
		// 2. L'Applicazione Amministratore Sistema mostra all’Amministratore Sistema
		// l’elenco dei profili dei Gestori Cinema
		assertTrue(adminApp.printAllManagers());
	}
	
	// Scenario alternativo 2a: L'Applicazione Amministratore Sistema non trova
	// Gestori Cinema registrati
	@Test
	public void UC10test2() {
		adminApp.removeGestoreCinema("RSSLCU80A01D969P");
		
		// 2a. L'Applicazione Amministratore Sistema non trova Gestori Cinema registrati
		assertFalse(adminApp.printAllManagers());
		return;
	}

}

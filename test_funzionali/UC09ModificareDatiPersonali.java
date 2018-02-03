package test_funzionali;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import sistema.*;

public class UC09ModificareDatiPersonali {

	ApplicazioneAmministratoreSistema adminApp;
	Calendar adminBirthday;

	@Before
	public void setUp() throws Exception {
		adminBirthday = Calendar.getInstance();
		adminBirthday.set(1975, 2, 5);
		adminApp = new ApplicazioneAmministratoreSistema("Anna",
				"Bianchi", "BNCNNA75C45D969Q", adminBirthday, "AnnaBianchi", "0000",
				"anna.bianchi@gmail.com");
		adminApp.login("AnnaBianchi", "0000");
	}

	// Scenario principale: l'Amministratore Sistema modifica i propri dati personali
	// (credenziali e/o email)
	@Test
	public void UC9test1() {
		// 2. L’Applicazione Amministratore Sistema chiede di inserire le modifiche
		// 3. L’Amministratore Sistema inserisce i dati richiesti
		// 4. L’Applicazione Amministratore Sistema effettua le modifiche
		// (La validazione della mail non è implementata.
		// Questa validazione è lasciata ad una futura implementazione)
		assertTrue(adminApp.changeEmail("anna.bianchi@alice.it"));
		assertTrue(adminApp.changeUsername("AnnaB"));
		assertTrue(adminApp.changePassword("1111"));
		adminApp.logout();
		assertTrue(adminApp.login("AnnaB", "1111"));
	}

	// Scenario secondario 3a: L’Amministratore Sistema decide di annullare l’operazione
	@Test
	public void UC9test2() {
		// 2. L’Applicazione Amministratore Sistema chiede di inserire le modifiche
		// 3a. L’Amministratore Sistema decide di annullare l’operazione
		return;
	}
	
	// Scenario secondario 4a: L’Applicazione Amministratore Sistema tenta di effettuare
	// le modifiche, ma non riesce poiché i dati non sono validi
	@Test
	public void UC9test3() {
		// 2. L’Applicazione Amministratore Sistema chiede di inserire le modifiche
		// 3. L’Amministratore Sistema inserisce i dati richiesti
		// 4. L’Applicazione Amministratore Sistema non effettua le modifiche
		// (La validazione della mail non è implementata.
		// Questa validazione è lasciata ad una futura implementazione)
		assertFalse(adminApp.changeUsername("AB"));
		assertFalse(adminApp.changePassword("p a s s"));
		adminApp.logout();
		assertFalse(adminApp.login("AB", "p a s s"));
	}
}

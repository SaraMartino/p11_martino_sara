package test_funzionali;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import sistema.*;

public class UC11RegistrareUnNuovoGestoreCinema {
	
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
	}

	// Scenario principale: Registrare un nuovo Gestore Cinema
	@Test
	public void UC11test1() {
		// 2. L'Applicazione Amministratore Sistema chiede all’Amministratore Sistema
		// di inserire i parametri registrazione Gestore Cinema
		// 3. L’Amministratore Sistema immette i dati richiesti
		// 4. L'Applicazione Amministratore Sistema registra il nuovo Gestore Cinema
		// (In questa prima implementazione, la generazione di username per i gestori viene semplificata
		// utilizzando come username il codice fiscale; poiché gli username devono essere unici,
		// non possono essere registrati due gestori con lo stesso codice fiscale.
		// Non viene effettuato nessun altro controllo sui dati inseriti
		// La generazione corretta di username unici e la validazione dei dati sono lasciate
		// ad una futura implementazione)
		assertTrue(adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com"));
	}
	
	// Scenario alternativo 3a: L'Amministratore Sistema decide di annullare l’operazione
	@Test
	public void UC11test2() {
		// 2. L'Applicazione Amministratore Sistema chiede all’Amministratore Sistema
		// di inserire i parametri registrazione Gestore Cinema
		// 3a. L'Amministratore Sistema decide di annullare l’operazione
		return;
	}

	// Scenario alternativo 4a: L'Applicazione Amministratore Sistema non registra
	// il nuovo Gestore Cinema
	@Test
	public void UC11test3() {
		// Registrazione precedente del gestore per eseguire correttamente lo scenario alternativo
		assertTrue(adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com"));
		
		// 2. L'Applicazione Amministratore Sistema chiede all’Amministratore Sistema
		// di inserire i parametri registrazione Gestore Cinema
		// 3. L’Amministratore Sistema immette i dati richiesti
		// 4a. L'Applicazione Amministratore Sistema non registra il nuovo Gestore Cinema
		// (In questa prima implementazione, la generazione di username per i gestori viene semplificata
		// utilizzando come username il codice fiscale; poiché gli username devono essere unici,
		// non possono essere registrati due gestori con lo stesso codice fiscale.
		// Non viene effettuato nessun altro controllo sui dati inseriti
		// La generazione corretta di username unici e la validazione dei dati sono lasciate
		// ad una futura implementazione)
		assertFalse(adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com"));
		// Andare al passo 2 dello scenario principale
	}

}

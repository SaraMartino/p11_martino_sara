package test_funzionali;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import sistema.ApplicazioneAmministratoreSistema;
import sistema.ApplicazioneGestoreCinema;

public class UC02Deautenticarsi {

	ApplicazioneAmministratoreSistema adminApp;
	ApplicazioneGestoreCinema managerApp;
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
		adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com");
		managerApp = new ApplicazioneGestoreCinema();
		managerApp.login("RSSLCU80A01D969P", "0000");
	}

	// Scenario principale: l'amministratore effettua il logout
	@Test
	public void UC2AmministratoreSistemaTest1() {
		assertTrue(adminApp.logout());
		assertFalse(adminApp.isAdminLogged());
	}
	
	// Scenario alternativo 3b: L’attore annulla l’operazione 
	@Test
	public void UC2AmministratoreSistemaTest2() {
		assertTrue(adminApp.isAdminLogged());
	}
	
	// Scenario principale: il gestore cinema effettua il logout
	@Test
	public void UC2GestoreCinemaTest1() {
		assertTrue(managerApp.logout());
		assertFalse(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P").isLogged());
	}
	
	// Scenario alternativo 3b: L’attore annulla l’operazione 
	@Test
	public void UC2GestoreCinemaTest2() {
		assertTrue(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P").isLogged());
	}

}

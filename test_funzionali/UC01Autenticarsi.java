package test_funzionali;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import sistema.*;

public class UC01Autenticarsi {

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
		adminApp.logout();
		managerApp = new ApplicazioneGestoreCinema();
	}

	// Scenario principale: l'amministratore effettua il login
	@Test
	public void UC1AmministratoreSistemaTest1() {
		assertTrue(adminApp.login("AnnaBianchi", "0000"));
		assertTrue(adminApp.isAdminLogged());
	}
	
	// Scenario alternativo 3a: L’attore comunica al sistema di aver dimenticato
	// le credenziali
	@Test
	public void UC1AmministratoreSistemaTest2() {
		adminApp.sendEmail("anna.bianchi@gmail.com", "AnnaBianchi 0000");
		assertFalse(adminApp.isAdminLogged());
	}
	
	// Scenario alternativo 3b: L’attore comunica al sistema di voler annullare
	// l’operazione
	@Test
	public void UC1AmministratoreSistemaTest3() {
		assertFalse(adminApp.isAdminLogged());
	}
	
	// Scenario alternativo 4a: Il sistema non valida i dati inseriti
	@Test
	public void UC1AmministratoreSistemaTest4() {
		assertFalse(adminApp.login("LucaRossi", "1234"));
		assertFalse(adminApp.isAdminLogged());
	}
	
	// Scenario principale: il gestore cinema effettua il login
	@Test
	public void UC1GestoreCinemaTest1() {
		assertTrue(managerApp.login("RSSLCU80A01D969P", "0000"));
		assertTrue(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P").isLogged());
	}
	
	// Scenario alternativo 3a: L’attore comunica al sistema di aver dimenticato
	// le credenziali
	@Test
	public void UC1GestoreCinemaTest2() {
		managerApp.sendEmail("luca.rossi@gmail.com", "RSSLCU80A01D969P 0000");
		assertFalse(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P").isLogged());
	}
	
	// Scenario alternativo 3b: L’attore comunica al sistema di voler annullare
	// l’operazione
	@Test
	public void UC1GestoreCinemaTest3() {
		assertFalse(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P").isLogged());
	}
	
	// Scenario alternativo 4a: Il sistema non valida i dati inseriti
	@Test
	public void UC1GestoreCinemaTest4() {
		// Invalid credentials
		managerApp.login("WrongUser", "WrongPassword");
		assertFalse(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P").isLogged());
	}

}

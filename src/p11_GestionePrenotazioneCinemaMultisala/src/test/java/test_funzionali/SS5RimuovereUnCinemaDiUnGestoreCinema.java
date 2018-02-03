package test_funzionali;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import sistema.*;

public class SS5RimuovereUnCinemaDiUnGestoreCinema {
	
	ApplicazioneAmministratoreSistema adminApp;
	Calendar adminBirthday;
	Calendar managerBirthday;
	Cinema cinema;

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
		// Registrazione di un nuovo gestore
		adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				managerBirthday, "luca.rossi@gmail.com");
		cinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		adminApp.addNewCinema("RSSLCU80A01D969P", cinema);
		
		// Questo Scenario Secondario viene chiamato in seguito all'esecuzione
		// dello use case UC13
		// Scenario alternativo 6b di UC13:
		// 2. L'Applicazione Amministratore Sistema chiede all'Amministratore Sistema
		// lo username del Gestore Cinema
		// 3. L'Amministratore Sistema inserisce i dati richiesti
		// 4. L'Applicazione Amministratore Sistema valida i dati inseriti
		assertNotNull(ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema("RSSLCU80A01D969P"));
		// 6a: L'Amministratore Sistema comunica di voler rimuovere un cinema dalla
		// lista del Gestore Cinema
	}
	
	// Scenario Secondario: Rimuovere un cinema di un Gestore Cinema
	@Test
	public void SS5test1() {
		// 1. L'Applicazione Amministratore Sistema chiede di inserire l’id del
		// cinema da eliminare
		// 2. L’Amministratore Sistema inserisce i dati richiesti
		// 3. L'Applicazione Amministratore Sistema chiede conferma
		// 4. L’Amministratore Sistema conferma di voler eliminare il cinema
		// 5. L'Applicazione Amministratore Sistema effettua la cancellazione
		assertTrue(adminApp.removeCinema("RSSLCU80A01D969P", cinema.getId()));
	}
	
	// Scenario alternativo 2a: L'Amministratore Sistema decide di annullare l'operazione
	@Test
	public void SS5test2() {
		// 1. L'Applicazione Amministratore Sistema chiede di inserire l’id del
		// cinema da eliminare
		// 2a. Il Gestore Cinema decide di annullare l'operazione
		return;
	}
	
	// Scenario alternativo 4a: L'Amministratore Sistema non conferma l'operazione
	@Test
	public void SS4test3() {
		// 1. L'Applicazione Amministratore Sistema chiede di inserire l’id del
		// cinema da eliminare
		// 2. L’Amministratore Sistema inserisce i dati richiesti
		// 3. L'Applicazione Amministratore Sistema chiede conferma
		// 5a. L'Amministratore Sistema non conferma l'operazione
		return;
	}
	
	// Scenario alternativo 5a: L’Applicazione Amministratore Sistema non riesce
	// a cancellare il cinema perché l'id non è valido
	@Test
	public void SS5test4() {
		// 1. L'Applicazione Amministratore Sistema chiede di inserire l’id del
		// cinema da eliminare
		// 2. L’Amministratore Sistema inserisce i dati richiesti
		// 3. L'Applicazione Amministratore Sistema chiede conferma
		// 4. L’Amministratore Sistema conferma di voler eliminare il cinema
		// 5a. L'Applicazione Amministratore Sistema non effettua la cancellazione
		int wrongId = -12234;
		assertFalse(adminApp.removeCinema("RSSLCU80A01D969P", wrongId));
		// Andare al passo 1 dello scenario secondario
	}

}

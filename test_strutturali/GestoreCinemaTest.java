package test_strutturali;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

import sistema.*;

public class GestoreCinemaTest {
	
	GestoreCinema g;
	Calendar birthday;

	@Before
	public void setUp() throws Exception {
		birthday = Calendar.getInstance();
		birthday.set(1980, 0, 1);
		g = new GestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P", birthday,
				"RSSLCU80A01D969P", "0000", "luca.rossi@gmail.com");
	}

	@Test
	public void testConstructor() {
		assertNotNull(g);
		assertEquals("RSSLCU80A01D969P", g.getUsername());
		assertEquals("0000", g.getPassword());
		assertEquals("luca.rossi@gmail.com", g.getEmail());
		assertFalse(g.isLogged());
	}
	
	@Test
	public void testSetAutenticatoAndIsLogged() {
		assertFalse(g.isLogged());
		g.setAutenticato(true);
		assertTrue(g.isLogged());
	}
	
	@Test
	public void testSetEmailAndSetPassword() {
		g.setEmail("luca.rossi@alice.it");
		assertEquals("luca.rossi@alice.it", g.getEmail());
		g.setPassword("1111");
		assertEquals("1111", g.getPassword());
	}
	
	@Test
	public void testPrintProfilo() {
		
		Cinema mockedCinema = mock(Cinema.class);
		when(mockedCinema.getNome()).thenReturn("Odeon");
		when(mockedCinema.getIndirizzo()).thenReturn("Corso Buenos Aires, 83, 16129 Genova");

		Calendar c = Calendar.getInstance();
		c.set(1975, 2, 5);
		ApplicazioneAmministratoreSistema adminApp = new ApplicazioneAmministratoreSistema("Anna",
				"Bianchi", "BNCNNA75C45D969Q", c, "AnnaBianchi", "0000", "anna.bianchi@gmail.com");
		assertTrue(adminApp.login("AnnaBianchi", "0000"));
		adminApp.resetApplication();
		g.printProfilo();
		adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P", birthday, "luca.rossi@gmail.com");
		g.printProfilo();
		adminApp.addNewCinema(g.getUsername(), mockedCinema);
		g.printProfilo();
	}

}

package test_strutturali;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import sistema.*;

public class ApplicazioneGestoreCinemaTest {

	ApplicazioneGestoreCinema app;
	Calendar birthday;
	GestoreCinema gestore;
	ApplicazioneAmministratoreSistema adminApp;
		
	@Before
	public void setUp() throws Exception {
		app = new ApplicazioneGestoreCinema();
		birthday = Calendar.getInstance();
		birthday.set(1980, 0, 1);
		gestore = new GestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P",
				birthday, "RSSLCU80A01D969P", "0000", "luca.rossi@gmail.com");
		Calendar c = Calendar.getInstance();
		c.set(1975, 2, 5);
		adminApp = new ApplicazioneAmministratoreSistema("Anna",
				"Bianchi", "BNCNNA75C45D969Q", c, "AnnaBianchi", "0000", "anna.bianchi@gmail.com");
		assertTrue(adminApp.login("AnnaBianchi", "0000"));
		adminApp.resetApplication();
	}
	
	@Test
	public void testConstructor() {
		assertNotNull(app);
	}
	
	@Test
	public void testLoginAndLogout() {

		assertFalse(app.login("RSSLCU80A01D969P", "0000"));
		assertFalse(app.logout());

		// Registration of two GestoreCinema
		assertTrue(adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P", birthday, "luca.rossi@gmail.com"));
		assertTrue(adminApp.registraNuovoGestoreCinema("Marco", "Verdi", "VRDMRC80A01D969X", birthday, "luca.rossi@gmail.com"));
	
		assertFalse(app.login(null, null));
		assertFalse(app.login("RSSLCU80A01D969P", null));
		assertFalse(app.login("RSSLCU80A01D969P", "WrongPassword"));

		assertTrue(app.login("RSSLCU80A01D969P", "0000"));
		assertFalse(app.login("RSSLCU80A01D969P", "0000"));
		assertTrue(app.logout());
		assertFalse(app.logout());
		assertTrue(app.login("VRDMRC80A01D969X", "0000"));
		assertTrue(app.logout());
		
		assertTrue(adminApp.removeGestoreCinema("VRDMRC80A01D969X"));
		assertTrue(adminApp.registraNuovoGestoreCinema("Marco", "Verdi", "VRDMRC80A01D969X", birthday, "luca.rossi@gmail.com"));
		assertTrue(app.login("VRDMRC80A01D969X", "0000"));
				
		// Remotion of the GestoreCinema
		assertTrue(adminApp.removeGestoreCinema("VRDMRC80A01D969X"));
		assertFalse(app.logout());
	}

	@Test
	public void testInserisciNuovaSalaAndEliminaSala() {		
		assertEquals(-1, app.inserisciNuovaSala(-1, "Sala A", 10, 10, 10));
		assertFalse(app.eliminaSala(-1, -1));
		
		// Registration of the GestoreCinema
		adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P", birthday, "luca.rossi@gmail.com");
		app.login("RSSLCU80A01D969P", "0000");
		assertEquals(-1, app.inserisciNuovaSala(-1, "Sala A", 10, 10, 10));
		assertFalse(app.eliminaSala(-1, -1));
		app.logout();
		assertEquals(-1, app.inserisciNuovaSala(-1, "Sala A", 10, 10, 10));
		assertFalse(app.eliminaSala(-1, -1));
		
		app.login("RSSLCU80A01D969P", "0000");
		Cinema cinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		assertTrue(adminApp.addNewCinema("RSSLCU80A01D969P", cinema));
		assertEquals(-1, app.inserisciNuovaSala(-1, "Sala A", 10, 10, 10));
		int id = app.inserisciNuovaSala(cinema.getId(), "Sala A", 10, 10, 10);
		assertNotEquals(-1, id);
		assertFalse(app.eliminaSala(-1, id));
		assertTrue(app.eliminaSala(cinema.getId(), id));
	}
	
	@Test
	public void testPrint() {
		ArrayList<Sala> rooms = new ArrayList<Sala>();
		Cinema cinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		rooms.add(new Sala("Sala A", 10, 10, 10, cinema));
		app.printRoomsFromList(rooms);
		
		assertFalse(app.printProfilo());
		assertFalse(app.printRooms(-1));
		
		// Registration of the GestoreCinema
		adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P", birthday, "luca.rossi@gmail.com");
		assertFalse(app.printProfilo());
		assertFalse(app.printRooms(-1));

		app.login("RSSLCU80A01D969P", "0000");
		assertTrue(app.printProfilo());
		assertFalse(app.printRooms(-1));
		adminApp.removeGestoreCinema("RSSLCU80A01D969P");
		adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P", birthday, "luca.rossi@gmail.com");
		assertFalse(app.logout());
		app.login("RSSLCU80A01D969P", "0000");
		
		adminApp.addNewCinema("RSSLCU80A01D969P", cinema);
		assertTrue(app.printProfilo());
		assertFalse(app.printRooms(-1));
		assertFalse(app.printRooms(cinema.getId()));
		
		app.logout();
		assertFalse(app.printProfilo());
		assertFalse(app.printRooms(-1));		
	}
	
	@Test
	public void testPrintAllCinemaAndAllShows() {
		assertFalse(app.printAllCinema());
		assertFalse(app.printAllShows(-1, -1));
		// Registration of the GestoreCinema
		adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P", birthday, "luca.rossi@gmail.com");
		app.login("RSSLCU80A01D969P", "0000");
		assertFalse(app.printAllCinema());
		assertFalse(app.printAllShows(-1, -1));
		
		Cinema cinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		adminApp.addNewCinema("RSSLCU80A01D969P", cinema);
		assertTrue(app.printAllCinema());
		assertFalse(app.printAllShows(cinema.getId(), -1));
		assertFalse(app.printAllShows(-1, -1));
		
		app.logout();
		assertFalse(app.printAllCinema());
		assertFalse(app.printAllShows(-1, -1));
		
	}
		
	@Test
	public void testAddShowAndVerifyRoomsAvailability() {
		Spettacolo mockedShow = mock(Spettacolo.class);
		assertNull(app.verifyRoomsAvailability(-1, mockedShow, 5));
		assertFalse(app.addShows(-1, mockedShow, 5, -1));
		
		// Registration of the GestoreCinema
		assertTrue(adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P", birthday, "luca.rossi@gmail.com"));
		assertNull(app.verifyRoomsAvailability(-1, mockedShow, 5));
		assertFalse(app.addShows(-1, mockedShow, 5, -1));
		
		app.login("RSSLCU80A01D969P", "0000");
		assertNull(app.verifyRoomsAvailability(-1, mockedShow, 5));
		assertFalse(app.addShows(-1, mockedShow, 5, -1));
		
		Cinema cinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		adminApp.addNewCinema("RSSLCU80A01D969P", cinema);
		assertNull(app.verifyRoomsAvailability(-1, mockedShow, 5));
		assertFalse(app.addShows(-1, mockedShow, 5, -1));
		assertNotNull(app.verifyRoomsAvailability(cinema.getId(), mockedShow, 5));
		assertFalse(app.addShows(cinema.getId(), mockedShow, 0, -1));
		// Returns false because there are no rooms registered for this cinema
		assertFalse(app.addShows(cinema.getId(), mockedShow, 5, -1));
		
		app.logout();
		assertNull(app.verifyRoomsAvailability(-1, mockedShow, 5));
		assertFalse(app.addShows(-1, mockedShow, 5, -1));
	}
	
	@Test
	public void testSendEmail() {
		app.sendEmail("luca.rossi@gmail.com", "params");
	}
	
	@Test
	public void testHasCinemaAndHasRoom() {
		assertFalse(app.hasCinema(-1));
		assertFalse(app.hasRoom(-1, -1));

		// Registration of the GestoreCinema
		assertTrue(adminApp.registraNuovoGestoreCinema("Luca", "Rossi", "RSSLCU80A01D969P", birthday, "luca.rossi@gmail.com"));
		
		app.login("RSSLCU80A01D969P", "0000");
		assertFalse(app.hasCinema(-1));
		assertFalse(app.hasRoom(-1, -1));

		Cinema cinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		adminApp.addNewCinema("RSSLCU80A01D969P", cinema);
		assertTrue(app.hasCinema(cinema.getId()));
		assertFalse(app.hasCinema(-1));
		assertFalse(app.hasRoom(cinema.getId(), -1));
		assertFalse(app.hasRoom(-1, -1));
		
		app.logout();
		assertFalse(app.hasCinema(-1));
		assertFalse(app.hasRoom(-1, -1));
	}

}

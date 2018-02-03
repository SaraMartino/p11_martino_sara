package test_strutturali;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sistema.*;

public class CinemaTest {
	
	Cinema c;
	static Calendar date;
	static Spettacolo s;
	static Film mockedFilm;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		date = Calendar.getInstance();
		date.set(2018, 6, 1, 10, 00);
		date.add(Calendar.DAY_OF_MONTH, 5);
		mockedFilm = mock(Film.class);
		when(mockedFilm.getDurata()).thenReturn(50);
		s = new Spettacolo(mockedFilm, date, 10.0f);
	}

	@Before
	public void setUp() throws Exception {
		c = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
	}

	@Test
	public void testConstructor() {
		assertNotNull(c);
		assertEquals("Odeon", c.getNome());
		assertEquals("Corso Buenos Aires, 83, 16129 Genova", c.getIndirizzo());
	}
	
	@Test
	public void testSetAndGetId() {
		c.setId(0);
		assertEquals(0, c.getId());
	}
	
	@Test
	public void testHasSala() {
		int id = c.addSala("Sala A", 10, 10, 10);
		assertFalse(c.hasSala(-1));
		assertTrue(c.hasSala(id));
	}
	
	@Test
	public void testAddSala() {
		assertNotEquals(-1, c.addSala("Sala A", 10, 10, 10));
		assertEquals(-1, c.addSala("Sala A", 20, 18, 15));
		assertNotEquals(-1, c.addSala("Sala B", 20, 18, 15));
	}
	
	@Test
	public void testRemoveSala() {
		int id = c.addSala("Sala A", 10, 10, 10);
		assertTrue(c.removeSala(id));
		assertFalse(c.removeSala(id));
	}
	
	@Test
	public void testPrintAllRoomsAndShows() {
		c.printAllRooms();
		int id = c.addSala("Sala A", 10, 10, 10);
		c.printAllRooms();
		
		assertFalse(c.printAllShows(-1));
		// It returns false because there are no scheduled shows
		assertFalse(c.printAllShows(id));
	}
	
	@Test
	public void testAddShowAndVerifyRoomsAvailability() {
		assertTrue(c.verifyRoomsAvailability(s, 2).isEmpty());
		int id = c.addSala("Sala A", 10, 10, 10);
		assertFalse(c.verifyRoomsAvailability(s, 2).isEmpty());
		c.addSala("Sala B", 20, 18, 15);
		assertTrue(c.addShows(s, 2, id));
		assertFalse(c.verifyRoomsAvailability(s, 2).isEmpty());
		assertFalse(c.addShows(s, 2, id));
		assertFalse(c.addShows(s, 2, -120));
		assertFalse(c.addShows(s, 0, id));
	}
	
	@Test
	public void testEquals() {
		assertTrue(c.equals(c));
		assertFalse(c.equals(null));
		String string = "";
		assertFalse(c.equals(string));
		Cinema newCinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		assertTrue(c.equals(newCinema));
		newCinema = new Cinema("Corallo", "Via Innocenzo IV, 13, 16128 Genova");
		assertFalse(c.equals(newCinema));
		newCinema = new Cinema("Odeon", "Via Innocenzo IV, 13, 16128 Genova");
		assertFalse(c.equals(newCinema));
		
	}

}

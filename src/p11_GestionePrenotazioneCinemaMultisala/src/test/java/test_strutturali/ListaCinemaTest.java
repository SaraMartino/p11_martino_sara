package test_strutturali;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sistema.*;

public class ListaCinemaTest {

	ListaCinema listaCinema;

	@Before
	public void setUp() throws Exception {
		listaCinema = new ListaCinema();
	}

	@Test
	public void testConstructor() {
		assertNotNull(listaCinema);
	}
	
	@Test
	public void testAddNewCinema() {
		Cinema cinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		assertTrue(listaCinema.addNewCinema(cinema));
		assertFalse(listaCinema.addNewCinema(cinema));
	}
	
	@Test
	public void testRemoveCinema() {
		Cinema cinema = new Cinema("Odeon", "Corso Buenos Aires, 83, 16129 Genova");
		listaCinema.addNewCinema(cinema);
		assertFalse(listaCinema.removeCinema(-3));
		assertFalse(listaCinema.removeCinema(2000000));
		assertTrue(listaCinema.removeCinema(cinema.getId()));
	}
	
	@Test
	public void testGetIterator() {
		assertTrue(listaCinema.getIterator() instanceof ListaCinemaIterator);
	}

}

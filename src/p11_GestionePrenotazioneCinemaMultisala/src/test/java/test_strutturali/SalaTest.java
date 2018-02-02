package test_strutturali;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sistema.*;

public class SalaTest {

	Sala s;
	static Calendar date;
	static Spettacolo spettacolo;
	static Film mockedFilm;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		date = Calendar.getInstance();
		date.set(2018, 6, 1, 10, 00);
		date.add(Calendar.DAY_OF_MONTH, 5);
		mockedFilm = mock(Film.class);
		when(mockedFilm.getDurata()).thenReturn(50);
		spettacolo = new Spettacolo(mockedFilm, date, 10.0f);
	}

	@Before
	public void setUp() throws Exception {
		Cinema mockedCinema = mock(Cinema.class);
		when(mockedCinema.getId()).thenReturn(1);
		s = new Sala("Sala A", 10, 10, 10, mockedCinema);
	}

	@Test
	public void testConstructor() {
		assertNotNull(s);
		assertEquals("Sala A", s.getNome());
		assertEquals(10*10, s.getCapacity());
		assertEquals(10, s.getTempoAttrezzaggio());
		assertEquals(1, s.getCinemaId());
	}
	
	@Test
	public void testSetAndGetId() {
		s.setId(5);
		assertEquals(5, s.getId());
	}
	
	@Test
	public void testAddShowAndVerifyAvailability() {
		s.setId(5);
		assertTrue(s.verifyAvailability(spettacolo, 2));
		assertFalse(s.addShow(spettacolo, 2));
		spettacolo.setSala(s);
		// Add a show at 10.00
		assertTrue(s.addShow(spettacolo, 2));
		
		Calendar newDate = (Calendar) date.clone();
		newDate.set(Calendar.HOUR_OF_DAY, 12);
		Spettacolo newSpettacolo = new Spettacolo(mockedFilm, newDate, 10.0f);
		assertTrue(s.verifyAvailability(newSpettacolo, 2));
		newSpettacolo.setSala(s);
		// Add a show at 12.00
		assertTrue(s.addShow(newSpettacolo, 2));
		// Try to add a show at 12.00
		// It returns false because there is already a show at the same hour and minutes
		assertFalse(s.addShow(newSpettacolo, 2));
		
		newDate.add(Calendar.MINUTE, 30);
		newSpettacolo = new Spettacolo(mockedFilm, newDate, 10.0f);
		newSpettacolo.setSala(s);
		// Try to add a show at 12.30 (the duration of the show is 60 min)
		// It returns false because newSpettacolo starts when the previous show is
		// not already finished
		assertFalse(s.verifyAvailability(newSpettacolo, 2));
		
		newDate.set(Calendar.HOUR_OF_DAY, 18);
		newDate.set(Calendar.MINUTE, 0);
		newSpettacolo = new Spettacolo(mockedFilm, newDate, 10.0f);
		newSpettacolo.setSala(s);
		// Add a show at 18.00
		assertTrue(s.addShow(newSpettacolo, 2));
		
		newDate.set(Calendar.HOUR_OF_DAY, 17);
		newDate.set(Calendar.MINUTE, 30);
		newSpettacolo = new Spettacolo(mockedFilm, newDate, 10.0f);
		// Try to add a show at 17.30 (the duration of the show is 60 min)
		// It returns false because newSpettacolo ends when the previous show is
		// already started
		assertFalse(s.verifyAvailability(newSpettacolo, 2));
		
		newDate.set(Calendar.HOUR_OF_DAY, 12);
		newDate.set(Calendar.MINUTE, 30);
		newSpettacolo = new Spettacolo(mockedFilm, newDate, 10.0f);
		// Try to add a show at 12.30 (the duration of the show is 60 min)
		// It returns false because newSpettacolo ends when the previous show is
		// already started
		assertFalse(s.verifyAvailability(newSpettacolo, 2));
		
		newDate.set(Calendar.HOUR_OF_DAY, 16);
		newDate.set(Calendar.MINUTE, 0);
		newSpettacolo = new Spettacolo(mockedFilm, newDate, 10.0f);
		newSpettacolo.setSala(s);
		// Add a show at 16.00
		assertTrue(s.addShow(newSpettacolo, 2));
	}
	
	@Test
	public void testPrint() {
		s.print();
		assertFalse(s.printAllShows());
		
		s.setId(5);
		Spettacolo show = new Spettacolo(mockedFilm, date, 10.0f);
		show.setSala(s);
		// Add a show at 10.00
		assertTrue(s.addShow(show, 2));
		assertTrue(s.printAllShows());
	}

}

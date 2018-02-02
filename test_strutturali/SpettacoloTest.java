package test_strutturali;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sistema.*;

public class SpettacoloTest {
	
	Spettacolo s;
	static Calendar startDate;
	static Sala mockedSala;
	static Film mockedFilm;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		startDate = Calendar.getInstance();
		startDate.set(2018, 6, 1, 10, 00);
		startDate.add(Calendar.DAY_OF_MONTH, 5);
		mockedFilm = mock(Film.class);
		when(mockedFilm.getId()).thenReturn("ABC");
		when(mockedFilm.getDurata()).thenReturn(120);
		mockedSala = mock(Sala.class);
		when(mockedSala.getId()).thenReturn(1);
		when(mockedSala.getCapacity()).thenReturn(100);
		when(mockedSala.getTempoAttrezzaggio()).thenReturn(10);
		when(mockedSala.getNome()).thenReturn("Sala");
		when(mockedSala.getCinemaId()).thenReturn(5);
	}
	
	@Before
	public void setUp() throws Exception {
		s = new Spettacolo(mockedFilm, startDate, 10.0f);
	}

	@Test
	public void testConstructor() {
		assertNotNull(s);
		assertEquals("ABC", s.getFilmId());
		assertEquals(startDate, s.getDataInizio());
		assertEquals(10.0f, s.getPrezzoBase(), 0.0);
	}
	
	@Test
	public void testSetAndGetIdEvento() {
		s.setIdEvento(1);
		assertEquals(1, s.getIdEvento());
	}
	
	@Test
	public void testSetSala() {
		assertEquals(-1, s.getSalaId());
		assertEquals(-1, s.getCinemaId());
		s.setSala(mockedSala);
		assertEquals(1, s.getSalaId());
		assertEquals(5, s.getCinemaId());
	}

	@Test
	public void testGetDurata() {
		s.setSala(mockedSala);
		assertEquals(mockedSala.getTempoAttrezzaggio() + mockedFilm.getDurata(), s.getDurata());
	}
	
	@Test
	public void testGetNextSpettacolo() {
		Calendar nextDate = (Calendar) startDate.clone();
		nextDate.add(Calendar.DAY_OF_MONTH, 1);
		assertEquals(nextDate, s.getNextSpettacolo(1).getDataInizio());
	}
	
	@Test
	public void testGetKey() {
		assertEquals(startDate.get(Calendar.YEAR) + "/" + startDate.get(Calendar.MONTH) + "/"
				+ startDate.get(Calendar.DAY_OF_MONTH), s.getKey());
	}

	@Test
	public void testCompareTo() {
		Spettacolo newS = new Spettacolo(mockedFilm, startDate, 10.0f);
		assertEquals(0, s.compareTo(newS));
	}
	
	@Test
	public void testEquals() {
		s.setSala(mockedSala);
		assertFalse(s.equals(null));
		assertTrue(s.equals(s));
		assertFalse(s.equals("String"));
		
		Film newMockedFilm = mock(Film.class);
		when(newMockedFilm.getId()).thenReturn("ABC");
		when(newMockedFilm.getDurata()).thenReturn(120);
		Sala newMockedSala = mock(Sala.class);
		when(newMockedSala.getId()).thenReturn(1);
		when(newMockedSala.getCapacity()).thenReturn(100);
		when(newMockedSala.getTempoAttrezzaggio()).thenReturn(10);
		when(newMockedSala.getNome()).thenReturn("Sala");
		when(newMockedSala.getCinemaId()).thenReturn(5);
		Calendar newStartDate = (Calendar) startDate.clone();
		Spettacolo newS = new Spettacolo(newMockedFilm, newStartDate, 10.0f);
		newS.setSala(newMockedSala);
		
		assertTrue(s.equals(newS));
		when(newMockedSala.getCinemaId()).thenReturn(10);
		assertFalse(s.equals(newS));
		when(newMockedSala.getId()).thenReturn(10);
		assertFalse(s.equals(newS));
		newS.setIdEvento(-200);
		assertFalse(s.equals(newS));
		
		
	}
	
	@Test
	public void testPrint() {
		s.setSala(mockedSala);
		s.print();
	}

}

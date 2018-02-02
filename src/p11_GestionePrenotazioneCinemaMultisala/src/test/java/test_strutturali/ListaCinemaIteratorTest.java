package test_strutturali;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sistema.*;

public class ListaCinemaIteratorTest {

	ListaCinemaIterator i;
	static LinkedList<Cinema> listaCinema;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		listaCinema = new LinkedList<Cinema>();
		Cinema mockedCinema = mock(Cinema.class);
		listaCinema.add(mockedCinema);
	}

	@Before
	public void setUp() throws Exception {
		i = new ListaCinemaIterator(listaCinema);
	}

	@Test
	public void testConstructor() {
		assertNotNull(i);
	}
	
	@Test
	public void testHasNextAndNext() {
		assertTrue(i.hasNext());
		assertNotNull(i.next());
		assertFalse(i.hasNext());
		assertNull(i.next());
	}

}

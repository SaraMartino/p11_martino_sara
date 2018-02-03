package test_strutturali;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sistema.*;

public class PersonaTest {

	Persona p;
	static Calendar birthday;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		birthday = Calendar.getInstance();
		birthday.set(1980, 0, 1);
	}

	@Before
	public void setUp() throws Exception {
		p = new Persona("Luca", "Rossi", "RSSLCU80A01D969P", birthday);
	}

	@Test
	public void testConstructor() {
		assertNotNull(p);
		assertEquals("Luca", p.getNome());
		assertEquals("Rossi", p.getCognome());
		assertEquals("RSSLCU80A01D969P", p.getCodiceFiscale());
		assertEquals(birthday, p.getDataNascita());
	}
	
	@Test
	public void testPrint() {
		p.print();
		p = new Persona("Luca", "Rossi", "RSSLCU80A01D969P", null);
		p.print();
	}

}

package test_strutturali;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sistema.*;

public class AmministratoreSistemaTest {

	AmministratoreSistema a;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Calendar birthday = Calendar.getInstance();
		birthday.set(1980, 0, 1);
		a = new AmministratoreSistema("Luca", "Rossi", "RSSLCU80A01D969P", birthday,
				"RSSLCU80A01D969P", "0000", "luca.rossi@gmail.com");
	}

	@Test
	public void testConstructor() {
		assertNotNull(a);
		assertEquals("RSSLCU80A01D969P", a.getUsername());
		assertEquals("0000", a.getPassword());
		assertEquals("luca.rossi@gmail.com", a.getEmail());
		assertFalse(a.isLogged());
	}
	
	@Test
	public void testSetAutenticatoAndIsLogged() {
		assertFalse(a.isLogged());
		a.setAutenticato(true);
		assertTrue(a.isLogged());
	}
	
	@Test
	public void testSetAndGet() {
		a.setUsername("Admin");
		assertEquals("Admin", a.getUsername());
		a.setPassword("1111");
		assertEquals("1111", a.getPassword());
		a.setEmail("admin@gmail.com");
		assertEquals("admin@gmail.com", a.getEmail());
	}
	
	@Test
	public void testPrint() {
		a.printProfilo();
	}
	
}

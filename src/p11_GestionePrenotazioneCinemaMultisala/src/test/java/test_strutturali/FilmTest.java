package test_strutturali;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import sistema.*;

public class FilmTest {

	Film f;
	
	@Before
	public void setUp() throws Exception {
		ArrayList<String> attori = new ArrayList<String>();
		attori.add("Roberto Benigni");
		attori.add("Nicoletta Braschi");
		attori.add("Giorgio Cantarini");
		attori.add("Giustino Durano");
		ArrayList<String> genere = new ArrayList<String>();
		genere.add("Drammatico");
		genere.add("Commedia");
		String trama = "Seconda guerra mondiale. Guido, sua moglie Dora e suo figlio Giosuè vengono rinchiusi in un campo nazista. Guido dice al figlio che si trovano in un lagher per partecipare ad un gioco a premi, dove chi fa più punti vince un carrarmato. In questo modo riesce a proteggere il figlio dall'orrore che stanno vivendo.";
		ArrayList<String> tag = new ArrayList<String>();
		tag.add("olocausto");
		tag.add("guerra");
		tag.add("oscar");
		tag.add("amore");
		f = new Film("10.5240/5A58-58D4-01CB-C41D-6902-K", "La vita è bella", "Roberto Benigni",
				attori, 120, 1997, genere, "Melampo Cinematografica", trama, tag);
	}

	@Test
	public void testConstructor() {
		assertNotNull(f);
		assertEquals("10.5240/5A58-58D4-01CB-C41D-6902-K", f.getId());
		assertEquals(120, f.getDurata());
	}
	
	@Test
	public void testPrint() {
		f.printBaseInfo();
		f.printAllInfo();
	}
	
	@Test
	public void testCompare() {
		// Correct and complete title
		assertTrue(f.compareTitle("La vita è bella"));
		// Correct but incomplete title
		assertTrue(f.compareTitle("vita bella"));
		// Wrong title
		assertFalse(f.compareTitle("Titanic"));
		// Correct year
		assertTrue(f.compareYear(1997));
		// Wrong year
		assertFalse(f.compareYear(2018));
		// Correct genre
		assertTrue(f.compareGenre("Drammatico"));
		// Wrong genre
		assertFalse(f.compareGenre("Fantascienza"));
		// Correct and complete director name
		assertTrue(f.compareDirector("Roberto Benigni"));
		// Correct but incomplete director name
		assertTrue(f.compareDirector("Benigni"));
		// Wrong director name
		assertFalse(f.compareDirector("Christopher Nolan"));
		// Correct and complete actor name
		assertTrue(f.compareActor("Nicoletta Braschi"));
		// Correct but incomplete director name
		assertTrue(f.compareActor("Braschi"));
		// Wrong director name
		assertFalse(f.compareActor("Leonardo DiCaprio"));
		// Correct and complete production company
		assertTrue(f.compareProductionCompany("Melampo Cinematografica"));
		// Correct but incomplete production company
		assertTrue(f.compareProductionCompany("Melampo"));
		// Wrong production company
		assertFalse(f.compareProductionCompany("20th Century Fox Film Corporation"));
		// Correct tag
		assertTrue(f.compareTag("oscar"));
		// More than one tag (each one correct)
		assertTrue(f.compareTag("oscar guerra"));
		// Wrong tag
		assertFalse(f.compareTag("iceberg"));
		// More than one tag (not all correct)
		assertFalse(f.compareTag("oscar iceberg olocausto"));
	}

}

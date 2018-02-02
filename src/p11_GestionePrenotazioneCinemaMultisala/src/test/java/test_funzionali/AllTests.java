package test_funzionali;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({UC01Autenticarsi.class, UC02Deautenticarsi.class,
	UC03VisualizzareIlProprioProfilo.class,
	UC04VisualizzareLaProgrammazioneDiUnaSalaDiUnoDeiPropriCinema.class,
	UC05InserireUnNuovoSpettacoloNellaProgrammazioneDiUnCinema.class,
	UC06GestireLeSaleDiUnoDeiPropriCinema.class,
	UC07VisualizzareUnElencoDiFilmEsistentiNelCircuitoCinema.class,
	UC08VisualizzareUnaSchedaFilm.class, UC09ModificareDatiPersonali.class,
	UC10VisualizzareProfiliDeiGestoriCinema.class,
	UC11RegistrareUnNuovoGestoreCinema.class, UC12EliminareUnGestoreCinema.class,
	UC13ModificareUnGestoreCinema.class, UC14InserireUnNuovoFilm.class,
	UC15EliminareUnFilm.class, SS1EliminareUnaSalaDiUnoDeiPropriCinema.class,
	SS2InserireUnaNuovaSalaInUnoDeiPropriCinema.class, SS3CercareUnFilm.class,
	SS4AggiungereUnCinemaPerUnGestoreCinema.class,
	SS5RimuovereUnCinemaDiUnGestoreCinema.class})
public class AllTests {
	
}

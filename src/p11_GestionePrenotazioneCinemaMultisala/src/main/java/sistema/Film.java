package sistema;

import java.util.*;

/**
 * Film.java
 * <p>
 * A representation of a film. A Film is characterized by the following attributes: id (for example EIDR
 * Entertainment Identifier Register), title, director, actors, duration, year, genres, production company,
 * plot and tags related to the film.
 * <p>
 * The class also provide two different print methods in order to print all the info of the film
 * or only the base info (id, title, year)
 *
 * @author Sara Martino
 */
public class Film {
    private String id;
    private String titolo;
    private String regista;
    private ArrayList<String> attori;
    private int durata;
    private int anno;
    private ArrayList<String> listaGeneri;
    private String casaDiProduzione;
    private String trama;
    private ArrayList<String> listaTag;
	
    /**
     * Constructs an object of type Film using the input parameters.
     * 
     * @param  id					An unique id identifying the film
     * 								For example EIDR Entertainment Identifier Register
     * @param  titolo				The title of the film
     * @param  regista				The director of the film
     * @param  attori				A list containing the main actors of the film
     * @param  durata				The duration of the film
     * @param  anno					The year of the film
     * @param  genere				A list of the genres of the film
     * @param  casaDiProduzione		The production company
     * @param  trama				The plot of the film
     * @param  tag					A list of tag that are pertinent to the film
     */
    public Film(String id, String titolo, String regista, ArrayList<String> attori,
    		int durata, int anno, ArrayList<String> genere, String casaDiProduzione,
    		String trama, ArrayList<String> tag) {
    	super();
    	this.id = id;
    	this.titolo = titolo;
    	this.regista = regista;
    	this.attori = attori;
    	this.durata = durata;
    	this.anno = anno;
    	this.listaGeneri = genere;
    	this.casaDiProduzione = casaDiProduzione;
    	this.trama = trama;
    	this.listaTag = tag;
    }
    	
    /**
     * Returns the id of the film.
     * 
     * @return The id of the film
     */
    public String getId() {
    	return id;
    }
    
    /**
     * Returns the duration of the film.
     * 
     * @return The id of the film
     */
    public int getDurata() {
    	return durata;
    }
    	
    /**
     * Prints base info of the film, i.e. id, title and year.
     */
    public void printBaseInfo() {
    	System.out.println("Id:                      " + this.id);
    	System.out.println("Titolo:                  " + this.titolo);
    	System.out.println("Anno:                    " + this.anno);
    }
    	
    /**
     * Prints all info of the film, i.e. id, title, year, genres, duration, director, actors,
     * production company, plot and tags.
     */
    public void printAllInfo() {
    	System.out.println("Id:                      " + this.id);
    	System.out.println("Titolo:                  " + this.titolo);
    	System.out.println("Anno:                    " + this.anno);
    	System.out.print("Genere:                  ");
    	for (int i = 0; i < listaGeneri.size() - 1; i++) {
    		System.out.print(listaGeneri.get(i) + " / ");
    	}
    	System.out.println(listaGeneri.get(listaGeneri.size() - 1));
    	System.out.println("Durata:                  " + this.durata + " minuti");
    	System.out.println("Regista:                 " + this.regista);
    	System.out.print("Attori:                  ");
    	for (int i = 0; i < attori.size() - 1; i++) {
    		System.out.print(attori.get(i) + ", ");
    	}
    	System.out.println(attori.get(attori.size() - 1));
    	System.out.println("Casa di produzione:      " + this.casaDiProduzione);
    	System.out.println("Trama:                   " + this.trama);
    	System.out.print("Tag:                     ");
    	for (int i = 0; i < listaTag.size() - 1; i++) {
    		System.out.print(listaTag.get(i) + " - ");
    	}
    	System.out.println(listaTag.get(listaTag.size() - 1));
    }
    	
    /**
     * Compares the title of the film with the input string.
     * <p>
     * Returns true if and only if the title of the film contains each word of the input string.
     * 
     * @param t	the sequence of words to compare with the title
     * @return	true if the title of the film contains each word of the input string,
     * 		false otherwise
     */
    
    /*
	 * This function is necessary in order to search a film by title or part of it
	 */
    public boolean compareTitle(String t) {
    	boolean flag = true;
        String[] splitStr = t.split("\\s+");
    	for (int i = 0; i < splitStr.length; i++) {
    		String temp = splitStr[i];
    		if (!this.titolo.toLowerCase().contains(temp.toLowerCase())) {
    			flag = false;
    			break;
    		}
    	}
    	return flag;
    }
    	
    /**
     * Compares the year of the film with the input parameter.
     * <p>
     * Returns true if and only if the year of the film is the same as the input.
     * 
     * @param y	the year to compare with the year of the film
     * @return	true if the year of the film is the same as the input parameter, false otherwise
     */   	
    /*
     * This function is necessary in order to search a film by year
     */
    public boolean compareYear(int y) {
    	return (this.anno == y);
    }
    	
    /**
     * Compares the genres of the film with the input parameter.
     * <p>
     * Returns true if and only if one of the elements of the genres list of the film
     * equals the input.
     * 
     * @param g	the genre to compare
     * @return	true if the input is the same as one of the elements of the genres list of the
     * 		film, false otherwise
     */	
    /*
     * This function is necessary in order to search a film by genre
     */
    public boolean compareGenre(String g) {
    	for (int i = 0; i < listaGeneri.size(); i++) {
    		if (this.listaGeneri.get(i).toLowerCase().equals(g.toLowerCase())) {
    			return true;
    		}	
    	}
    	return false;
    }
    
    /**
     * Compares the director of the film with the input parameter.
     * <p>
     * Returns true if and only if the string representing the director of the film contains 
     * each word of the input string.
     * 
     * @param d	the sequence of words to compare with the director
     * @return	true if the director of the film contains each word of the input string,
     * 		false otherwise
     */	
    /*
     * This function is necessary in order to search a film by director
     */
    public boolean compareDirector(String d) {
    	String[] splitStr = d.split("\\s+");
    	boolean flag = true;
    	for (int i = 0; i < splitStr.length; i++) {
    		String temp = splitStr[i];
    		if (!this.regista.toLowerCase().contains(temp.toLowerCase())) {
    			flag = false;
    			break;
    		}
    	}
    	return flag;
    }
    	
    /**
     * Compares the actors of the film with the input parameter.
     * <p>
     * Returns true if and only if one of the elements of the actors list of the film
     * contains each word of the input.
     * 
     * @param a	the sequence of words to compare with the actors
     * @return	true if one of the elements of the actors list of the film contains
     * 		each word of the input string, false otherwise
     */ 	
    /*
     * This function is necessary in order to search a film by actor
     */
    public boolean compareActor(String a) {
    	String[] splitStr = a.split("\\s+");
    	boolean flag = true;
    	for (int j = 0; j < attori.size(); j++) {
    		flag = true;
    		for (int i = 0; i < splitStr.length; i++) {
    			String temp = splitStr[i];
    			if (!this.attori.get(j).toLowerCase().contains(temp.toLowerCase())) {
    				flag = false;
    				break;
    			}
    		}
    		if (flag) {
    			break;
    		}	
    	}
    	return flag;		
    }
    	
    /**
     * Compares the production company of the film with the input parameter.
     * <p>
     * Returns true if and only if the production company of the film contains
     * the specified sequence of char values.
     * 
     * @param c	the sequence to search for
     * @return	true if the production company contains c, false otherwise
     */
    /*
     * This function is necessary in order to search a film by production company
     * or part of the name of the production company
     */
    public boolean compareProductionCompany(String c) {
    	return this.casaDiProduzione.toLowerCase().contains(c.toLowerCase());
    }
    
    /**
     * Compares the tags of the film with the input parameter.
     * <p>
     * Returns true if and only if each tag in the input parameter is contained in the tags
     * list of the film.
     * 
     * @param t	the tags to compare
     * @return	true if each tag in the input string is contained in the tags list of
     * 		the film, false otherwise
     */
    /*
     * This function is necessary in order to search a film by tags
     */
    public boolean compareTag(String t) {
    	String[] splitStr = t.split("\\s+");
    	boolean flag = false;
    	for (int i = 0; i < splitStr.length; i++) {
    		flag = false;
    		String temp = splitStr[i];
    		for (int j = 0; j < listaTag.size(); j++) {
    			if (this.listaTag.get(j).toLowerCase().contains(temp.toLowerCase())) {
    				flag = true;
    				break;
    			}
    		}
    		if (flag == false) {
    			break;
    		}
    	}
    	return flag;
    }
}

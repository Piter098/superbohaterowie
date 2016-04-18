/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Fizyczne;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import superbohaterix.Enumeratory.kierunki;

/**
 * Kazdy obiekt, ktory moze zostac wyswietlony w polu gry<br>
 * Posiada swoje koordynaty, obrazek, id, nazwę oraz kierunek
 * @author Piter
 */
public class PunktNaMapie {
    private int x;
    private int y;
    private Image grafika;
    private ImageView fizycznyObrazek;
	private int id;

    private kierunki kierunek;
	private String nazwa;
    
    public void umiescNaMapie(){}
	
	/**
     * @return id obrazka na liscie obrazkow
     */
    public int getId() {
        return id;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @return grafika wyswietlana na planszy
     */
    public Image getGrafika() {
        return grafika;
    }
  
    /**
     * @return kierunek
     */
    public kierunki getKierunek() {
        return kierunek;
    }
	
    /**
     * @return fizycznyObrazek - obrazek, ktory mozna kliknac
     */
    public ImageView getFizycznyObrazek() {
        return fizycznyObrazek;
    }
	
	/**
	 * @return nazwa/imie obiektu
	 */
	public String getNazwa() {
		return nazwa;
	}

    /**
     * @param id id obrazka na liscie obrazkow
     */
    public void setId(int id) {
        this.id = id;
    }
	
    /**
     * @param x ustawia pozycje x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @param y ustawia pozycje y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @param grafika ustawia grafike obiektu
     */
    public void setGrafika(Image grafika) {
        this.grafika = grafika;
    }

    /**
     * @param kierunek ustawia kierunek
     */
    public void setKierunek(kierunki kierunek) {
        this.kierunek = kierunek;
    }

    /**
     * @param fizycznyObrazek ustawia obrazek, który można kliknac
     */
    public void setFizycznyObrazek(ImageView fizycznyObrazek) {
        this.fizycznyObrazek = fizycznyObrazek;
    }

	/**
	 * @param nazwa ustawia nazwe/imie obiektu
	 */
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

}
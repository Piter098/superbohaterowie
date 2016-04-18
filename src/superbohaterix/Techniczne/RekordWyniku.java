/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Techniczne;

import com.thoughtworks.xstream.annotations.*;

/**
 * Wpis na liscie wynikow<br>
 * Zawiera imie i czas gry
 * @author Piter
 */
public class RekordWyniku{

	private String imie = "";
	private int czas = 0;

	public RekordWyniku(String imie, int czas) {
		this.imie = imie;
		this.czas = czas;
	}
	public RekordWyniku() {
	}
	
	@Override
	public String toString(){
		return this.imie+" - "+this.czas;
	}

	/**
	 * @return imie gracza
	 */
	public String getImie() {
		return imie;
	}

	/**
	 * @return czas gry
	 */
	public int getCzas() {
		return czas;
	}

	/**
	 * @param imie ustawia imie gracza
	 */
	public void setImie(String imie) {
		this.imie = imie;
	}

	/**
	 * @param czas ustawia czas gry
	 */
	public void setCzas(int czas) {
		this.czas = czas;
	}
	
}

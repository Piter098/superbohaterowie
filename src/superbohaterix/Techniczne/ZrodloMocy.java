/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Techniczne;

import superbohaterix.Enumeratory.typZrodla;
import static superbohaterix.Enumeratory.typZrodla.*;

/**
 * Zrodlo mocy<br>
 * Posiada swoj potencjal, typ, nazwe oraz id
 * @author Piter
 */
public class ZrodloMocy {
	
	private int potencjal;
	private final typZrodla typ;
	private final String nazwa;
	private final int id;

	/**
	 * Konstruktor
	 * @param id
	 */
	public ZrodloMocy(int id) {
		this.id=id;
		this.potencjal=10;
		
		
		switch(this.id){
			case 0: this.typ = POWER; this.nazwa = "Potężny placeholder";break;
			case 1: this.typ = INTEL; this.nazwa =  "Nienazwane źródło inteligencji";break;
			case 2: this.typ = SPEED; this.nazwa =  "Półlegalne źródło szybkości";break;
			case 3: this.typ = FIGHT; this.nazwa =  "Podziemny krąg";break;
			case 4: this.typ = ENERGY; this.nazwa =  "Niewiem co to jest, ale zwiększa energię";break;
			case 5: this.typ = DEFENCE; this.nazwa =  "Srebrna taśma klejąca";break;
			default: this.typ = null; this.nazwa = "Domyślna nazwa, która nie powinna się nigdy pojawić";break;
		}
	}

	/**
	 * @return nazwa
	 */
	public String getNazwa() {
		return nazwa;
	}

	/**
	 * @return potencjal
	 */
	public int getPotencjal() {
		return potencjal;
	}

	/**
	 * @return typ
	 */
	public typZrodla getTyp() {
		return typ;
	}

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param potencjal zwieksza potencjal o dana wartosc
	 */
	public void addPotencjal(int potencjal) {
		this.potencjal += potencjal;
	}
	/**
	 * @param potencjal zmniejsza potencjal o dana wartosc
	 */
	public void subPotencjal(int potencjal) {
		this.potencjal -= potencjal;
	}
	
	
}

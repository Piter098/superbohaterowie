/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Techniczne;

import java.util.logging.Level;
import java.util.logging.Logger;
import static superbohaterix.Enumeratory.stanRuchu.*;
import superbohaterix.Fizyczne.Bohater;
import superbohaterix.Fizyczne.Czlowiek;
import superbohaterix.Fizyczne.Nadczlowiek;
import superbohaterix.Fizyczne.Zloczynca;
import superbohaterix.OknoGlowne;

/**
 * Obiekt odpowiedzialny za walke miedzy bohaterem a zloczynca
 * @author Piter
 */
public class Walka implements Runnable {

	private final Bohater bohater;
	private final Zloczynca zloczynca;
	/**
	 * False - tura zloczyncy<br>
	 * True - tura bohatera
	 */
	private boolean turaBohatera;
	private boolean walkaTrwa = true;
	private int delay = 0;

	/**
	 * Konstruktor
	 * @param b bohater
	 * @param z zloczynca
	 */
	public Walka(Bohater b, Zloczynca z) {
		bohater = b;
		zloczynca = z;

		bohater.setStan(BITWA);
		zloczynca.setStan(BITWA);
		
		//Request odswiezenia panelu informacyjnego
		OknoGlowne.chcialbymOdswiezyc(bohater);
		OknoGlowne.chcialbymOdswiezyc(zloczynca);

		//Jesli bohater jest szybszy to on zaczyna, jesli nie to zloczynca
		turaBohatera = bohater.getSpd() > zloczynca.getSpd();

	}

	/**
	 * Losowanie umiejetnosci z jaka atakuje nadczlowiek
	 * @param n nadczlowiek
	 * @return id umiejetnosci
	 */
	private int losujUmiejetnosc(Nadczlowiek n) {
		int skill = 0;

		switch ((int) (Math.random() * 10) % 3) {
			case 0:
				skill = n.getStr();
				break;
			case 1:
				skill = n.getIntel();
				break;
			case 2:
				skill = n.getEnrg();
				break;
		}

		return skill;
	}

	/**
	 * Wszystko jest uruchamiane jako watek, bo tak
	 */
	@Override
	public void run() {
		while (walkaTrwa) {
			if (!Magazyn.isPauza()) {
				walcz();
			}

			if (!bohater.isZywy()) {
				zloczynca.setStan(IDZIE);
				walkaTrwa = false;
				break;
			}
			if (!zloczynca.isZywy()) {
				bohater.setStan(IDZIE);
				bohater.setCelDoLikwidacji(null);
				walkaTrwa = false;
				break;
			}

			try {
				Thread.sleep(10);
			}
			catch (InterruptedException ex) {
				Logger.getLogger(Czlowiek.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 * Obsluga atakow w turze
	 */
	private void walcz() {

		Nadczlowiek atakujacy;
		Nadczlowiek przeciwnik;

		if (delay == 0) {
			if (turaBohatera) {
				atakujacy = bohater;
				przeciwnik = zloczynca;
			}
			else {
				atakujacy = zloczynca;
				przeciwnik = bohater;
			}

			if (atakujacy.getHp() > 0) {
				if (przeciwnik.getHp() > 0) {

					int ciosBazowy = losujUmiejetnosc(atakujacy) * (int) (1 + (float) (atakujacy.getSkill()));
					int cios = ciosBazowy - przeciwnik.getDef();
					if (cios < 0) {
						cios = 0;
					}
					przeciwnik.setHp(przeciwnik.getHp() - cios);
					//System.out.println(atakujacy.getNazwa() + " atakuje. " + przeciwnik.getNazwa() + " traci " + cios + " hp (" + ciosBazowy + ")");
					delay = 100;

					turaBohatera = !turaBohatera;
				}
				else {
					przeciwnik.zabij();
					walkaTrwa = false;
					
					//Request odswiezenia panelu informacyjnego
					OknoGlowne.chcialbymOdswiezyc(przeciwnik);
				}
			}
			else {
				atakujacy.zabij();
				walkaTrwa = false;
				
				//Request odswiezenia panelu informacyjnego
				OknoGlowne.chcialbymOdswiezyc(atakujacy);
			}
		}
		else if (delay > 0) {
			delay--;
		}
	}

}

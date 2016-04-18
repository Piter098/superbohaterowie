/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Fizyczne;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import superbohaterix.Enumeratory.stanRuchu;
import static superbohaterix.Enumeratory.stanRuchu.*;
import superbohaterix.OknoGlowne;
import superbohaterix.Techniczne.GeneratorImion;
import superbohaterix.Techniczne.Magazyn;
import superbohaterix.Techniczne.Wynik;
import superbohaterix.Techniczne.ZrodloMocy;

/**
 * Zloczynca - wrog, morduje, atakuje itp.
 *
 * @author Piter
 */
public final class Zloczynca extends Nadczlowiek {

	private int odstepAtak = 50;
	private int odstepAtak2 = 0;

	private int wyjX;
	private int wyjY;

	/**
	 * Zloczynca morduje cywila jesli spotka go na drodze
	 */
	private void zabijCywila() {
		//Wektor do sprawdzania czy po drugiej stronie ulicy znajduje sie cywil
		int[] wektor = new int[2];
		switch (getKierunek()) {
			case E:
				wektor[0] = 0;
				wektor[1] = -1;
				break;
			case S:
				wektor[0] = 1;
				wektor[1] = 0;
				break;
			case N:
				wektor[0] = -1;
				wektor[1] = 0;
				break;
			case W:
				wektor[0] = 0;
				wektor[1] = 1;
				break;
		}

		Cywil cel = null;
		//Znajdowanie cywila, na ktorego natrafil zloczynca
		for (Cywil o : Magazyn.getC()) {
			if (o.getX() == getX() && o.getY() == getY() && o.getStan() != STACJONUJE && o.getStan() != MARTWY) {
				cel = o;
				break;
			}
			if (o.getX() == getX() + wektor[0] && o.getY() == getY() + wektor[1] && o.getStan() != STACJONUJE && o.getStan() != MARTWY) {
				cel = o;
				break;
			}
		}
		//Morderstwo
		if (cel != null && getStan() != stanRuchu.STOI && cel.getStan() != MARTWY) {
			cel.zabij();
		}
	}

	/**
	 * Jesli zloczynca wjedzie do miasta to zaczyna je atakowac
	 */
	@Override
	public void wjazdDoMiasta() {
		
		if (getX() == getCel().getWjazd(0) && getY() == getCel().getWjazd(1)) {
			if (getStan() == IDZIE) {
				setStan(STOI);
				//System.out.println(getNazwa() + ": Wjechalem do miasta!");
				wyjX = getCel().getWyjazd(0);
				wyjY = getCel().getWyjazd(1);
				OknoGlowne.chcialbymOdswiezyc(this);
			}
			
			//Co 50 taktow zloczynca kradnie potencjal z kazdego zrodla w miescie
			if (odstepAtak == 0) {

				for (ZrodloMocy o : getCel().getZrodla()) {
					if (o.getPotencjal() > 0) {
						o.subPotencjal(1);
					}
				}
				odstepAtak = 50;
				OknoGlowne.chcialbymOdswiezyc(getCel());

			}
			else if (odstepAtak > 0) {
				odstepAtak--;
			}

			//Co 200 taktow zloczynca zabija cywila w miescie
			if (odstepAtak2 == 0 && getCel().getOdwiedzajacy().isEmpty() == false) {
				getCel().getOdwiedzajacy().get(0).zabij();
				odstepAtak2 = 200;
				OknoGlowne.chcialbymOdswiezyc(getCel());
			}
			else if (odstepAtak2 > 0) {
				odstepAtak2--;
			}

			//Sprawdzanie czy atakowane miasto nadal istnieje
			getCel().czyMozeIstniec();
		}
	}

	/**
	 * Wyszukiwanie najblizszego miasta
	 * @return najblizsze miasto
	 */
	public Miasto najblizszeMiasto() {
		Miasto najblizsze = null;//Magazyn.losujMiasto();
		int odleglosc = 0;
		int najmnOdl = 0;

		for (Miasto o : Magazyn.getMiasta()) {
			odleglosc = (o.getX() - getX()) * (o.getX() - getX()) + (o.getY() - getY()) * (o.getY() - getY());

			if (najmnOdl == 0) {
				najmnOdl = odleglosc;
				najblizsze = o;
			}
			else if (odleglosc < najmnOdl) {
				najmnOdl = odleglosc;
				najblizsze = o;
			}
		}
		return najblizsze;
	}

	/**
	 * Metoda ruchu, dodatkowo ustala dokad ma sie udac zloczynca i zabija cywilow po drodze
	 */
	@Override
	public void idz(){
		if (getStan() != BITWA) {
			if (getCel().isIstnieje() == false) {
				if (getStan() != IDZIE) {
					setX(wyjX);
					setY(wyjY);
				}
				if (Magazyn.getMiasta().isEmpty() == false) {
					setCel(najblizszeMiasto());
					if (getStan() == STOI) {
						setStan(IDZIE);
						OknoGlowne.chcialbymOdswiezyc(this);
					}
				}
				//Jesli wszystkie miasta zostaly zniszczone to zakoncz gre
				else {
					Wynik.przegraj();
					if (getStan() == STOI) {
						setStan(STOI);
						OknoGlowne.chcialbymOdswiezyc(this);
					}
				}
			}
			if (getStan() == IDZIE) {
				zabijCywila();
			}
			super.idz();
		}
	}

	/**
	 * Konstruktor Zloczyncy - nadawane mu jest imie
	 */
	public Zloczynca() {
		super();
		
		//bonus do szybkosci
		setSpd(getSpd() + (int) (Math.random()*100) % 10);
		
		
		setCzyBohater(false);

		int offset = (int) (Math.random() * 10) % 2;

		//losowanie pozycji poczatkowej
		switch ((int) (Math.random() * 100) % 7) {
			case 0:
				setX(9);
				setY(6 + offset);
				break;
			case 1:
				setX(15);
				setY(6 + offset);
				break;
			case 2:
				setX(6 + offset);
				setY(9);
				break;
			case 3:
				setX(12 + offset);
				setY(9);
				break;
			case 4:
				setX(18 + offset);
				setY(9);
				break;
			case 5:
				setX(9);
				setY(12 + offset);
				break;
			case 6:
				setX(15);
				setY(12 + offset);
				break;
		}

		setCel(Magazyn.losujMiasto());
		ustalKierunek();
		setStan(IDZIE);

		//Istnieje szansa, ze zloczynca bedzie wyjatkowy
		//Ultra zloczynca posiada unikalny obrazek i zwiekszone wartosci umiejetnosci, zycia itd
		if ((int) (Math.random() * 100) % 10 == 0) {
			Group obrazekTemp;
			switch ((int) (Math.random() * 100) % 4) {
				default:
					setNazwa("Doktor Gruszka");
					obrazekTemp = new Group(new ImageView(new Image("grafiki/bohaterowie/specjalne/gruszka.png", 128, 128, false, false)));
					break;
				case 1:
					setNazwa("Magik");
					obrazekTemp = new Group(new ImageView(new Image("grafiki/bohaterowie/specjalne/magik.png", 128, 128, false, false)));
					break;
				case 2:
					setNazwa("Anders");
					obrazekTemp = new Group(new ImageView(new Image("grafiki/bohaterowie/specjalne/anders.png", 128, 128, false, false)));
					break;
				case 3:
					setNazwa("Ramiel");
					obrazekTemp = new Group(new ImageView(new Image("grafiki/bohaterowie/specjalne/ramiel.png", 128, 128, false, false)));
					break;
			}
			setObrazekNadczlowieka(obrazekTemp.snapshot(null, null));
			setGrafika(Magazyn.getObrazekUltraZloczyncy());
			setHp(getHp() + (int) (Math.random() * 100) % 20);
			setDef(getDef() + (int) (Math.random() * 100) % 10);
			setStr(getStr() + (int) (Math.random() * 10) % 5);
			setIntel(getIntel() + (int) (Math.random() * 10) % 5);
			setEnrg(getEnrg() + (int) (Math.random() * 10) % 5);
			setSpd(getSpd() + (int) (Math.random() * 10) % 5);
			setSkill(getSkill() + (int) (Math.random() * 10) % 3);
		}
		else {
			setNazwa(GeneratorImion.getZloczynca());
			robienieObrazka();
			setGrafika(Magazyn.getObrazekZloczyncy());
		}
		
		//Ustalanie celu
		if (Magazyn.getMiasta().isEmpty() == false) {
			setCel(najblizszeMiasto());
			setStan(IDZIE);
		}
	}

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Fizyczne;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import superbohaterix.Enumeratory.kierunki;
import superbohaterix.Enumeratory.stanRuchu;
import static superbohaterix.Enumeratory.stanRuchu.*;
import superbohaterix.OknoGlowne;
import superbohaterix.Techniczne.GeneratorImion;
import superbohaterix.Techniczne.Magazyn;
import superbohaterix.Techniczne.Walka;

/**
 * Bohater - ten dobry, walczy ze zloczyncami, przywraca porzadek na swiecie
 * @author Piter
 */
public final class Bohater extends Nadczlowiek {

	private boolean scigajWroga = false;
	private Zloczynca celDoLikwidacji;
	private Walka walcz;

	/**
	 * Walka pomiedzy dwoma nadludzmi
	 */
	public void walcz() {
		
		//Wektor do sprawdzania czy po drugiej stronie ulicy znajduje sie wrog
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

		Zloczynca cel = null;
		//Znajdowanie wroga, na ktorego natrafil bohater
		for (Zloczynca o : Magazyn.getZ()) {
			if (o.getX() == getX() && o.getY() == getY() && o.getStan() != BITWA && o.getStan() != MARTWY) {
				cel = o;
				break;
			}
			if (o.getX() == getX() + wektor[0] && o.getY() == getY() + wektor[1] && o.getStan() != BITWA && o.getStan() != MARTWY) {
				cel = o;
				break;
			}
		}
		
		//Uruchamianie bitwy
		if (cel != null && getStan() != stanRuchu.BITWA && getStan() != stanRuchu.MARTWY) {
			walcz = new Walka(this, cel);
			Thread t = new Thread(walcz);
			t.setDaemon(true);
			t.start();
		}
	}

	/**
	 * Konstruktor Bohatera - nadawane mu jest imie
	 * @param stolica
	 */
	public Bohater(Miasto stolica) {
		super();
		
		//Pojawia sie w stolicy
		setX(stolica.getWyjazd(0));
		setY(stolica.getWyjazd(1));
		ustalKierunek();
		setStan(IDZIE);

		Magazyn.setWartosciZrodel();

		//Zwiekszenie wartosci umiejetnosci proporcjonalnie do wartosci potencjalow zrodel na swiecie
		setStr(getStr() + (int) ((double) (Magazyn.getWartosciZrodel(0)) * 0.1));
		setIntel(getIntel() + (int) ((double) (Magazyn.getWartosciZrodel(1)) * 0.1));
		setSpd(getSpd() + (int) ((double) (Magazyn.getWartosciZrodel(2)) * 0.1));
		setSkill(getSkill() + (int) ((double) (Magazyn.getWartosciZrodel(3)) * 0.1));
		setEnrg(getEnrg() + (int) ((double) (Magazyn.getWartosciZrodel(4)) * 0.1));
		setDef(getDef() + (int) ((double) (Magazyn.getWartosciZrodel(5)) * 0.1));

		setCzyBohater(true);
		
		//Istnieje szansa, ze bohater bedzie wyjatkowy
		//Ultra bohater posiada unikalny obrazek i zwiekszone wartosci umiejetnosci, zycia itd
		if ((int) (Math.random() * 100) % 10 == 0) {
			Group obrazekTemp;
			switch ((int) (Math.random() * 100) % 4) {
				default:
					setNazwa("Butman");
					obrazekTemp = new Group(new ImageView(new Image("grafiki/bohaterowie/specjalne/butman.png", 128, 128, false, false)));
					break;
				case 1:
					setNazwa("Generał Piłsudski");
					obrazekTemp = new Group(new ImageView(new Image("grafiki/bohaterowie/specjalne/general.png", 128, 128, false, false)));
					break;
				case 2:
					setNazwa("Dragonborn");
					obrazekTemp = new Group(new ImageView(new Image("grafiki/bohaterowie/specjalne/dragonborn.png", 128, 128, false, false)));
					break;
				case 3:
					setNazwa("Komandor Shepard");
					obrazekTemp = new Group(new ImageView(new Image("grafiki/bohaterowie/specjalne/shepard.png", 128, 128, false, false)));
					break;
			}
			setObrazekNadczlowieka(obrazekTemp.snapshot(null, null));
			setGrafika(Magazyn.getObrazekUltraBohatera());
			przydzielWartosci(0.1, false);
		}
		else {
			setNazwa(GeneratorImion.getBohater());
			robienieObrazka();
			setGrafika(Magazyn.getObrazekBohatera());
		}
		
		//Szybkosc bohatera jest nieco wyzsza niz innych ludzi, zeby mogl dogonic zloczyncow
		setMaxDelay(40);
	}

	/**
	 * Nieco zmieniona metoda zmiany kierunku, roznica polega na tym, ze celem jest zloczynca
	 */
	@Override
	public void ustalKierunek() {
		if (scigajWroga == true && getCelDoLikwidacji() != null) {
			if (getCelDoLikwidacji().getStan() == BITWA || getCelDoLikwidacji().getStan() == MARTWY) {
				setCelDoLikwidacji(Magazyn.getZ().get((int) (Math.random() * Magazyn.getZ().size())));
				OknoGlowne.chcialbymOdswiezyc(this);
			}

			setKierunek(PoleNaUlicy.getKierunek(getX(), getY()));
			switch (getKierunek()) {
				case NE:
					if (getCelDoLikwidacji().getX() > getX() && (getCelDoLikwidacji().getY() == getY() || (getSkrzyzowanie() != 4 && getSkrzyzowanie() != 5))) {
						setKierunek(kierunki.E);
					}
					else {
						setKierunek(kierunki.N);
					}
					break;
				case SE:
					if (getCelDoLikwidacji().getY() > getY() && (getCelDoLikwidacji().getX() == getX() || (getSkrzyzowanie() != 1 && getSkrzyzowanie() != 3 && getSkrzyzowanie() != 3))) {
						setKierunek(kierunki.S);
					}
					else {
						setKierunek(kierunki.E);
					}
					break;
				case SW:
					if (getCelDoLikwidacji().getX() < getX() && (getCelDoLikwidacji().getY() == getY() || (getSkrzyzowanie() != 0 && getSkrzyzowanie() != 1))) {
						setKierunek(kierunki.W);
					}
					else {
						setKierunek(kierunki.S);
					}
					break;
				case NW:
					if (getCelDoLikwidacji().getY() < getY() && (getCelDoLikwidacji().getX() == getX() || (getSkrzyzowanie() != 0 && getSkrzyzowanie() != 2 && getSkrzyzowanie() != 4))) {
						setKierunek(kierunki.N);
					}
					else {
						setKierunek(kierunki.W);
					}
					break;
			}
		}
		else if (scigajWroga == true && getCelDoLikwidacji() == null) {
			setCelDoLikwidacji(Magazyn.getZ().get((int) (Math.random() * Magazyn.getZ().size())));
			OknoGlowne.chcialbymOdswiezyc(this);
		}
		//jesli nie ma zloczyncow to uzywa standardowej metody, bo musi wroci do stolicy
		else {
			super.ustalKierunek();
		}
	}

	/**
	 * Jesli wjechal do stolicy to znika ze swiata
	 */
	@Override
	public void wjazdDoMiasta() {
		if (scigajWroga == false) {
			super.wjazdDoMiasta();
			if (getStan() == STACJONUJE) {
				OknoGlowne.chcialbymOdswiezyc(this);
				zabij();
			}
		}
	}

	/**
	 * Metoda ruchu, dodatkowo ustala czy bohater ma scigac wroga, czy wrocic do stolicy
	 */
	@Override
	public void idz() {
		if (Magazyn.getZ().isEmpty() == true) {
			if (scigajWroga == true) {
				scigajWroga = false;
				setCelDoLikwidacji(null);
				OknoGlowne.chcialbymOdswiezyc(this);
			}
		}
		else {
			if (scigajWroga == false) {
				OknoGlowne.chcialbymOdswiezyc(this);
				scigajWroga = true;
			}
			
		}

		if (scigajWroga == false && getCel() != Magazyn.getSwiat().getStolica()) {
			setCel(Magazyn.getSwiat().getStolica());
			OknoGlowne.chcialbymOdswiezyc(this);
		}

		if (getStan() == IDZIE) {
			walcz();
		}

		super.idz();

	}

	/**
	 * @return the celDoLikwidacji
	 */
	public Zloczynca getCelDoLikwidacji() {
		return celDoLikwidacji;
	}

	/**
	 * @param celDoLikwidacji the celDoLikwidacji to set
	 */
	public void setCelDoLikwidacji(Zloczynca celDoLikwidacji) {
		this.celDoLikwidacji = celDoLikwidacji;
	}

}

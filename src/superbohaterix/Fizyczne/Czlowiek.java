/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Fizyczne;

import java.util.logging.Level;
import java.util.logging.Logger;
import superbohaterix.Enumeratory.kierunki;
import superbohaterix.Enumeratory.stanRuchu;
import static superbohaterix.Enumeratory.stanRuchu.*;
import superbohaterix.OknoGlowne;
import superbohaterix.Techniczne.Magazyn;
import superbohaterix.Techniczne.Wynik;

/**
 * Ruchomy obiekt<br>
 * Potrafi podrozowac pomiedzy miastami
 *
 * @author Piter
 */
public abstract class Czlowiek extends PunktNaMapie implements Runnable {

	private Miasto cel;
	private stanRuchu stan;
	private int delay = 0;
	/**
	 * Odstep czasowy przed wykonaniem kolejnego ruchu
	 */
	private int maxDelay = 50;
	private boolean Zywy = true;
	private int skrzyzowanie = -1;
	private Thread wskaznikWatku;

	/**
	 * Ta funkcja rozni sie w obiektach potomnych
	 */
	protected void wyjazdZMiasta() {
	}

	/**
	 * Jesli czlowiek wjedzie do miasta to ustalany jest jego stan na STACJONUJE
	 */
	protected void wjazdDoMiasta() {
		if (getX() == getCel().getWjazd(0) && getY() == getCel().getWjazd(1) && getStan() != STACJONUJE) {
			//System.out.println(getNazwa() + ": Wjechalem do miasta!");
			setStan(STACJONUJE);
			OknoGlowne.chcialbymOdswiezyc(this);
		}
	}

	/**
	 * Przesuwa czlowieka o dany wektor
	 * @param x
	 * @param y
	 */
	protected void przesun(int x, int y) {
		if (delay == 0) {
			setX(getX() + x);
			setY(getY() + y);
			delay = maxDelay;
		}
		delay--;
	}

	/**
	 * @return wektor[] przesuniecia, 0 - x, 1 - y
	 */
	public int[] ustalWektor() {
		int[] wektor = new int[2];
		switch (getKierunek()) {
			case E:
				wektor[0] = 1;
				wektor[1] = 0;
				break;
			case S:
				wektor[0] = 0;
				wektor[1] = 1;
				break;
			case N:
				wektor[0] = 0;
				wektor[1] = -1;
				break;
			case W:
				wektor[0] = -1;
				wektor[1] = 0;
				break;
			default:
				//System.out.println("Nieznany kierunek :/");
				wektor[0] = 0;
				wektor[1] = 0;
				setStan(stanRuchu.STOI);
				OknoGlowne.chcialbymOdswiezyc(this);
				break;
		}
		return wektor;
	}

	/**
	 * Funkcja zajmująca dany punkt na mapie i zwalniająca poprzedni
	 *
	 * @param x - poprzedni X
	 * @param y - poprzedni Y
	 */
	public void zajmij(int x, int y) {
		if (getX() != x || getY() != y) {
			PoleNaUlicy.setZajete(true, getX(), getY());
			PoleNaUlicy.setZajete(false, x, y);
		}
		else {
			PoleNaUlicy.setZajete(true, getX(), getY());
		}
	}

	/**
	 * Funkcja ustalajaca jaki kierunek ma obrac czlowiek, aby dotrzec do miasta<br>
	 */
	public void ustalKierunek() {
		setKierunek(PoleNaUlicy.getKierunek(getX(), getY()));
		switch (getKierunek()) {
			case NE:
				//Jesli jest na odpowiedniej wysokosci i szerokosci to zmienia kierunek
				//moze tez zjechac jesli jest to ostatnie skrzyzowanie
				if (getCel().getX() + 1 > getX() && (getCel().getY() + 1 == getY() || (getSkrzyzowanie() != 4 && getSkrzyzowanie() != 5))) {
					setKierunek(kierunki.E);
				}
				else {
					setKierunek(kierunki.N);
				}
				break;
			case SE:
				if (getCel().getY() + 1 > getY() && (getCel().getX() == getX() || (getSkrzyzowanie() != 1 && getSkrzyzowanie() != 3 && getSkrzyzowanie() != 5))) {
					setKierunek(kierunki.S);
				}
				else {
					setKierunek(kierunki.E);
				}
				break;
			case SW:
				if (getCel().getX() < getX() && (getCel().getY() == getY() || (getSkrzyzowanie() != 0 && getSkrzyzowanie() != 1))) {
					setKierunek(kierunki.W);
				}
				else {
					setKierunek(kierunki.S);
				}
				break;
			case NW:
				if (getCel().getY() < getY() && (getCel().getX() + 1 == getX() || (getSkrzyzowanie() != 0 && getSkrzyzowanie() != 2 && getSkrzyzowanie() != 4))) {
					setKierunek(kierunki.N);
				}
				else {
					setKierunek(kierunki.W);
				}
				break;
		}
	}

	/**
	 * Funkcja poruszająca człowiekiem bez sprawdzania czy miejsce jest wolne
	 */
	public synchronized void idz() {
		ustalKierunek();

		int[] wektor;
		wektor = ustalWektor();

		//ustawia zmienna skrzyzowanie na id skrzyzowania na ktore zmierza czlowiek
		if (PoleNaUlicy.getSkrzyzowanie(getX() + wektor[0], getY() + wektor[1]) >= 0 && PoleNaUlicy.getSkrzyzowanie(getX() + wektor[0], getY() + wektor[1]) != getSkrzyzowanie()) {
			setSkrzyzowanie(PoleNaUlicy.getSkrzyzowanie(getX() + wektor[0], getY() + wektor[1]));
		}

		if (getStan() == IDZIE) {
			przesun(wektor[0], wektor[1]);
		}

		//jesli czlowiek zjechal ze skrzyzowania to zmienna skrzyzowanie jest ustawiana na -1
		if (PoleNaUlicy.getSkrzyzowanie(getX(), getY()) == -1 && getSkrzyzowanie() >= 0) {
			setSkrzyzowanie(-1);
		}
	}

	/**
	 * Ta funkcja jest tylko w klasie Cywil
	 */
	protected void sprawdzStanMiasta() {
	}

	/**
	 * @return the cel
	 */
	public Miasto getCel() {
		return cel;
	}

	/**
	 * @return the stan
	 */
	public stanRuchu getStan() {
		return stan;
	}

	/**
	 * @param cel the cel to set
	 */
	public void setCel(Miasto cel) {
		this.cel = cel;
		//System.out.println(this.getImie() + " zamierza iść do " + cel.getNazwa());
	}

	/**
	 * @param stan the stan to set
	 */
	public void setStan(stanRuchu stan) {
		this.stan = stan;
	}

	/**
	 * @param Zywy the Zywy to set
	 */
	public void setZywy(boolean Zywy) {
		this.Zywy = Zywy;
	}

	/**
	 * Tutaj uruchamiane sa wszystkie najwazniejsze metody dla czlowieka
	 */
	@Override
	public void run() {

		//Wszystko dziala gdy czlowiek jest zywy, a gra nadal dziala
		while (getStan() != MARTWY && !Wynik.isCzyPrzegrana()) {
			try {
				if (Magazyn.isPauza() == false) {
					
					//Podejmowanie decyzji o opuszczeniu miasta
					if (getStan() == STACJONUJE && Magazyn.isOpuszczanieMiast()) {
						if ((int) (Math.random() * 1000) == 0) {
							wyjazdZMiasta();
						}
					}
					else {
						//Ruch
						idz();
						//Podejmowanie decyzji o wjezdzie do miasta
						if (getStan() != BITWA) {
							wjazdDoMiasta();
						}
					}

					if (Magazyn.isPostoje()) {
						postoj();
					}

					//Sprawdzanie czy miasto nadal istnieje
					sprawdzStanMiasta();
				}

				Thread.sleep(10);
			}
			catch (InterruptedException ex) {
				Logger.getLogger(Czlowiek.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 * @param skrzyzowanie the skrzyzowanie to set
	 */
	public void setSkrzyzowanie(int skrzyzowanie) {
		this.skrzyzowanie = skrzyzowanie;
	}

	/**
	 * @return the skrzyzowanie
	 */
	public int getSkrzyzowanie() {
		return skrzyzowanie;
	}

	/**
	 * @return the Zywy
	 */
	public boolean isZywy() {
		return Zywy;
	}

	/**
	 * @param maxDelay the maxDelay to set
	 */
	public void setMaxDelay(int maxDelay) {
		this.maxDelay = maxDelay;
	}

	/**
	 * Ta metoda dotyczy obiektu Cywil
	 */
	public void postoj() {
	}

	/**
	 * @return the wskaznikWatku
	 */
	public Thread getWskaznikWatku() {
		return wskaznikWatku;
	}

	/**
	 * @param wskaznikWatku the wskaznikWatku to set
	 */
	public void setWskaznikWatku(Thread wskaznikWatku) {
		this.wskaznikWatku = wskaznikWatku;
	}

}

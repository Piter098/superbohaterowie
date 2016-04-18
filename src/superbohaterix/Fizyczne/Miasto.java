/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Fizyczne;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import superbohaterix.OknoGlowne;
import superbohaterix.Techniczne.Magazyn;
import superbohaterix.Techniczne.Wynik;
import superbohaterix.Techniczne.ZrodloMocy;

/**
 * Miejsce pobytu cywilow.<br>Moze zostac zaatakowane i zniszczone
 * @author Piter
 */
public class Miasto extends PunktNaMapie {

	private boolean istnieje = true;
	/**
	 * Czas do zwiekszenia potencjalu
	 */
	private int czasDoZwiekszenia = 0;

	/**
	 * Lista mieszkancow
	 */
	private final ArrayList<Cywil> mieszkancy = new ArrayList<>();

	/**
	 * Lista cywilow przebywajacych w miescie
	 */
	private final ArrayList<Cywil> odwiedzajacy = new ArrayList<>();

	/**
	 * Tablica dostepnych zrodel energii w danym miescie
	 */
	private final ArrayList<ZrodloMocy> zrodla = new ArrayList<>();
	private boolean czyStolica;
	//Pozycje wjazdow i wyjazdow z miasta
	private final int[] wjazd = new int[2];
	private final int[] wyjazd = new int[2];

	/**
	 * Konstuktor
	 * @param nazwa
	 * @param id
	 * @param x
	 * @param y
	 * @param wjX wjazd x
	 * @param wjY wjazd y
	 * @param wyX wyjazd x
	 * @param wyY wyjazd y
	 */
	public Miasto(String nazwa, int id, int x, int y, int wjX, int wjY, int wyX, int wyY) {
		setNazwa(nazwa);
		setX(x);
		setY(y);
		this.wjazd[0] = wjX;
		this.wjazd[1] = wjY;
		this.wyjazd[0] = wyX;
		this.wyjazd[1] = wyY;
		setGrafika(new Image("grafiki/miasta/" + id + ".png"));
		setFizycznyObrazek(new ImageView(getGrafika()));

		for (int i = 0; i < 6 && zrodla.size() < 5; i++) {
			if ((int) (Math.random() * 2) == 0) {
				ZrodloMocy nowe = new ZrodloMocy(i);
				zrodla.add(nowe);
			}
		}
		//Jesli nadal brak zrodel to dodaje jedno
		if (zrodla.isEmpty() == true) {
			ZrodloMocy nowe = new ZrodloMocy((int) (Math.random() * 5));
			zrodla.add(nowe);
		}
	}

	/**
	 * Funkcja ustalajaca czy miasto zostalo zniszczone<br>
	 */
	public void czyMozeIstniec() {
		if (!Wynik.isCzyPrzegrana()) {
			int potencjaly = 0;

			if (istnieje == true) {
				for (ZrodloMocy o : zrodla) {
					potencjaly += o.getPotencjal();
				}
				if (odwiedzajacy.isEmpty() == true && potencjaly == 0) {
					//System.out.println(getNazwa()+" przestało istnieć");
					istnieje = false;
					OknoGlowne.chcialbymOdswiezyc(this);
				}
			}

			//Sprawdzenie czy koniec gry
			Wynik.przegraj();

			if (istnieje == false) {
				//Jesli miasto bylo stolica to wybierana jest nowa
				while (isCzyStolica() && !Wynik.isCzyPrzegrana() && !Magazyn.getMiasta().isEmpty()) {
					Magazyn.getSwiat().setStolica(Magazyn.losujMiasto());
				}
				//Request skasowania miasta
				Magazyn.getDoUsunieciaMiasta().add(this);
			}
		}
	}

	/**
	 * Zwieksza potencjaly zrodel mocy
	 */
	public void zwiekszPotencjaly() {
		if (czasDoZwiekszenia == 0) {
			for (ZrodloMocy o : zrodla) {
				o.addPotencjal(mieszkancy.size());
			}

			OknoGlowne.chcialbymOdswiezyc(this);
			//System.out.println("Zwiekszono potencjaly");
			czasDoZwiekszenia = 1000;
		}
		czasDoZwiekszenia--;
	}

	/**
	 * @return the czyStolica
	 */
	public boolean isCzyStolica() {
		return czyStolica;
	}

	/**
	 * Zwraca miejsce wjazdu do miasta
	 *
	 * @param i 0 - x, 1 - y
	 * @return wjazd[i] - punkt na mapie
	 */
	public int getWjazd(int i) {
		return wjazd[i];
	}

	/**
	 * Zwraca miejsce wyjazdu do miasta
	 *
	 * @param i 0 - x, 1 - y
	 * @return wyjazd[i] - punkt na mapie
	 */
	public int getWyjazd(int i) {
		return wyjazd[i];
	}

	/**
	 * @param czyStolica the czyStolica to set
	 */
	public void setCzyStolica(boolean czyStolica) {
		if (!Wynik.isCzyPrzegrana()) {
			this.czyStolica = czyStolica;
			//OknoGlowne.chcialbymOdswiezyc(null);
			//System.out.println(this + " " + getNazwa() + " jest teraz stolicą!");
		}
	}

	/**
	 * @return lista mieszkancow
	 */
	public ArrayList<Cywil> getMieszkancy() {
		return mieszkancy;
	}

	/**
	 * @return lista odwiedzajacych
	 */
	public ArrayList<Cywil> getOdwiedzajacy() {
		return odwiedzajacy;
	}

	/**
	 * @return lista zrodel mocy
	 */
	public ArrayList<ZrodloMocy> getZrodla() {
		return zrodla;
	}

	/**
	 * @return czy miasto istnieje
	 */
	public boolean isIstnieje() {
		return istnieje;
	}

}

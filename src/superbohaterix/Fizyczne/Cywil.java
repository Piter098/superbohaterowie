/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Fizyczne;

import superbohaterix.Enumeratory.kierunki;
import static superbohaterix.Enumeratory.stanRuchu.*;
import superbohaterix.OknoGlowne;
import superbohaterix.Techniczne.GeneratorImion;
import superbohaterix.Techniczne.Magazyn;

/**
 * Cywil - zwykly mieszkaniec jednego z miast, jezdzi sobie w odwiedziny do
 * innych miast, jest ofiara atakow<br>
 * Postac tragiczna
 * @author Piter
 */
public final class Cywil extends Czlowiek {

	private String nazwisko;
	private Miasto miastoRodzinne;
	/**
	 * Czas pozostaly podczas postoju
	 */
	private int countdown = 500;
	private boolean chcialbymSemafor = false;
	/**
	 * Wartosc skrzyzowania o jedno pole w przod
	 */
	private int nastSkrzyzowanie = -1;

	/**
	 * Czy cywil ma zostac pokazany na mapie
	 */
	private boolean doPokazania = true;

	/**
	 * Medota sprawiajaca, ze cywil postanawia zrobic sobie maly postoj (np na srodku skrzyzowania)
	 */
	@Override
	public void postoj() {
		if (getStan() == IDZIE) {
			if ((int) (Math.random() * 1000) == 0) {
				countdown = 500;
				setStan(POSTOJ);
				if (isChcialbymSemafor()) {
					getWskaznikWatku().interrupt();
				}
				OknoGlowne.chcialbymOdswiezyc(this);
				//Magazyn.getSwiat().odswierzPanel(this);
			}
		}
		else if (getStan() == POSTOJ) {
			if (countdown > 0) {
				countdown--;
			}
			else {
				setStan(IDZIE);
				OknoGlowne.chcialbymOdswiezyc(this);

				//System.out.println(getImie() + ": Pora ruszać...");
			}
		}
	}

	/**
	 * Cywil wyjezdza z miasta - usuwany jest z listy odwiedzajacych i wybiera nowy cel
	 */
	@Override
	public void wyjazdZMiasta() {
		if (getStan() == STACJONUJE) {
			Miasto obecnieStacjonujeW = getCel();
			obecnieStacjonujeW.getOdwiedzajacy().remove(this);

			//Wybiera miasto do odwiedzenia
			if (obecnieStacjonujeW == miastoRodzinne) {
				while (getCel() == miastoRodzinne) {
					setCel(Magazyn.losujMiasto());
				}
			}
			//Wraca do swojego miasta
			else {
				setCel(miastoRodzinne);
			}
			setX(obecnieStacjonujeW.getWyjazd(0));
			setY(obecnieStacjonujeW.getWyjazd(1));
			setStan(IDZIE);

			OknoGlowne.chcialbymOdswiezyc(this);
			OknoGlowne.chcialbymOdswiezyc(obecnieStacjonujeW);
			
			//Cywil ma zostac pokazany na mapie
			setDoPokazania(true);
		}
	}

	/**
	 * Cywil wjezdza do miasta - zwalnia pole, na ktorym sie znajduje i ustawia jego stan na STACJONUJE
	 */
	@Override
	public void wjazdDoMiasta() {
		if (getX() == getCel().getWjazd(0) && getY() == getCel().getWjazd(1) && getStan() != STACJONUJE && isZywy() == true) {
			setStan(STACJONUJE);
			PoleNaUlicy.setZajete(false, getX(), getY());
			getCel().getOdwiedzajacy().add(this);

			OknoGlowne.chcialbymOdswiezyc(this);
			OknoGlowne.chcialbymOdswiezyc(getCel());

			//System.out.println(getImie() + ": Wjechalem do miasta!");
		}
	}

	/**
	 * Sprawdza czy miasto istnieje
	 */
	@Override
	protected void sprawdzStanMiasta() {
		if (getCel().isIstnieje() == false) {
			Miasto nowe = Magazyn.losujMiasto();
			setCel(nowe);
		}
		if (getMiastoRodzinne().isIstnieje() == false) {
			setMiastoRodzinne(getCel());
		}
	}

	/**
	 * Funkcja poruszająca cywilem na mapie
	 */
	@Override
	public synchronized void idz() {

		//Ustawiany jest obrazek cywila zaleznie od jego stanu
		switch (getStan()) {
			default:
				setGrafika(Magazyn.getObrazekCywila());
				break;
			case STOI:
				setGrafika(Magazyn.getObrazekCywilaS());
				break;
			case POSTOJ:
				setGrafika(Magazyn.getObrazekCywilaP());
				break;
		}
		
		//Jesli cywil znajduje sie na skrzyzowaniu to wartosc przyjmuje id tego skrzyzowania
		//jesli nie znajduje sie to przyjmuje:
		//-1 gdy na pole mozna wjechac
		//-2 gdy nie mozna (czerwone swiatlo)
		int obecneSkrzyzowanie = PoleNaUlicy.getSkrzyzowanie(getX(), getY());

		try {
			if (getStan() == IDZIE) {
				zajmij(getX(), getY());
				int x, y;
				x = getX();
				y = getY();

				ustalKierunek();
				PoleNaUlicy.setZajete(true, getX(), getY());
				int[] wektor;
				wektor = ustalWektor();
				
				//Jakie id skrzyzowania jest na nastepnym polu
				nastSkrzyzowanie = PoleNaUlicy.getSkrzyzowanie(getX() + wektor[0], getY() + wektor[1]);
				//Czy nastepne pole jest zajete
				boolean nastZajete = PoleNaUlicy.isZajete(getX() + wektor[0], getY() + wektor[1]);
				
				//Jesli nastepne pole jest wolne, a ewnetualne skrzyzowanie pokazuje zielone swiatlo to patrzymy dalej
				if (nastZajete == false && obecneSkrzyzowanie != -2) {
					//Jesli na nastepnym polu znajduje sie skrzyzowanie, a cywil nie znajduje sie jeszcze na nim
					if (nastSkrzyzowanie >= 0 && getSkrzyzowanie() < 0) {

						//Cywil ustawia sie w kolejce po semafor
						chcialbymSemafor = true;
						Magazyn.getSkrzyzowania().get(nastSkrzyzowanie).getSemafor().acquire();
						chcialbymSemafor = false;

						//Jesli dostal semafor to przyjmuje jego id
						setSkrzyzowanie(nastSkrzyzowanie);
						OknoGlowne.chcialbymOdswiezyc(Magazyn.getSkrzyzowania().get(getSkrzyzowanie()));
						//System.out.println(getNazwa() + ": Zabieram");

					}
					//Jesli obecne pole nie jest skrzyzowaniem to cywil zwalnia semafor
					//czyli gdy cywil zjedzie ze skrzyzowania
					else if (nastSkrzyzowanie < 0 && getSkrzyzowanie() >= 0 && obecneSkrzyzowanie < 0) {
						Magazyn.getSkrzyzowania().get(getSkrzyzowanie()).getSemafor().release();
						OknoGlowne.chcialbymOdswiezyc(Magazyn.getSkrzyzowania().get(getSkrzyzowanie()));
						setSkrzyzowanie(-1);
						//System.out.println(getNazwa() + ": Zwalniam bo juz nie jestem na skrzyzowaniu");
					}
					przesun(wektor[0], wektor[1]);
				}
				//Jesli cywil ma semafor, ale nie moze wjechac na skrzyzowanie bo jest zajete pole to zwalnia
				else if (nastZajete == true && obecneSkrzyzowanie < 0) {
					if (getSkrzyzowanie() >= 0) {
						Magazyn.getSkrzyzowania().get(getSkrzyzowanie()).getSemafor().release();
						OknoGlowne.chcialbymOdswiezyc(Magazyn.getSkrzyzowania().get(getSkrzyzowanie()));
						setSkrzyzowanie(-1);
						//System.out.println(getNazwa() + ": Zwalniam bo jest zajete");
					}
				}
				//Jesli cywil ma semafor, ale jest czerwone swiatlo to zwalnia
				else if (obecneSkrzyzowanie == -2){
					if (getSkrzyzowanie() >= 0) {
						Magazyn.getSkrzyzowania().get(getSkrzyzowanie()).getSemafor().release();
						OknoGlowne.chcialbymOdswiezyc(Magazyn.getSkrzyzowania().get(getSkrzyzowanie()));
						setSkrzyzowanie(-1);
						//System.out.println(getNazwa() + ": Zwalniam bo mam czerwone");
					}
				}

				zajmij(x, y);
			}
			//Jesli cywil dostal semafor, ale postanowil sie zatrzymac (albo umrzec) przed wjazdem na nie to zwalnia
			else if (getStan() != IDZIE && getSkrzyzowanie() >= 0 && obecneSkrzyzowanie < 0){
				Magazyn.getSkrzyzowania().get(getSkrzyzowanie()).getSemafor().release();
						OknoGlowne.chcialbymOdswiezyc(Magazyn.getSkrzyzowania().get(getSkrzyzowanie()));
						setSkrzyzowanie(-1);
						//System.out.println(getNazwa() + ": Zwalniam bo zatrzymałem się przed");
			}
		}
		catch (InterruptedException ex) {
			//System.out.println("przerwano!");
			chcialbymSemafor = false;
		}
	}

	/**
	 * Smierc cywila
	 */
	public void zabij() {
		PoleNaUlicy.setZajete(false, getX(), getY());
		
		//Jesli cywil byl akurat w miescie to usuwa sie z listy odwiedzajacych
		if (getStan() == STACJONUJE) {
			getCel().getOdwiedzajacy().remove(this);
			OknoGlowne.chcialbymOdswiezyc(getCel());
			//System.out.println("Zabito mnie w miescie :(");
		}

		//Miasto rodzinne w trybie natychiastowym skresla cywila z listy mieszkancow
		if(getMiastoRodzinne().isIstnieje()){
			getMiastoRodzinne().getMieszkancy().remove(this);
		}

		setStan(MARTWY);

		setZywy(false);
		
		//Cywil trafia na czarna liste osob do usuniecia z listy wszystkich cywilow
		Magazyn.getDoUsunieciaC().add(this);
		OknoGlowne.chcialbymOdswiezyc(this);
		OknoGlowne.chcialbymOdswiezyc(miastoRodzinne);
		//System.out.println(getImie() + " umiera");
	}

	/**
	 * Konstruktor cywila - ustalana jest plec, miasto rodzinne, imie i nazwisko
	 *
	 * @param plec
	 * @param miastoR
	 */
	public Cywil(int plec, Miasto miastoR) {
		setNazwa(GeneratorImion.getImie(plec));
		setNazwisko(GeneratorImion.getNazwisko(plec));
		setCel(miastoR);
		miastoRodzinne = miastoR;
		miastoRodzinne.getMieszkancy().add(this);
		setX(miastoRodzinne.getWjazd(0));
		setY(miastoRodzinne.getWjazd(1));
		
		//cywil domyslnie pojawia sie w miescie
		wjazdDoMiasta();
		
		setKierunek(kierunki.N);
		setGrafika(Magazyn.getObrazekCywila());

		OknoGlowne.chcialbymOdswiezyc(miastoRodzinne);
		//System.out.println("Stworzono cywila! - " + getImie() + " " + getNazwisko() + " (" + this + ")");
	}

	/**
	 * @return the nazwisko
	 */
	public final String getNazwisko() {
		return nazwisko;
	}

	/**
	 * @return the miastoRodzinne
	 */
	public Miasto getMiastoRodzinne() {
		return miastoRodzinne;
	}

	/**
	 * @param nazwisko the nazwisko to set
	 */
	public final void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	/**
	 * @param miastoRodzinne the miastoRodzinne to set
	 */
	public final void setMiastoRodzinne(Miasto miastoRodzinne) {
		Miasto stareMiasto = this.getMiastoRodzinne();
		this.miastoRodzinne = miastoRodzinne;
		miastoRodzinne.getMieszkancy().add(this);
		stareMiasto.getMieszkancy().remove(this);
		OknoGlowne.chcialbymOdswiezyc(miastoRodzinne);
		OknoGlowne.chcialbymOdswiezyc(stareMiasto);
	}

	/**
	 * Czy cywil ma byc pokazany na planszy
	 * @return the doPokazania
	 */
	public boolean isDoPokazania() {
		return doPokazania;
	}

	/**
	 * @param doPokazania the doPokazania to set
	 */
	public void setDoPokazania(boolean doPokazania) {
		this.doPokazania = doPokazania;
	}

	/**
	 * @return the chcialbymSemafor
	 */
	public boolean isChcialbymSemafor() {
		return chcialbymSemafor;
	}
}

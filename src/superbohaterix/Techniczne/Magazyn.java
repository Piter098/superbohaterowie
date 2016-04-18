/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Techniczne;

import superbohaterix.Fizyczne.Skrzyzowanie;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import superbohaterix.Fizyczne.*;

/**
 * Wszystko zwiazane z przechowywaniem kolejek, grafik i opcji<br>
 * Nazwałem tę klasę tak dlatego, że ta nazwa najlepiej odwzorowuje jej cel
 *
 * @author Piter
 */
public class Magazyn {

	private static final Image obrazekCywila = new Image("grafiki/ikony/cywil.png");
	private static final Image obrazekCywilaS = new Image("grafiki/ikony/cywilStop.png");
	private static final Image obrazekCywilaP = new Image("grafiki/ikony/cywilPost.png");
	private static final Image obrazekBohatera = new Image("grafiki/ikony/bohater.png");
	private static final Image obrazekZloczyncy = new Image("grafiki/ikony/zloczynca.png");
	private static final Image obrazekUltraBohatera = new Image("grafiki/ikony/ubohater.png");
	private static final Image obrazekUltraZloczyncy = new Image("grafiki/ikony/uzloczynca.png");
	private static final Image obrSwZie = new Image("grafiki/ikony/sZie.png");
	private static final Image obrSwZol = new Image("grafiki/ikony/sZol.png");
	private static final Image obrSwCze = new Image("grafiki/ikony/sCze.png");
	private static final Image obrSwZol2 = new Image("grafiki/ikony/sZol2.png");
	private static final Image grafikaPrezydenta = new Image("grafiki/obrazy/prez.png", 128, 128, false, false);

	private static final Color kolorS = new Color(0.4, 0.2, 0, 1);
	private static final Color kolor = new Color(1, 0.95, 0.8, 1);
	private static final Pane pane = new Pane();
	private static Swiat swiat;
	
	private static boolean pauza = false;
	private static boolean postoje = true;
	private static boolean spawnWrogow = true;
	private static boolean opuszczanieMiast = true;
	//Suma potencjalow z poszczegolnych miast
	private static final int[] wartosciZrodel = new int[6];

	private static final ArrayList<Miasto> aMiasta = new ArrayList<>();
	private static final ArrayList<Cywil> aC = new ArrayList<>();
	private static final ArrayList<Nadczlowiek> aN = new ArrayList<>();
	private static final ArrayList<Bohater> aB = new ArrayList<>();
	private static final ArrayList<Zloczynca> aZ = new ArrayList<>();
	private static final ArrayList<Skrzyzowanie> aSkrzyzowania = new ArrayList<>();
	private static final ArrayList<Cywil> doUsunieciaC = new ArrayList<>();
	private static final ArrayList<Nadczlowiek> doUsunieciaN = new ArrayList<>();
	private static final ArrayList<Miasto> doUsunieciaMiasta = new ArrayList<>();

	
	/**
	 * Losuje miasto z listy miast
	 * @return losowe miasto lub nic jesli nie ma juz zadnego miasta
	 */
	public static Miasto losujMiasto() {
		if (aMiasta.size() > 0) {
			return aMiasta.get(((int) (Math.random() * 100)) % aMiasta.size());
		}
		Wynik.przegraj();
		return null;
	}

	/**
	 * @return the obrazekCywila
	 */
	public static Image getObrazekCywila() {
		return obrazekCywila;
	}

	/**
	 * @return lista miast
	 */
	public static ArrayList<Miasto> getMiasta() {
		return aMiasta;
	}

	/**
	 * @return lista cywilow
	 */
	public static ArrayList<Cywil> getC() {
		return aC;
	}

	/**
	 * @return lista nadludzi
	 */
	public static ArrayList<Nadczlowiek> getN() {
		return aN;
	}

	/**
	 * @return lista bohaterow
	 */
	public static ArrayList<Bohater> getB() {
		return aB;
	}

	/**
	 * @return lista zloczyncow
	 */
	public static ArrayList<Zloczynca> getZ() {
		return aZ;
	}

	/**
	 * @return lista skrzyzowan
	 */
	public static ArrayList<Skrzyzowanie> getSkrzyzowania() {
		return aSkrzyzowania;
	}

	/**
	 * Kaze zmienic sygnalizacje na kazdym skrzyzowaniu
	 */
	public static void swiatla() {
		for (Skrzyzowanie o : aSkrzyzowania) {
			o.zmienSygnalizacje();
		}
	}

	/**
	 * @return obrazek swiatel - Zielone
	 */
	public static Image getObrSwZie() {
		return obrSwZie;
	}

	/**
	 * @return obrazek swiatel - Zolte
	 */
	public static Image getObrSwZol() {
		return obrSwZol;
	}

	/**
	 * @return obrazek swiatel - Czerwone
	 */
	public static Image getObrSwCze() {
		return obrSwCze;
	}

	/**
	 * @return obrazek swiatel - Zolte2
	 */
	public static Image getObrSwZol2() {
		return obrSwZol2;
	}

	/**
	 * @return kolor tekstu
	 */
	public static Color getKolor() {
		return kolor;
	}

	/**
	 * @return pane - lista elementow na planszy
	 */
	public static Pane getPane() {
		return pane;
	}

	/**
	 * @return obiekt Swiat
	 */
	public static Swiat getSwiat() {
		return swiat;
	}

	/**
	 * @param aSwiat ustawia zmienna swiat
	 */
	public static void setSwiat(Swiat aSwiat) {
		swiat = aSwiat;
	}

	/**
	 * @return the obrazekBohatera
	 */
	public static Image getObrazekBohatera() {
		return obrazekBohatera;
	}

	/**
	 * @return the obrazekZloczyncy
	 */
	public static Image getObrazekZloczyncy() {
		return obrazekZloczyncy;
	}

	/**
	 * @return lista cywilow do usuniecia z listy aC
	 */
	public static ArrayList<Cywil> getDoUsunieciaC() {
		return doUsunieciaC;
	}

	/**
	 * @return lista nadludzi do usuniecia z listy aN
	 */
	public static ArrayList<Nadczlowiek> getDoUsunieciaN() {
		return doUsunieciaN;
	}

	/**
	 * @return the obrazekUltraBohatera
	 */
	public static Image getObrazekUltraBohatera() {
		return obrazekUltraBohatera;
	}

	/**
	 * @return the obrazekUltraZloczyncy
	 */
	public static Image getObrazekUltraZloczyncy() {
		return obrazekUltraZloczyncy;
	}

	/**
	 * @return the obrazekCywila - Stoi
	 */
	public static Image getObrazekCywilaS() {
		return obrazekCywilaS;
	}

	/**
	 * @return the obrazekCywila - Postoj
	 */
	public static Image getObrazekCywilaP() {
		return obrazekCywilaP;
	}

	/**
	 * @return lista miast do usuniecia z listy aMiasta
	 */
	public static ArrayList<Miasto> getDoUsunieciaMiasta() {
		return doUsunieciaMiasta;
	}

	/**
	 * @return czy jest pauza
	 */
	public static boolean isPauza() {
		return pauza;
	}

	/**
	 * @param aPauza the pauza to set
	 */
	public static void setPauza(boolean aPauza) {
		pauza = aPauza;
	}

	/**
	 * @return the grafikaPrezydenta
	 */
	public static Image getGrafikaPrezydenta() {
		return grafikaPrezydenta;
	}

	/**
	 * @return czy zezwolono na postoje
	 */
	public static boolean isPostoje() {
		return postoje;
	}

	/**
	 * @return czy zezwolono na spawn wrogow
	 */
	public static boolean isSpawnWrogow() {
		return spawnWrogow;
	}

	/**
	 * @param aPostoje the postoje to set
	 */
	public static void setPostoje(boolean aPostoje) {
		postoje = aPostoje;
	}

	/**
	 * @param aSpawnWrogow the spawnWrogow to set
	 */
	public static void setSpawnWrogow(boolean aSpawnWrogow) {
		spawnWrogow = aSpawnWrogow;
	}

	/**
	 * @return czy zezwolono na  opuszczanie miast
	 */
	public static boolean isOpuszczanieMiast() {
		return opuszczanieMiast;
	}

	/**
	 * @param aOpuszczanieMiast the opuszczanieMiast to set
	 */
	public static void setOpuszczanieMiast(boolean aOpuszczanieMiast) {
		opuszczanieMiast = aOpuszczanieMiast;
	}

	/**
	 * @return kolor obramowki tekstu
	 */
	public static Color getKolorS() {
		return kolorS;
	}

	/**
	 * @param i
	 * @return potencjal danego zrodla
	 */
	public static int getWartosciZrodel(int i) {
		return wartosciZrodel[i];
	}

	/**
	 * Ustala tablice wartosci zrodel jako sumy potencjalow ze wszystkich miast
	 */
	public static void setWartosciZrodel() {
		for (Miasto o : aMiasta){
			for (ZrodloMocy z : o.getZrodla()){
				wartosciZrodel[z.getId()] = z.getPotencjal();
			}
		}
	}
}

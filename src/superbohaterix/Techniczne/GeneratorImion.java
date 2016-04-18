/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Techniczne;

/**
 * Unikalny generator imoin
 *
 * @author Piter
 */
public class GeneratorImion {

	private static String imie;

	//IMIONA
	private static String imionaMeskie[] = {"Wojciech", "Marcin", "Janusz", "Andrzej", "Grzegorz", "Marek", "Dariusz", "Dawid", "Maciej", "Tomasz", "Mateusz", "Artur", "Adam", "Michał", "Łukasz", "Hans", "Steven", "Stefan", "Paweł", "Filip"};
	private static String imionaBohaterow[] = {"Bohater", "Wyzwoliciel", "Heros", "Dobroczyńca", "Obrońca", "Prorok", "Wieszcz", "Wędrowiec", "Jasnowidz", "Cherubin", "Centurion", "Zdobywca", "Kapłan", "Teurg", "Mędrzec", "Wojownik", "Strażnik", "Czempion", "Templariusz", "Paladyn", "Rycerz"};
	private static String imionaZloczyncow[] = {"Niszczyciel", "Śmiercionośca", "Morderca", "Złodziej", "Parszywiec", "Korsarz", "Łowca", "Zaklinacz", "Okultysta", "Aryjczyk", "Płomień", "Karmazyn", "Sztylet", "Upadły", "Cień", "Worteks", "Obliwion", "Akolita", "Czart", "Pasożyt", "Szakal", "Lisz", "Talos", "Żmij", "Piromanta"};

	private static String imionaZenskie[] = {"Anna", "Maria", "Aneta", "Jadwiga", "Helena", "Jolanta", "Marta", "Sylwia", "Karolina", "Julia", "Emilia", "Katarzyna", "Dagmara", "Laura", "Emily", "Lucy"};

	//FIXXY
	private static String tytulB[] = {"Super", "Uber", "Mega", "Kapitan", "Profesor", "Generał", "Doktor", "Kanclerz", "Mistrz"};
	private static String tytulZ[] = {"Super", "Uber", "Kapitan", "Profesor", "Generał", "Doktor", "Lord", "Hrabia", "Kanclerz", "Imperator", "Baron", "Don", "Mistrz", "Car"};

	private static String prefixB[] = {"Bohaterski", "Wybitny", "Wspaniały", "Niesamowity", "Szlachenty", "Nieskazitelny", "Elokwentny", "Dumny"};

	private static String prefixZ[] = {"Niepokonany", "Niepowstrzymany", "Laserowy", "Tytanowy", "Szkarłatny"};
	
	//NAZWISKA
	private static String nazwiskaOdmienialne[] = {"Malinowsk", "Kozłowsk", "Kowalsk", "Wiśniewsk", "Komarowsk", "Zielińsk", "Dąbrowsk", "Lewandowsk", "Wojciechowsk"};
	private static String nazwiska[] = {"Nowak", "Włodarczyk", "Wieczorek", "Zimmermann", "Smith", "Goldbaum", "Owens", "Muller", "Krawczyk", "Kaczmarek", "Woźniak", "Kowalczyk", "Wójcik", "Jóźwiak", "Białek", "Williams", "Hughes", "Logan", "Taylor", "Anderson", "Schultz", "Krause", "Braun", "Cohenbaum", "Buchman"};

	/**
	 * Generator imion dla bohaterow
	 *
	 * @return imie
	 */
	public static String getBohater() {

		String prefiks = ((int) (Math.random() * 5) != 0) ? (prefixB[(int) (Math.random() * prefixB.length)]) + " " : "";
		String suffiks1 = ((int) (Math.random() * 3) == 0) ? "man" : "";
		//String suffiks2 = ((int)(Math.random()*4)==0) ? "zwany "+(suffixB[(int) (Math.random()*suffixZ.length)]) : "";
		String suffiks2 = ((int) (Math.random() * 3) == 0) ? " zwany " + (prefixB[(int) (Math.random() * prefixB.length)]) + "m" : "";
		String tytul = ((int) (Math.random() * 7) == 0) ? (tytulB[(int) (Math.random() * tytulB.length)]) + " " : "";

		int losujImie = (int) (Math.random() * 3);
		if (losujImie == 0) {
			imie = imionaBohaterow[(int) (Math.random() * imionaBohaterow.length)];
		}
		else if (losujImie == 1) {
			imie = getNazwisko(0);
		}
		else {
			imie = imionaMeskie[(int) (Math.random() * imionaMeskie.length)];
		}

		imie = prefiks + tytul + imie + suffiks1 + suffiks2;

		return imie;
	}

	/**
	 * Generator imion dla zloczyncow
	 *
	 * @return imie
	 */
	public static String getZloczynca() {

		String prefiks = ((int) (Math.random() * 3) != 0) ? (prefixZ[(int) (Math.random() * prefixZ.length)]) + " " : "";
		String suffiks1 = ((int) (Math.random() * 3) == 0) ? "man" : "";
		//String suffiks2 = ((int)(Math.random()*3)!=0) ? "zwany "+(suffixZ[(int) (Math.random()*suffixZ.length)]) : "";
		String suffiks2 = ((int) (Math.random() * 3) == 0) ? " zwany " + (prefixZ[(int) (Math.random() * prefixZ.length)]) + "m" : "";
		String tytul = ((int) (Math.random() * 7) == 0) ? (tytulZ[(int) (Math.random() * tytulZ.length)]) + " " : "";

		int losujImie = (int) (Math.random() * 3);
		if (losujImie == 0) {
			imie = imionaZloczyncow[(int) (Math.random() * imionaZloczyncow.length)];
		}
		else if (losujImie == 1) {
			imie = getNazwisko(0);
		}
		else {
			imie = imionaMeskie[(int) (Math.random() * imionaMeskie.length)];
		}

		imie = prefiks + tytul + imie + suffiks1 + suffiks2;

		return imie;
	}

	/**
	 * Generator imion dla cywilow
	 * @param plec
	 * @return imie
	 */
	public static String getImie(int plec) {
		imie = (plec == 0) ? imionaMeskie[(int) (Math.random() * imionaMeskie.length)] : imionaZenskie[(int) (Math.random() * imionaZenskie.length)];
		if ((int) (Math.random() * 10) == 0) {
			imie += (plec == 0) ? " " + imionaMeskie[(int) (Math.random() * imionaMeskie.length)] : " " + imionaZenskie[(int) (Math.random() * imionaZenskie.length)];
		}
		return imie;
	}

	/**
	 * Generator nazwisk dla cywilow
	 * @param plec
	 * @return nazwisko
	 */
	public static String getNazwisko(int plec) {
		String suffix = (plec == 0) ? "i" : "a";
		if ((int) (Math.random() * 2) == 0) {
			imie = nazwiskaOdmienialne[(int) (Math.random() * nazwiskaOdmienialne.length)] + suffix;
		}
		else {
			imie = nazwiska[(int) (Math.random() * nazwiska.length)];
		}

		return imie;
	}

}

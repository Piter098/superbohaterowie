/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Fizyczne;

import superbohaterix.Enumeratory.kierunki;
import superbohaterix.OknoGlowne;

/**
 * Tablice przechowujace pozycje obiektow na ulicach
 * @author Piter
 */
public class PoleNaUlicy {
    private volatile static boolean[][] zajete = new boolean [OknoGlowne.getRozmX()][OknoGlowne.getRozmY()];
    private volatile static kierunki[][] kierunek = new kierunki [OknoGlowne.getRozmX()][OknoGlowne.getRozmY()];
    private volatile static int[][] skrzyzowanie = new int [OknoGlowne.getRozmX()][OknoGlowne.getRozmY()];

    /**
     * @return Czy dane pole jest zajete
     * @param x pozycja X
     * @param y pozycja Y
     */
    public synchronized static boolean isZajete(int x,int y) {
        return zajete[x][y];
    }

    /**
     * @return Kierunek danego pola
     * @param x pozycja X
     * @param y pozycja Y
     */
    public synchronized static kierunki getKierunek(int x,int y) {
        return kierunek[x][y];
    }

    /**
     * @param zajete ustawia zajetosc pola
     * @param x pozycja X
     * @param y pozycja Y
     */
    public synchronized static void setZajete(boolean zajete,int x,int y) {
        PoleNaUlicy.zajete[x][y] = zajete;
    }

    /**
     * @param kierunek ustawia kierunek pola
     * @param x pozycja X
     * @param y pozycja Y
     */
    public synchronized static void setKierunek(kierunki kierunek,int x, int y) {
        PoleNaUlicy.kierunek[x][y] = kierunek;
    }

    /**
     * @return id skrzyzowania na danym polu
	 * <br>-1 - "wolne", -2 = "nie wolno wjezdzac", więsze równe 0 - "id skrzyzowania"
	 * @param x
	 * @param y
     */
    public synchronized static int getSkrzyzowanie(int x,int y) {
        return skrzyzowanie[x][y];
    }

    /**
     * @param wartosc ustawia id skrzyzowania
	 * <br>-1 - "wolne", -2 = "nie wolno wjezdzac", więsze równe 0 - "id skrzyzowania"
     * @param x pozycja X
     * @param y pozycja Y
     */
    public synchronized static void setSkrzyzowanie(int wartosc,int x, int y) {
        PoleNaUlicy.skrzyzowanie[x][y] = wartosc;
    }
}

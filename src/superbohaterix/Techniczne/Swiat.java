/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Techniczne;

import superbohaterix.Fizyczne.Skrzyzowanie;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import superbohaterix.Enumeratory.kierunki;
import superbohaterix.Fizyczne.Bohater;
import superbohaterix.Fizyczne.Cywil;
import superbohaterix.Fizyczne.Miasto;
import superbohaterix.Fizyczne.PoleNaUlicy;
import superbohaterix.Fizyczne.Zloczynca;
import superbohaterix.OknoGlowne;

/**
 * Wszystko zwiazane scisle ze swiatem gry
 *
 * @author Piter
 */
public final class Swiat {

	private int rozmX;
	private int rozmY;
	private Miasto stolica;

	/**
	 * Tworzy cywila i dodaje go do listy obiektów wyświetlanych na mapie.
	 * Jednocześnie zapewnia, że usunięcie go spowoduje jego zniknięcie z
	 * mapy<br>
	 * Event Handler pozwala na manipulację wybranym człowiekiem
	 *
	 * @param m miasto rodzinne
	 */
	public void tworzCywila(Miasto m) {
		Cywil cywil = new Cywil(((int) (Math.random() * 10)) % 2, m);
		Magazyn.getC().add(cywil);

		ImageView iV = new ImageView(cywil.getGrafika());

		cywil.setId(Magazyn.getPane().getChildren().indexOf(iV));
		cywil.setFizycznyObrazek(iV);

		iV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					//Wyswietla obiekt na panelu
					ObslugaPanelu.setWybrany(cywil);
					ObslugaPanelu.odswierzPanel(cywil);
				}
			}
		});

		//Nowy watek
		Thread t = new Thread(cywil);
		cywil.setWskaznikWatku(t);
		t.setDaemon(true);
		t.start();

		OknoGlowne.chcialbymOdswiezyc(null);
	}

	/**
	 * Tworzy bohatera i dodaje go do listy obiektów wyświetlanych na mapie.
	 * Jednocześnie zapewnia, że usunięcie go spowoduje jego zniknięcie z
	 * mapy<br>
	 * Event Handler pozwala na manipulację wybranym bohaterem
	 *
	 * @param stolica
	 */
	public void tworzBohatera(Miasto stolica) {
		Bohater bohater = new Bohater(stolica);
		Magazyn.getN().add(bohater);
		Magazyn.getB().add(bohater);

		ImageView iV = new ImageView(bohater.getGrafika());
		Magazyn.getPane().getChildren().add(iV);

		bohater.setId(Magazyn.getPane().getChildren().indexOf(iV));
		bohater.setFizycznyObrazek(iV);

		iV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					//Wyswietla obiekt na panelu
					ObslugaPanelu.setWybrany(bohater);
					ObslugaPanelu.odswierzPanel(bohater);
				}
			}
		});

		//Nowy watek
		Thread t = new Thread(bohater);
		bohater.setWskaznikWatku(t);
		t.setDaemon(true);
		t.start();

		OknoGlowne.chcialbymOdswiezyc(null);
	}

	/**
	 * Tworzy zloczynce i dodaje go do listy obiektów wyświetlanych na mapie.
	 * Jednocześnie zapewnia, że usunięcie go spowoduje jego zniknięcie z
	 * mapy<br>
	 * Event Handler pozwala na manipulację wybranym zloczynca
	 */
	public void tworzZloczynce() {
		Zloczynca zloczynca = new Zloczynca();
		Magazyn.getN().add(zloczynca);
		Magazyn.getZ().add(zloczynca);

		ImageView iV = new ImageView(zloczynca.getGrafika());
		Magazyn.getPane().getChildren().add(iV);

		zloczynca.setId(Magazyn.getPane().getChildren().indexOf(iV));
		zloczynca.setFizycznyObrazek(iV);

		iV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					//Wyswietla obiekt na panelu
					ObslugaPanelu.setWybrany(zloczynca);
					ObslugaPanelu.odswierzPanel(zloczynca);
				}
			}
		});

		//Nowy watek
		Thread t = new Thread(zloczynca);
		zloczynca.setWskaznikWatku(t);
		t.setDaemon(true);
		t.start();

		OknoGlowne.chcialbymOdswiezyc(null);
	}

	/**
	 * Tworzy miasto i dodaje go do listy obiektów wyświetlanych na mapie.
	 * Jednocześnie zapewnia, że usunięcie go spowoduje jego zniknięcie z
	 * mapy<br>
	 * Event Handler pozwala na manipulację wybranym miastem
	 *
	 * @param nazwa
	 * @param x
	 * @param y
	 * @param id
	 * @param wjX wjazd x
	 * @param wjY wjazd y
	 * @param wyX wyjazd x
	 * @param wyY wyjazd y
	 * @param offX przesuniecie na mapie x
	 * @param offY przesuniecie na mapie y
	 */
	public void tworzMiasto(String nazwa, int id, int x, int y, int wjX, int wjY, int wyX, int wyY, int offX, int offY) {
		Miasto miasto = new Miasto(nazwa, id, x, y, wjX, wjY, wyX, wyY);
		Magazyn.getMiasta().add(miasto);

		ImageView iV = new ImageView(miasto.getGrafika());

		Magazyn.getPane().getChildren().add(iV);
		iV.relocate(miasto.getX() * 32 + offX, miasto.getY() * 32 + offY);

		miasto.setId(Magazyn.getPane().getChildren().indexOf(iV));
		miasto.setFizycznyObrazek(iV);

		iV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					//Wyswietla obiekt na panelu
					ObslugaPanelu.setWybrany(miasto);
					ObslugaPanelu.odswierzPanel(miasto);
				}
			}
		});
		OknoGlowne.chcialbymOdswiezyc(null);
	}

	/**
	 * Tworzy skrzyzowanie i dodaje go do listy obiektów wyświetlanych na mapie.
	 * Jednocześnie zapewnia, że usunięcie go spowoduje jego zniknięcie z
	 * mapy<br>
	 * Event Handler pozwala na manipulację wybranym skrzyzowaniem
	 *
	 * @param x
	 * @param y
	 */
	public void tworzSkrzyzowanie(int x, int y) {
		//Magazyn.getSkrzyzowania().add(new Skrzyzowanie(i * 6, j * 6, Magazyn.getSkrzyzowania().size()));
		Skrzyzowanie skrzyzowanie = new Skrzyzowanie(x, y, Magazyn.getSkrzyzowania().size());
		Magazyn.getSkrzyzowania().add(skrzyzowanie);

		ImageView iV = new ImageView(skrzyzowanie.getGrafika());

		Magazyn.getPane().getChildren().add(iV);
		iV.relocate(skrzyzowanie.getX() * 32 - 32, skrzyzowanie.getY() * 32 - 32);
		skrzyzowanie.setFizycznyObrazek(iV);

		iV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					//Wyswietla obiekt na panelu
					ObslugaPanelu.setWybrany(skrzyzowanie);
					ObslugaPanelu.odswierzPanel(skrzyzowanie);
				}
			}
		});

	}

	/**
	 * Konstruktor swiata<br>Tworzy ulice, skrzyzowania i miasta
	 *
	 * @param rozmX szerokosc w pikselach
	 * @param rozmY wysokosc w pikselach
	 */
	public Swiat(int rozmX, int rozmY) {
		this.rozmX = rozmX;
		this.rozmY = rozmY;
		//System.out.println("Konstruktor swiata 1.0!");
		for (int i = 0; i < getRozmX(); i++) {
			for (int j = 0; j < getRozmY(); j++) {
				PoleNaUlicy.setZajete(false, i, j);
				PoleNaUlicy.setSkrzyzowanie(-1, i, j);
			}
		}
		//<editor-fold defaultstate="collapsed" desc="Ulice">
		for (int i = 0; i < getRozmX(); i++) {
			for (int j = 0; j < getRozmY(); j++) {
				PoleNaUlicy.setKierunek(kierunki.N, i, j);
				PoleNaUlicy.setZajete(true, i, j);
			}
		}
		for (int i = 1; i <= 3; i++) {
			for (int j = 3; j <= 16; j++) {
				PoleNaUlicy.setKierunek(kierunki.S, i * 6, j);
				PoleNaUlicy.setZajete(false, i * 6, j);
			}
		}
		for (int i = 1; i <= 3; i++) {
			for (int j = 3; j <= 16; j++) {
				PoleNaUlicy.setKierunek(kierunki.N, i * 6 + 1, j);
				PoleNaUlicy.setZajete(false, i * 6 + 1, j);
			}
		}
		for (int i = 1; i <= 2; i++) {
			for (int j = 3; j <= 22; j++) {
				PoleNaUlicy.setKierunek(kierunki.W, j, i * 6);
				PoleNaUlicy.setZajete(false, j, i * 6);
			}
		}
		for (int i = 1; i <= 2; i++) {
			for (int j = 3; j <= 22; j++) {
				PoleNaUlicy.setKierunek(kierunki.E, j, i * 6 + 1);
				PoleNaUlicy.setZajete(false, j, i * 6 + 1);
			}
		}
        //</editor-fold>

		//<editor-fold defaultstate="collapsed" desc="Skrzyzowania">
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 2; j++) {
				//PoleNaUlicy.setKierunek(kierunki.SW, i*6, j*6);
				tworzSkrzyzowanie(i * 6, j * 6);
				//Magazyn.getSkrzyzowania().add(new Skrzyzowanie(i * 6, j * 6, Magazyn.getSkrzyzowania().size()));
			}
		}

		////////
		//Pasy do zawracania
		for (int i = 1; i <= 3; i++) {
			PoleNaUlicy.setKierunek(kierunki.NW, i * 6 + 1, 4);
		}
		for (int i = 1; i <= 3; i++) {
			PoleNaUlicy.setKierunek(kierunki.SE, i * 6, 15);
		}
		for (int i = 1; i <= 2; i++) {
			PoleNaUlicy.setKierunek(kierunki.NE, 21, i * 6 + 1);
		}
		for (int i = 1; i <= 2; i++) {
			PoleNaUlicy.setKierunek(kierunki.SW, 4, i * 6);
		}
		///
		for (int i = 1; i <= 3; i++) {
			PoleNaUlicy.setKierunek(kierunki.W, i * 6 + 1, 3);
		}
		for (int i = 1; i <= 3; i++) {
			PoleNaUlicy.setKierunek(kierunki.E, i * 6, 16);
		}
		for (int i = 1; i <= 2; i++) {
			PoleNaUlicy.setKierunek(kierunki.N, 22, i * 6 + 1);
		}
		for (int i = 1; i <= 2; i++) {
			PoleNaUlicy.setKierunek(kierunki.S, 3, i * 6);
		}
        //</editor-fold>

		//<editor-fold defaultstate="collapsed" desc="Tworzenie miast">
		tworzMiasto("Północne Lynnwood", 1, 6, 2, 7, 3, 6, 3, -8, -18);
		tworzMiasto("Drobin", 2, 12, 2, 13, 3, 12, 3, -8, -18);
		tworzMiasto("Łaszczów", 3, 18, 2, 19, 3, 18, 3, -8, -18);
		tworzMiasto("Welshpool", 4, 2, 6, 3, 6, 3, 7, -18, -8);
		tworzMiasto("Cape Town", 5, 22, 6, 22, 7, 22, 6, 0, -8);
		tworzMiasto("Braniewo", 6, 2, 12, 3, 12, 3, 13, -18, -8);
		tworzMiasto("Ziębice", 7, 22, 12, 22, 13, 22, 12, 0, -8);
		tworzMiasto("Ożarów", 8, 6, 16, 6, 16, 7, 16, -12, 0);
		tworzMiasto("Volunruud", 9, 12, 16, 12, 16, 13, 16, -8, 0);
		tworzMiasto("Tyczyn", 10, 18, 16, 18, 16, 19, 16, -8, 0);
		setStolica(Magazyn.getMiasta().get(8));
		//</editor-fold>

		for (int i = 0; i < 6; i++) {
			tworzCywila(Magazyn.losujMiasto());
		}

		//Wyswietla informacje o swiecie na panelu
		ObslugaPanelu.setWybrany(null);
		ObslugaPanelu.odswierzPanel(null);
	}

	/**
	 * @return rozmiar swiata x
	 */
	public int getRozmX() {
		return rozmX;
	}

	/**
	 * @return rozmiar swiata y
	 */
	public int getRozmY() {
		return rozmY;
	}

	/**
	 * @return the stolica
	 */
	public Miasto getStolica() {
		return stolica;
	}

	/**
	 * @param rozmX the rozmX to set
	 */
	public void setRozmX(int rozmX) {
		this.rozmX = rozmX;
	}

	/**
	 * @param rozmY the rozmY to set
	 */
	public void setRozmY(int rozmY) {
		this.rozmY = rozmY;
	}

	/**
	 * Wybiera nowa stolice
	 *
	 * @param stolica the stolica to set
	 */
	public void setStolica(Miasto stolica) {
		if (this.stolica != null) {
			this.stolica.setCzyStolica(false);
		}
		this.stolica = stolica;
		stolica.setCzyStolica(true);
		OknoGlowne.chcialbymOdswiezyc(stolica);
		//OknoGlowne.chcialbymOdswiezyc(null);
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix;

import superbohaterix.Fizyczne.Skrzyzowanie;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import superbohaterix.Enumeratory.*;
import superbohaterix.Fizyczne.*;
import superbohaterix.Techniczne.*;

/**
 * Romiar swiata podany jest w blokach 32x32
 *
 * @author Piter
 */
public class OknoGlowne extends Application {

	private static final int rozmX = 26;
	private static final int rozmY = 20;
	private static PunktNaMapie chetnyDoOdswiezenia;
	private String pozycjaKursora = "";

	/**
	 * @return the rozmX - Szerokosc mapy w blokach
	 */
	public static int getRozmX() {
		return rozmX;
	}

	/**
	 * @return the rozmY - Wysokosc mapy w blokach
	 */
	public static int getRozmY() {
		return rozmY;
	}

	/**
	 * Obsluga requestu odwiezenia panelu
	 *
	 * @param p requestujacy obiekt
	 */
	public static void chcialbymOdswiezyc(PunktNaMapie p) {
		if (p != null) {
			//jesli obecny panel pokazuje requestowany obiekt to odswieza panel
			if (ObslugaPanelu.getWybrany() == p) {
				chetnyDoOdswiezenia = p;
			}
		}
		//Jesli parametr jest rowny null to pokazuje domyslny panel
		else {
			ObslugaPanelu.odswierzPanel(chetnyDoOdswiezenia);
		}
	}

	private Canvas map;

	/**
	 * Główny element programu<br>Tutaj uruchamiane są najważniejsze elementy
	 *
	 * @param primaryStage okno glowne programu
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
		loader.load();
		Group elementyPanelu = new Group();
		elementyPanelu.relocate(rozmX * 32, 0);

		//Przycisk pauzy
		Button pauza = new Button();
		ImageView play = new ImageView(new Image("grafiki/ikony/play.png"));
		ImageView pause = new ImageView(new Image("grafiki/ikony/pause.png"));
		if (Magazyn.isPauza()) {
			pauza.setGraphic(play);
		}
		else {
			pauza.setGraphic(pause);
		}

		superbohaterix.FXMLDocumentController controller = (superbohaterix.FXMLDocumentController) loader.getController();
		this.map = controller.getMap();

		GraphicsContext context = this.map.getGraphicsContext2D();
		//GraphicsContext panel = this.sidePanel.getGraphicsContext2D();
		Magazyn.getPane().getChildren().addAll(map, elementyPanelu, pauza);

		//tworzenie swiata
		Swiat swiat = new Swiat(getRozmX(), getRozmY());
		Magazyn.setSwiat(swiat);

		//jesli gracz kliknie na pustym polu to wyswietlany jest domyslny panel informacyjny
		this.map.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					//Wyswietla informacje o swiecie na panelu
					ObslugaPanelu.setWybrany(null);
					ObslugaPanelu.odswierzPanel(null);
				}
			}
		});

		//Wyswietlanie informacji o danym polu na mapie (tylko do celow informacyjnych)
		this.map.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getSceneX() < rozmX * 32 && event.getSceneY() < rozmY * 32) {
					pozycjaKursora
							= (int) event.getSceneX() / 32 + "x" + (int) event.getSceneY() / 32 + " - "
							+ PoleNaUlicy.getKierunek((int) event.getSceneX() / 32, (int) event.getSceneY() / 32)
							+ " (" + PoleNaUlicy.isZajete((int) event.getSceneX() / 32, (int) event.getSceneY() / 32) + ")";
				}
				else {
					pozycjaKursora = "Poza zakresem";
				}
			}
		});

		//co ma robic klikniecie przycisku pauzy
		pauza.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (Magazyn.isPauza()) {
					Magazyn.setPauza(false);
					pauza.setGraphic(pause);
				}
				else {
					Magazyn.setPauza(true);
					pauza.setGraphic(play);
				}
			}
		});
		pauza.relocate(8, 8);

		//Tworzenie glownego okienka
		Scene scene3 = new Scene(Magazyn.getPane());
		primaryStage.setTitle("Superbohaterix");
		primaryStage.setScene(scene3);
		primaryStage.show();

		//renderowanie okienka
		Thread renderer;
		renderer = new Thread() {
			Image tloSwiata = new Image("grafiki/obrazy/swiat.png");

			/**
			 * Główny wątek programu
			 */
			@Override
			public void run() {
				//wykonuje gdy gra dziala
				while (!Wynik.isCzyPrzegrana()) {
					try {
						Thread.sleep(16);

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								if (!Wynik.isCzyPrzegrana()) {
									Wynik.addCzasGry();

									//tworzenie zloczyncy w losowym momencie
									if ((int) (Math.random() * 10000) % 700 == 0 && !Magazyn.isPauza() && Magazyn.isSpawnWrogow()) {
										Magazyn.getSwiat().tworzZloczynce();
									}

									//zmiana sygnalizacji
									if (Magazyn.isPauza() == false) {
										Magazyn.swiatla();
									}
									
									//odwiezenie panelu informacyjnego
									if (chetnyDoOdswiezenia != null) {
										ObslugaPanelu.odswierzPanel(chetnyDoOdswiezenia);
										chetnyDoOdswiezenia = null;
									}

									context.clearRect(0, 0, context.getCanvas().getWidth(), context.getCanvas().getHeight());
									//rysowanie tla
									context.drawImage(tloSwiata, 0, 0);

									//wyswietlanie informacji o polach na ulicy
									context.setStroke(Magazyn.getKolor());
									context.setTextAlign(TextAlignment.LEFT);
									context.strokeText(pozycjaKursora, 64, 16);

									//wykonane dla wszystkich miast
									for (Miasto o : Magazyn.getMiasta()) {
										o.zwiekszPotencjaly();
										
										//wyswietlanie nazwy
										context.setStroke(Magazyn.getKolorS());
										context.setFill(Magazyn.getKolor());
										context.setTextAlign(TextAlignment.CENTER);
										String nazwa = o.isCzyStolica() ? "★ " + o.getNazwa() + " ★" : o.getNazwa();
										context.strokeText(nazwa, o.getX() * 32 + 32, o.getY() * 32 - 14);
										context.fillText(nazwa, o.getX() * 32 + 32, o.getY() * 32 - 14);
									}
									for (Cywil o : Magazyn.getC()) {
										//kasowanie z planszy jesli stacjonuje lub nie zyje
										if (o.getStan() == stanRuchu.STACJONUJE) {
											Magazyn.getPane().getChildren().remove(o.getFizycznyObrazek());
										}
										else {
											//pokazywanie cywila
											if (o.isDoPokazania() == true) {

												Magazyn.getPane().getChildren().add(o.getFizycznyObrazek());
												o.setDoPokazania(false);
											}
										}
										o.getFizycznyObrazek().setImage(o.getGrafika());
										ImageView pojazd = o.getFizycznyObrazek();
										pojazd.setRotate(o.getKierunek().ordinal() * 45);
										pojazd.relocate(o.getX() * 32, o.getY() * 32);
									}

									for (Nadczlowiek o : Magazyn.getN()) {
										//kasowanie z planszy jesli stacjonuje lub nie zyje
										if (o.getStan() == stanRuchu.STACJONUJE) {
											Magazyn.getPane().getChildren().remove(o.getFizycznyObrazek());
										}
										ImageView pojazd = o.getFizycznyObrazek();
										pojazd.setRotate(o.getKierunek().ordinal() * 45);
										pojazd.relocate(o.getX() * 32, o.getY() * 32);
									}
									
									//wyswietlanie skrzyzowan
									for (Skrzyzowanie o : Magazyn.getSkrzyzowania()) {
										o.getFizycznyObrazek().setImage(o.getGrafika());
									}
									
									//Usuwanie niepotrzebnych ludzi
									for (Cywil o : Magazyn.getDoUsunieciaC()) {
										PoleNaUlicy.setZajete(false, o.getX(), o.getY());
										Magazyn.getPane().getChildren().remove(o.getFizycznyObrazek());
										//zwalnia semafor
										if (o.getSkrzyzowanie() >= 0) {
											Magazyn.getSkrzyzowania().get(o.getSkrzyzowanie()).getSemafor().release();
											o.setSkrzyzowanie(-1);
										}
										Magazyn.getC().remove(o);
										o.getMiastoRodzinne().getMieszkancy().remove(o);
										OknoGlowne.chcialbymOdswiezyc(null);
										o.getWskaznikWatku().stop();
									}
									Magazyn.getDoUsunieciaC().clear();

									//Usuwanie niepotrzebnych nadludzi
									for (Nadczlowiek o : Magazyn.getDoUsunieciaN()) {
										Magazyn.getPane().getChildren().remove(o.getFizycznyObrazek());
										Magazyn.getN().remove(o);
										if (o.isCzyBohater()) {
											Magazyn.getB().remove((Bohater) o);
										}
										else {
											Magazyn.getZ().remove((Zloczynca) o);
										}
										OknoGlowne.chcialbymOdswiezyc(null);
										o.getWskaznikWatku().stop();
									}
									Magazyn.getDoUsunieciaN().clear();

									//Usuwanie niepotrzebnych miast
									for (Miasto o : Magazyn.getDoUsunieciaMiasta()) {
										Magazyn.getPane().getChildren().remove(o.getFizycznyObrazek());
										Magazyn.getMiasta().remove(o);
										OknoGlowne.chcialbymOdswiezyc(null);
										//System.out.println("Usunieto: " + o.getNazwa());
									}
									Magazyn.getDoUsunieciaMiasta().clear();
								}
								else {
									//Koniec gry
									Wynik oknoPrzegrania = new Wynik(primaryStage);
									oknoPrzegrania.getOkno().show();
								}
							}
						});

					}
					catch (InterruptedException ex) {
					}
				}
			}
		};

		renderer.setDaemon(true);
		renderer.start();

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}

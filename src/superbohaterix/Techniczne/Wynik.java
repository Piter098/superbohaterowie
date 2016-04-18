/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Techniczne;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import superbohaterix.Fizyczne.Miasto;

/**
 * Wszystko zwiazane z zapisem wyniku gracza
 * @author Piter
 */
public class Wynik {

	private static int czasGry = 0;
	private static ArrayList<RekordWyniku> wyniki = new ArrayList<>();
	private static boolean czyPrzegrana = false;

	/**
	 * @return czy gra juz sie skonczyla
	 */
	public static boolean isCzyPrzegrana() {
		return czyPrzegrana;
	}

	/**
	 * Konczenie gry
	 */
	public static void przegraj() {
		if (!isCzyPrzegrana()) {
			boolean koniec = true;
			for (Miasto o : Magazyn.getMiasta()) {
				if (o.isIstnieje()) {
					koniec = false;
					break;
				}
			}
			if (koniec) {
				czyPrzegrana = true;
				System.err.println("Koniec gry");
			}
		}
	}

	private final Stage okno = new Stage();

	/**
	 * Konstruktor okiena, ktore wyskakuje po zakonczeniu gry
	 * @param primaryStage 
	 */
	public Wynik(Stage primaryStage) {
		Label wynikInfo = new Label("Koniec gry!\nTwoj czas: " + czasGry/60);
		wynikInfo.relocate(16, 16);
		Label wprowadzInfo = new Label("Podaj swoje imie Überprezydencie");
		wprowadzInfo.relocate(24, 58);
		TextField wprowadz = new TextField();
		wprowadz.relocate(20, 80);
		wprowadz.setPrefWidth(192);
		Button wprowadzZatwierdz = new Button("OK");
		wprowadzZatwierdz.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!wprowadz.getText().isEmpty()) {
					try {
						zapisz(wprowadz.getText());
					}
					catch (IOException ex) {
						Logger.getLogger(Wynik.class.getName()).log(Level.SEVERE, null, ex);
					}
					catch (ClassNotFoundException ex) {
						Logger.getLogger(Wynik.class.getName()).log(Level.SEVERE, null, ex);
					}
					primaryStage.close();
				}
			}
		});

		wprowadzZatwierdz.relocate(92, 120);
		wprowadzZatwierdz.setPrefWidth(48);

		Group podajImie = new Group(wynikInfo, wprowadzInfo, wprowadz, wprowadzZatwierdz);

		okno.setTitle("Koniec");
		okno.initStyle(StageStyle.UTILITY);
		okno.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				primaryStage.close();
			}
		});
		okno.setScene(new Scene(podajImie, 232, 160));
		okno.initModality(Modality.WINDOW_MODAL);
		okno.initOwner(primaryStage);
	}

	/**
	 * Dodanie do listy wynikow i zapis do xml
	 * @param tekst imie gracza
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void zapisz(String tekst) throws IOException, ClassNotFoundException {

		String nazwaPliku = "./wynik.xml";
		File file = new File(nazwaPliku);
		XStream xstream = new XStream(new DomDriver("Unicode"));

		wyniki.clear();

		//wczyt
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(nazwaPliku);
			try (BufferedReader rdr = new BufferedReader(new InputStreamReader(fis, "UTF-8"))) {
				if (fis.available() > 0) {
					ObjectInputStream in;
					in = xstream.createObjectInputStream(rdr);
					wyniki = (ArrayList<RekordWyniku>) in.readObject();
					in.close();
				}
			}
		}

		//Dodanie obecnego wyniku do listy
		wyniki.add(new RekordWyniku(tekst, czasGry / 60));
		wyniki.sort(new Comparator<RekordWyniku>() {
			@Override
			public int compare(RekordWyniku o1, RekordWyniku o2) {
				return Integer.signum(o2.getCzas() - o1.getCzas());
			}
		});
		
		//Odrzucenie ostatniego wyniku
		if (wyniki.size() > 5) {
			for (int i = 5; i < wyniki.size(); i++) {
				wyniki.remove(i);
			}
		}
		
		//zapis
		try (PrintWriter pw = new PrintWriter(nazwaPliku, "UTF-8")) {
			ObjectOutputStream out;
			
			out = xstream.createObjectOutputStream(pw, "ListaWynikow");
			out.writeObject(wyniki);
			out.close();
		}
	}

	/**
	 * Zwieksza czas gry o jeden takt (jest 60 taktow na sekunde)
	 */
	public static void addCzasGry() {
		czasGry++;
	}

	/**
	 * @return okno
	 */
	public Stage getOkno() {
		return okno;
	}

}

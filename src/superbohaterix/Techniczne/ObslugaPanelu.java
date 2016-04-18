/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Techniczne;

import superbohaterix.Fizyczne.Skrzyzowanie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import superbohaterix.Enumeratory.stanRuchu;
import superbohaterix.Fizyczne.Bohater;
import superbohaterix.Fizyczne.Cywil;
import superbohaterix.Fizyczne.Miasto;
import superbohaterix.Fizyczne.Nadczlowiek;
import superbohaterix.Fizyczne.PoleNaUlicy;
import superbohaterix.Fizyczne.PunktNaMapie;
import superbohaterix.Fizyczne.Zloczynca;
import superbohaterix.OknoGlowne;

/**
 *
 * @author Piter
 */
public class ObslugaPanelu {

	private static PunktNaMapie wybrany = null;

	/**
	 * Panel informacyjny na temat cywila
	 * @param c cywil
	 */
	public static void wyswietlNaPanelu(Cywil c) {
		Group elementy = (Group) Magazyn.getPane().getChildren().get(1);
		elementy.getChildren().clear();
		Label label = new Label(c.getNazwa() + " " + c.getNazwisko());
		label.relocate(32, 16);
		label.setMaxWidth(128);
		label.setWrapText(true);
		Line separator = new Line(16, 54, 160, 54);

		//Zmienia stan ruchu cywila
		Button zmienStan = new Button("Zmien stan");
		zmienStan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (c.getStan() == stanRuchu.STOI || c.getStan() == stanRuchu.POSTOJ) {
					c.setStan(stanRuchu.IDZIE);
					System.out.println("Ruszam");
				}
				else if (c.getStan() == stanRuchu.IDZIE) {
					c.setStan(stanRuchu.STOI);
					if (c.isChcialbymSemafor()) {
						c.getWskaznikWatku().interrupt();
					}
				}
				OknoGlowne.chcialbymOdswiezyc(c);
			}
		});
		zmienStan.relocate(16, 64);
		
		//Zabija cywila
		Button zabij = new Button("Zabij");
		zabij.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				c.zabij();
			}
		});
		zabij.relocate(16, 96);
		Label info = new Label("Miasto rodzinne: \n   " + c.getMiastoRodzinne().getNazwa()
				+ "\nUdaje si\u0119 do miasta: \n   " + c.getCel().getNazwa()
				+ "\nAktualny stan: \n   " + c.getStan().name()
				+ "\nID skrzyżowania: " + c.getSkrzyzowanie()
				+ "\nID ulicy: " + PoleNaUlicy.getSkrzyzowanie(c.getX(), c.getY())
		);
		info.relocate(16, 128);
		info.setMaxWidth(192);
		info.setWrapText(true);
		elementy.getChildren().addAll(label, separator, zmienStan, zabij, info);
	}

	/**
	 * Panel informacyjny na temat nadczlowieka
	 * @param n nadczlowiek
	 */
	public static void wyswietlNaPanelu(Nadczlowiek n) {
		Group elementy = (Group) Magazyn.getPane().getChildren().get(1);
		elementy.getChildren().clear();
		Label label = new Label(n.getNazwa());
		Label info = new Label();
		if (n.isCzyBohater()) {
			Bohater b = (Bohater) n;
			if(b.getCelDoLikwidacji()==null){
				info.setText("Wracam do miasta\nPozostali złoczyńcy: " + Magazyn.getZ().size());
			}
			else{
				info.setText("Ścigam: "+b.getCelDoLikwidacji().getNazwa()+"\nPozostali złoczyńcy: " + Magazyn.getZ().size());
			}
		}
		else {
			info.setText("Cel: " + n.getCel().getNazwa() + "\nPozostali bohaterowie: " + Magazyn.getB().size());
		}

		info.setText(info.getText() + "\nAktualny stan: " + n.getStan().name()+
				"\n\n HP: " + n.getHp()+
				"\n Energia: " + n.getEnrg()+
				"\n Siła: " + n.getStr()+
				"\n Szybkość: " + n.getSpd()+
				"\n Inteligencja: " + n.getIntel()+
				"\n Wytrzymałość: " + n.getDef()+
				"\n Walka: " + n.getSkill()
				);

		info.relocate(16, 256);
		info.setMaxWidth(192);
		info.setWrapText(true);
		label.relocate(32, 160);
		label.setMaxWidth(128);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setWrapText(true);
		
		//Zabija nadczlowieka
		Button zabij = new Button("Zabij");
		zabij.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				n.zabij();
			}
		});
		zabij.relocate(16, 212);
		ImageView iV = new ImageView(n.getObrazekNadczlowieka());
		iV.relocate(32, 32);
		elementy.getChildren().addAll(iV, label, info, zabij);
	}

	/**
	 * Panel informacyjny na temat miasta
	 * @param m miasto
	 */
	public static void wyswietlNaPanelu(Miasto m) {
		Group elementy = (Group) Magazyn.getPane().getChildren().get(1);
		elementy.getChildren().clear();

		Label label = new Label(m.getNazwa());
		label.relocate(32, 128);
		label.setMaxWidth(128);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setWrapText(true);

		Line separator = new Line(16, 150, 160, 150);

		//Ustawia obecne miasto jako stolice
		Button ustalStolice = new Button("Ustaw jako stolicę");
		ustalStolice.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Magazyn.getSwiat().setStolica(m);
			}
		});
		ustalStolice.relocate(16, 180);

		Label info = new Label();

		String stolica;
		stolica = m.isCzyStolica() ? "[STOLICA]" : "";
		
		//Stolica wysyla bohatera
		Button deploy = new Button("Wyślij bohatera");
		deploy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (m.isCzyStolica()) {
					Magazyn.getSwiat().tworzBohatera(m);
				}
			}
		});
		deploy.relocate(16, 180);
		if (m.isCzyStolica() == false) {
			deploy.setVisible(false);
		}
		else {
			ustalStolice.setVisible(false);
		}
		
		//Miasto tworzy cywila
		Button zrobCywila = new Button("Stwórz cywila");
		zrobCywila.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Magazyn.getSwiat().tworzCywila(m);
			}
		});
		zrobCywila.relocate(16, 212);

		String zrodla = "";
		for (ZrodloMocy zr : m.getZrodla()) {
			zrodla += "\n\u2022 " + zr.getNazwa() + ": " + zr.getPotencjal();
		}

		info.setText(stolica + "\n\n\n\n\nLiczba mieszkańców: \n  " + m.getMieszkancy().size() + "\nLiczba odwiedzających: \n  " + m.getOdwiedzajacy().size() + "\nŹródła mocy:" + zrodla+
				"\n\nWjazd = "+m.getWjazd(0)+"x"+m.getWjazd(1)+
				"\nWyjazd = "+m.getWyjazd(0)+"x"+m.getWyjazd(1));
		info.relocate(16, 160);
		info.setMaxWidth(160);
		info.setWrapText(true);

		ImageView iV = new ImageView(m.getGrafika());
		iV.relocate(64, 32);

		elementy.getChildren().addAll(iV, separator, label, info, ustalStolice, deploy, zrobCywila);
	}
	
	/**
	 * Panel informacyjny na temat skrzyzowania
	 * @param s skrzyzowanie
	 */
	public static void wyswietlNaPanelu(Skrzyzowanie s) {
		Group elementy = (Group) Magazyn.getPane().getChildren().get(1);
		elementy.getChildren().clear();
		Label label = new Label("ID: " + s.getId());
		label.relocate(32, 16);
		label.setMaxWidth(128);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setWrapText(true);
		Line separator = new Line(16, 32, 160, 32);

		Label info = new Label();
		info.setText("Wolne semafory " + s.getSemafor().availablePermits()
				+ "\nTechniczna nazwa obecnego koloru swiatel: " + s.getTryb().name()
				+ "\nW kolejce po semafor: "
				+ s.getSemafor().getQueueLength());
		info.relocate(16, 38);
		info.setMaxWidth(160);
		info.setWrapText(true);

		//Zmienia stan swiatel
		Button zmien = new Button("Zmień stan");
		zmien.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				s.setCzasSwiatel(0);
			}
		});
		zmien.relocate(16, 128);

		elementy.getChildren().addAll(separator, label, info, zmien);
	}

	/**
	 * Domyslny panel informacyjny - podaje info o swiecie
	 */
	public static void wyswieltNaPanelu() {
		Group elementy = (Group) Magazyn.getPane().getChildren().get(1);
		elementy.getChildren().clear();

		//Wysylanie nadludzi
		Button deploy = new Button("Wyślij bohatera");
		deploy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Magazyn.getSwiat().tworzBohatera(Magazyn.getSwiat().getStolica());
			}
		});
		Button deploy2 = new Button("Wyślij złoczyńcę");
		deploy2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Magazyn.getSwiat().tworzZloczynce();
			}
		});

		//Zabijanie
		Button kill = new Button("Wymorduj bohaterów");
		kill.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (Bohater o : Magazyn.getB()) {
					o.zabij();
				}
			}
		});
		Button kill2 = new Button("Wymorduj złoczyńców");
		kill2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (Zloczynca o : Magazyn.getZ()) {
					o.zabij();
				}
			}
		});
		Button kill3 = new Button("Wymorduj cywilów na ulicach");
		kill3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (Cywil o : Magazyn.getC()) {
					if (o.getStan()!=stanRuchu.STACJONUJE){
						o.zabij();
					}
				}
			}
		});
		
		//Dodatkowe opcje
		CheckBox postoje = new CheckBox("Zezwalaj na postoje");
		postoje.setSelected(Magazyn.isPostoje());
		postoje.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Magazyn.setPostoje(postoje.isSelected());
			}
		});

		CheckBox spawnWrogow = new CheckBox();
		spawnWrogow.setSelected(Magazyn.isSpawnWrogow());
		spawnWrogow.setPrefWidth(160);
		spawnWrogow.setWrapText(true);
		spawnWrogow.setText("Zezwalaj na pojawianie się wrogów");
		spawnWrogow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Magazyn.setSpawnWrogow(spawnWrogow.isSelected());
			}
		});
		
		CheckBox opuszczanieMiast = new CheckBox();
		opuszczanieMiast.setSelected(Magazyn.isOpuszczanieMiast());
		opuszczanieMiast.setPrefWidth(160);
		opuszczanieMiast.setWrapText(true);
		opuszczanieMiast.setText("Zezwalaj na opuszczanie miast");
		opuszczanieMiast.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Magazyn.setOpuszczanieMiast(opuszczanieMiast.isSelected());
			}
		});
		
		Label label = new Label("Świat - panel Überprezydenta");
		label.relocate(16, 16);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setWrapText(true);

		//Informacje o swiecie
		Label info = new Label();
		String stolicaTekst = Magazyn.getSwiat() != null ? "\nStolica kraju: " + Magazyn.getSwiat().getStolica().getNazwa() : "";
		
		info.setText("Liczba cywilów: " + Magazyn.getC().size()
				+ "\nLiczba bohaterów: " + Magazyn.getB().size()
				+ "\nLiczba złoczyńców: " + Magazyn.getZ().size()
				+ "\nLiczba miast: " + Magazyn.getMiasta().size()
				+ stolicaTekst
		);
		info.relocate(16, 192);
		info.setMaxWidth(160);
		info.setWrapText(true);

		deploy.relocate(16, 300);
		deploy2.relocate(16, 332);
		kill.relocate(16, 380);
		kill2.relocate(16, 412);
		kill3.relocate(16, 444);
		postoje.relocate(16, 476);
		spawnWrogow.relocate(16, 500);
		opuszczanieMiast.relocate(16, 540);

		Line separator = new Line(16, 32, 160, 32);

		//Obraz Überprezydenta
		ImageView iV = new ImageView(Magazyn.getGrafikaPrezydenta());
		iV.relocate(24, 40);

		Label podpis = new Label("Überprezydent świata");
		podpis.relocate(24, 168);

		elementy.getChildren().addAll(iV, separator, podpis, label, info, deploy, deploy2, kill, kill2, kill3, postoje, spawnWrogow, opuszczanieMiast);
	}

	/**
	 * Odswieza panel informacyjny
	 * @param p aktualnie wybrany obiekt
	 */
	public static void odswierzPanel(PunktNaMapie p) {
		//Jesli podano argument to sprawdza jakiego obiektu dotyczy
		if (wybrany != null) {
			if (wybrany == p) {
				switch (wybrany.getClass().getSimpleName()) {
					case "Cywil":
						Cywil c = (Cywil) wybrany;
						if (c.getStan() == stanRuchu.MARTWY) {
							setWybrany(null);
							Group elementy = (Group) Magazyn.getPane().getChildren().get(1);
							elementy.getChildren().clear();
						}
						else {
							wyswietlNaPanelu(c);
						}
						break;
					case "Zloczynca":
						Nadczlowiek z = (Nadczlowiek) wybrany;
						if (z.getStan() == stanRuchu.MARTWY) {
							setWybrany(null);
							Group elementy = (Group) Magazyn.getPane().getChildren().get(1);
							elementy.getChildren().clear();
						}
						else {
							wyswietlNaPanelu(z);
						}
						break;
					case "Bohater":
						Nadczlowiek b = (Nadczlowiek) wybrany;
						if (b.getStan() == stanRuchu.MARTWY) {
							setWybrany(null);
							Group elementy = (Group) Magazyn.getPane().getChildren().get(1);
							elementy.getChildren().clear();
						}
						else {
							wyswietlNaPanelu(b);
						}
						break;
					case "Miasto":
						Miasto m = (Miasto) wybrany;
						if (m.isIstnieje() == false) {
							setWybrany(null);
							Group elementy = (Group) Magazyn.getPane().getChildren().get(1);
							elementy.getChildren().clear();
						}
						else {
							wyswietlNaPanelu(m);
						}
						break;
					case "Skrzyzowanie":
						Skrzyzowanie s = (Skrzyzowanie) wybrany;
						wyswietlNaPanelu(s);
						break;
					//Jesli nic nie pasuje to wyswietla domyslny
					default:
						wyswieltNaPanelu();
						break;
				}
			}
		}
		//Wybierany jest domyslny panel
		else {
			wyswieltNaPanelu();
		}
	}

	/**
	 * @return the wybrany
	 */
	public static PunktNaMapie getWybrany() {
		return wybrany;
	}

	/**
	 * @param aWybrany the wybrany to set
	 */
	public static void setWybrany(PunktNaMapie aWybrany) {
		wybrany = aWybrany;
	}

}

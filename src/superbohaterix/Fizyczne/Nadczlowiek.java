/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Fizyczne;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import static superbohaterix.Enumeratory.stanRuchu.MARTWY;
import superbohaterix.OknoGlowne;
import superbohaterix.Techniczne.Magazyn;

/**
 * Cechy wyrozniajace bohaterow/zloczyncow od zwyklych ludzi
 *
 * @author Piter
 */
public abstract class Nadczlowiek extends Czlowiek {

	private int hp;
	private int intel;
	private int str;
	private int spd;
	private int def;
	private int enrg;
	private int skill;
	private WritableImage obrazekNadczlowieka;
	/**
	 * To pole jest tylko dla mojej wygody
	 */
	private boolean czyBohater;
	private boolean pojedynek;

	/**
	 * Ustalenie wartosci poszczegolnych umiejetnosci, zycia itd
	 * @param mnoznik wartosc 0 - 1
	 * @param odZera czy wartosci maja zostac najpierw wyzerowane
	 */
	public final void przydzielWartosci(double mnoznik, boolean odZera) {
		if (odZera) {
			hp = 0;
			def = 0;
			str = 0;
			intel = 0;
			enrg = 0;
			spd = 0;
			skill = 0;
		}
		
		hp += (int) ((double) (100 + (int) (Math.random() * 100) % 20) * mnoznik);
		def += (int) ((double) (50 + (int) (Math.random() * 100) % 10) * mnoznik);
		str += (int) ((double) (10 + (int) (Math.random() * 10) % 5) * mnoznik);
		intel += (int) ((double) (10 + (int) (Math.random() * 10) % 5) * mnoznik);
		enrg += (int) ((double) (10 + (int) (Math.random() * 10) % 5) * mnoznik);
		spd += (int) ((double) (10 + (int) (Math.random() * 10) % 5) * mnoznik);
		skill += (int) ((double) (5 + (int) (Math.random() * 10) % 3) * mnoznik);

	}

	public Nadczlowiek() {
		przydzielWartosci(1, true);
	}

	/**
	 * Zabija biednego nadczlowieka i dodaje go do listy do usuniecia
	 */
	public void zabij() {
		setStan(MARTWY);
		setZywy(false);
		Magazyn.getDoUsunieciaN().add(this);
		OknoGlowne.chcialbymOdswiezyc(this);
	}

	/**
	 * Generator obrazka nadczlowieka - nie bylo tego w wymaganiach, ale uznalem, ze bedzie fajniej
	 */
	public void robienieObrazka() {
		Image tlo;
		Image cialo;
		
		//tlo obrazka i mundur - niebieskie = bohater, czerwone = zloczynca
		if (isCzyBohater() == true) {
			tlo = new Image("grafiki/bohaterowie/t0.png", 128, 128, true, true);
			cialo = new Image("grafiki/bohaterowie/0.png", 128, 128, true, true);
		}
		else {
			tlo = new Image("grafiki/bohaterowie/t1.png", 128, 128, true, true);
			cialo = new Image("grafiki/bohaterowie/1.png", 128, 128, true, true);
		}

		//losowanie poszczegolnych czesci twarzy
		Group g = new Group(
				new ImageView(tlo),
				new ImageView(cialo),
				new ImageView(new Image("grafiki/bohaterowie/g/" + (int) (Math.random() * 10) % 2 + ".png", 128, 128, false, false)),
				new ImageView(new Image("grafiki/bohaterowie/u/" + (int) (Math.random() * 10) % 4 + ".png", 128, 128, false, false)),
				new ImageView(new Image("grafiki/bohaterowie/n/" + (int) (Math.random() * 10) % 3 + ".png", 128, 128, false, false)),
				new ImageView(new Image("grafiki/bohaterowie/o/" + (int) (Math.random() * 10) % 4 + ".png", 128, 128, false, false)),
				new ImageView(new Image("grafiki/bohaterowie/z/" + (int) (Math.random() * 10) % 4 + ".png", 128, 128, false, false)),
				new ImageView(new Image("grafiki/bohaterowie/w/" + (int) (Math.random() * 10) % 3 + "/" + (int) (Math.random() * 10) % 7 + ".png", 128, 128, false, false))
		);
		
		//losowanie detali - okularow, masek, mieczy itd
		if ((int) (Math.random() * 2) == 1) {
			g.getChildren().add(new ImageView(new Image("grafiki/bohaterowie/d/t/" + (int) (Math.random() * 10) % 5 + ".png", 128, 128, false, false)));
		}
		if ((int) (Math.random() * 2) == 1) {
			g.getChildren().add(new ImageView(new Image("grafiki/bohaterowie/d/p/" + (int) (Math.random() * 10) % 4 + ".png", 128, 128, false, false)));
		}
		if ((int) (Math.random() * 2) == 1) {
			g.getChildren().add(new ImageView(new Image("grafiki/bohaterowie/d/o/" + (int) (Math.random() * 10) % 6 + ".png", 128, 128, false, false)));
		}
		if ((int) (Math.random() * 2) == 1) {
			g.getChildren().add(new ImageView(new Image("grafiki/bohaterowie/p/" + (int) (Math.random() * 10) % 4 + ".png", 128, 128, false, false)));
		}

		//zapis do jednego obrazka
		obrazekNadczlowieka = g.snapshot(null, null);
	}

	/**
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @return the intel
	 */
	public int getIntel() {
		return intel;
	}

	/**
	 * @return the str
	 */
	public int getStr() {
		return str;
	}

	/**
	 * @return the spd
	 */
	public int getSpd() {
		return spd;
	}

	/**
	 * @return the def
	 */
	public int getDef() {
		return def;
	}

	/**
	 * @return the enrg
	 */
	public int getEnrg() {
		return enrg;
	}

	/**
	 * @return the skill
	 */
	public int getSkill() {
		return skill;
	}

	/**
	 * @param hp the hp to set
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * @param intel the intel to set
	 */
	public void setIntel(int intel) {
		this.intel = intel;
	}

	/**
	 * @param str the str to set
	 */
	public void setStr(int str) {
		this.str = str;
	}

	/**
	 * @param spd the spd to set
	 */
	public void setSpd(int spd) {
		this.spd = spd;
	}

	/**
	 * @param def the def to set
	 */
	public void setDef(int def) {
		this.def = def;
	}

	/**
	 * @param enrg the enrg to set
	 */
	public void setEnrg(int enrg) {
		this.enrg = enrg;
	}

	/**
	 * @param skill the skill to set
	 */
	public void setSkill(int skill) {
		this.skill = skill;
	}

	/**
	 * @return the czyBohater
	 */
	public boolean isCzyBohater() {
		return czyBohater;
	}

	/**
	 * @param czyBohater the czyBohater to set
	 */
	public void setCzyBohater(boolean czyBohater) {
		this.czyBohater = czyBohater;
	}

	/**
	 * @return the obrazekNadczlowieka
	 */
	public WritableImage getObrazekNadczlowieka() {
		return obrazekNadczlowieka;
	}

	/**
	 * @param obrazekNadczlowieka the obrazekNadczlowieka to set
	 */
	public void setObrazekNadczlowieka(WritableImage obrazekNadczlowieka) {
		this.obrazekNadczlowieka = obrazekNadczlowieka;
	}

	/**
	 * @return the pojedynek
	 */
	public boolean isPojedynek() {
		return pojedynek;
	}

	/**
	 * @param pojedynek the pojedynek to set
	 */
	public void setPojedynek(boolean pojedynek) {
		this.pojedynek = pojedynek;
	}
}

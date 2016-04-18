/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Fizyczne;

import java.util.concurrent.Semaphore;
import superbohaterix.Enumeratory.kierunki;
import superbohaterix.Enumeratory.trybSwiatel;
import static superbohaterix.Enumeratory.trybSwiatel.*;
import superbohaterix.OknoGlowne;
import superbohaterix.Techniczne.Magazyn;

/**
 * Skrzyzowanie na ulicy<br>
 * Posiada informacje o czasie pozostalym do zmiany swiatel, aktualnym trybie swiatel i stanie semafora
 * @author Piter
 */
public class Skrzyzowanie extends PunktNaMapie{
    private int czasSwiatel;
    private trybSwiatel tryb;
    private Semaphore semafor = new Semaphore(1,true);
   
    /**
     * Zmienia sygnalizacje na skrzyzowaniu
     */
    public void zmienSygnalizacje(){
        if(czasSwiatel==0){
            switch(getTryb()){
                case CZERWONE:
                    setTryb(ZOLTE);
                    czasSwiatel=80;
                    
                    setGrafika(Magazyn.getObrSwZol());
                    break;
                case ZOLTE:
                    setTryb(ZIELONE);
                    czasSwiatel=300;
					
					//W tym miejscu ustawiane sa pola dookola skrzyzowania
					//-1 oznacza, ze cywil moze jechac, -2 oznacza, ze nie moze
                    PoleNaUlicy.setSkrzyzowanie(-1, getX()-1, getY()+1);
                    PoleNaUlicy.setSkrzyzowanie(-1, getX()+2, getY());
                    
                    PoleNaUlicy.setSkrzyzowanie(-2, getX()+1, getY()+2);
                    PoleNaUlicy.setSkrzyzowanie(-2, getX(), getY()-1);
                    
					//zmiana grafiki zwiazana ze zmiana stanu
                    setGrafika(Magazyn.getObrSwZie());
                    break;
                case ZIELONE:
                    setTryb(ZOLTE2);
                    czasSwiatel=80;
                    
                    setGrafika(Magazyn.getObrSwZol2());
                    break;
                case ZOLTE2:
                    setTryb(CZERWONE);
                    czasSwiatel=300;
					
					//W tym miejscu ustawiane sa pola dookola skrzyzowania
					//-1 oznacza, ze cywil moze jechac, -2 oznacza, ze nie moze
                    PoleNaUlicy.setSkrzyzowanie(-2, getX()-1, getY()+1);
                    PoleNaUlicy.setSkrzyzowanie(-2, getX()+2, getY());
                    
                    PoleNaUlicy.setSkrzyzowanie(-1, getX()+1, getY()+2);
                    PoleNaUlicy.setSkrzyzowanie(-1, getX(), getY()-1);
                    
					//zmiana grafiki zwiazana ze zmiana stanu
                    setGrafika(Magazyn.getObrSwCze());
                    break;
            }
			//Request odswiezenia panelu informacyjnego
			OknoGlowne.chcialbymOdswiezyc(this);
        }
        else{
            czasSwiatel--;
        }
        
        
    }
    
	/**
	 * Konstruktor
	 * @param x
	 * @param y
	 * @param id
	 */
	public Skrzyzowanie(int x, int y,int id) {
        setX(x);
        setY(y);
        setId(id);
        
		//Ustawiane sa kierunki drogi
        PoleNaUlicy.setKierunek(kierunki.SW, x, y);
        PoleNaUlicy.setKierunek(kierunki.NW, x+1, y);
        PoleNaUlicy.setKierunek(kierunki.NE, x+1, y+1);
        PoleNaUlicy.setKierunek(kierunki.SE, x, y+1);
        
		//Cale skrzyzowanie ustawia swoje id na danych polach na ulicy
        PoleNaUlicy.setSkrzyzowanie(Magazyn.getSkrzyzowania().size(),x, y);
        PoleNaUlicy.setSkrzyzowanie(Magazyn.getSkrzyzowania().size(),x+1, y);
        PoleNaUlicy.setSkrzyzowanie(Magazyn.getSkrzyzowania().size(),x+1, y+1);
        PoleNaUlicy.setSkrzyzowanie(Magazyn.getSkrzyzowania().size(),x, y+1);
        
		//Losowanie stanu swiatel
        switch((int)(Math.random()*2)){
            case 0:
                tryb = ZIELONE;
                czasSwiatel=(int)(Math.random()*80);
                
                PoleNaUlicy.setSkrzyzowanie(-1, getX()-1, getY()+1);
                PoleNaUlicy.setSkrzyzowanie(-1, getX()+2, getY());
                    
                PoleNaUlicy.setSkrzyzowanie(-2, getX()+1, getY()+2);
                PoleNaUlicy.setSkrzyzowanie(-2, getX(), getY()-1);
                
                
                setGrafika(Magazyn.getObrSwZie());
                break;
            case 1:
                tryb = CZERWONE;
                czasSwiatel=(int)(Math.random()*80);
                
                PoleNaUlicy.setSkrzyzowanie(-2, getX()-1, getY()+1);
                PoleNaUlicy.setSkrzyzowanie(-2, getX()+2, getY());
                    
                PoleNaUlicy.setSkrzyzowanie(-1, getX()+1, getY()+2);
                PoleNaUlicy.setSkrzyzowanie(-1, getX(), getY()-1);
                
                setGrafika(Magazyn.getObrSwCze());
                break;
        }
		
		
        
    }
    
    /**
     * @return czas pozostaly do zmiany swiatel
     */
    public int getCzasSwiatel() {
        return czasSwiatel;
    }

    /**
     * @param czasSwiatel ustawia czas pozostaly do zmiany swiatel
     */
    public void setCzasSwiatel(int czasSwiatel) {
        this.czasSwiatel = czasSwiatel;
    }

    /**
     * @return semafor
     */
    public Semaphore getSemafor() {
        return semafor;
    }

	/**
	 * @return tryb
	 */
	public trybSwiatel getTryb() {
		return tryb;
	}

	/**
	 * @param tryb ustawia tryb
	 */
	public void setTryb(trybSwiatel tryb) {
		this.tryb = tryb;
	}

}

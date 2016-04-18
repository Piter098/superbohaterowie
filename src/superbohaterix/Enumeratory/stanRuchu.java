/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superbohaterix.Enumeratory;

/**
 * Opisuje aktualny stan ruchu ruchomego obiektu (Czlowieka)
 * @author Piter
 */
public enum stanRuchu {
    /**
     * IDZIE po drodze
     */
    IDZIE,
    /**
     * STOI z woli uzytkownika
     */
    STOI,
    /**
     * STACJONUJE w miescie
     */
    STACJONUJE,
    /**
     * POSTOJ z wlasnej wolnej woli
     */
    POSTOJ,
    /**
     * MARTWY - nie zyje
     */
    MARTWY,
    /**
     * BITWA na smierc i zycie pomiedzy dwoma nadludzmi
     */
    BITWA;
}

package avernusvenine.sne;

import avernusvenine.sne.spells.*;
import avernusvenine.sne.spells.area.*;
import avernusvenine.sne.spells.projectile.*;
import avernusvenine.sne.spells.targeted.*;


import java.util.HashMap;
import java.util.Map;

public class SpellDictionary {

    private static final Map<String, Spell> spellDictionary = new HashMap<>();

    public static void loadSpells(){
        new Fireball();

        // ROGUE
        new ShadowStrike();
        new SwiftStep();
        new CheapShot();
        new FanOfKnives();
        new Sprint();
        new Disperse();
        new Vanish();
        new CoupDeGrace();
        new HeadCrack();
        new GutShot();
    }


    public static Spell get(String id){
        return spellDictionary.get(id);
    }

    public static void add(Spell spell){
        spellDictionary.put(spell.getID(), spell);
    }

}

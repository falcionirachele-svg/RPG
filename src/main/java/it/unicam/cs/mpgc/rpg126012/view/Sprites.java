package it.unicam.cs.mpgc.rpg126012.view;


import javafx.scene.image.Image;

import java.io.InputStream;

/**Classe che mi serve per caricare gli sprite per ogni nemico/personaggio*/
public final class Sprites {
    private static final String FOLDER = "/immges/characters/";
    private Sprites(){}

    public static Image load(String key){
        String file = key.toLowerCase().replace(' ', '-') + ".png";
        InputStream in = Sprites.class.getResourceAsStream(FOLDER + file);
        return in == null ? null : new Image(in);
    }

}

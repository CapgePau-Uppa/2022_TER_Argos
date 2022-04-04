package com.argos.utils;

import java.awt.Color;
import java.util.Map;

/**
 *
 * @author Ivan
 */
public class PaletteMapper {

    /**
     * Map each color to a material
     */
    private Map<Color, String> colorMapping = null;
    /**
     * Different kinds of palettes should go here
     * Create a Palette Mapping setter for each kind
     * of palette
     */
    public enum paletteTypes {
        DEFAULT_PALETTE
    }
    /**
     * 
     * @param type Type of the palette
     */
    public PaletteMapper(paletteTypes type) {
        switch (type) {
            case DEFAULT_PALETTE:
                setDefaultPaletteMapping();
        } 
    }
    /**
     * Palette Mapping getter
     * @return 
     */
    public Map<Color, String> getColorMap(){
        return colorMapping;
    }
    /**
     * setter for the default Palette Mapping
     */
    private void setDefaultPaletteMapping() {
        colorMapping = Map.ofEntries(
                Map.entry(new Color(177,186,181), "Sable massif"),
                Map.entry(new Color(169,174,164), "Sable massif"),
                Map.entry(new Color(30,44,46), "Argile"),
                Map.entry(new Color(58,80,87), "Argile"),
                Map.entry(new Color(196,211,208), "Conglomerat"),
                Map.entry(new Color(170,180,170), "Conglomerat")
        );       
    }

}
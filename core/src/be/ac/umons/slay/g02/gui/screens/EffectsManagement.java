package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

import java.util.List;

import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.TileSetManagement;
import be.ac.umons.slay.g02.level.TileType;


public class EffectsManagement {


    static void eraseCells(TiledMapTileLayer layer) {
        for (int i = 0; i < layer.getWidth(); i++) {
            for (int j = 0; j < layer.getHeight(); j++) {
                HexManagement.eraseTile(new Coordinate(i, j), layer);
            }
        }
    }

    // Méthode pour surligner certaines cellules
    static void highlightCells(TiledMapTileLayer layer, List<Coordinate> list, TiledMapTile tile) {
        for (Coordinate cur : list) {
            HexManagement.drawTile(cur, tile, layer); //Surligne les tuiles de la liste
        }
    }

    // Méthode pour obscurcir la map
    static void shadowMap(TiledMapTileLayer layer, Playable level, TiledMapTileSet set) {
        for (int i = 0; i < layer.getWidth(); i++) {
            for (int j = 0; j < layer.getHeight(); j++) {
                if (level.get(i, j).getType().equals(TileType.NEUTRAL)) {
                    HexManagement.drawTile(new Coordinate(i, j), set.getTile(TileSetManagement.DARKEN_HIGHLIGHT.getId()), layer);
                }
            }
        }
    }
}

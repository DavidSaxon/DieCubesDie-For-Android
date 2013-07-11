/************************************\
| Utilities for loading level files. |
|                                    |
| @author David Saxon                |
\************************************/

package nz.co.withfire.diecubesdie.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.level.terrian.Ground;
import nz.co.withfire.diecubesdie.entities.level.terrian.Ramp;
import nz.co.withfire.diecubesdie.entities.level.terrian.Wall;
import nz.co.withfire.diecubesdie.entities.level.terrian.entry.Finish;
import nz.co.withfire.diecubesdie.entities.level.terrian.entry.Spawn;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

import android.util.Log;

public class LevelLoadUtil {

    //PUBLIC METHODS
    /**Gets the area of the level from the data
    @param data the level data
    @return the area type of the level*/
    static public ValuesUtil.LevelArea getArea(String data) {
        
        //create a scanner to read through the data
        Scanner scan = new Scanner(data);
        
        //read through the file until we find the area tag
        while (!scan.nextLine().equals("#AREA"));
        
        //read the area and get the equivlent enum for it
        String levelArea = scan.nextLine();
        
        if (levelArea.equals("PLAINS")) {
            
            return ValuesUtil.LevelArea.PLAINS;
        }
        if (levelArea.equals("MOUNTAINS")) {
            
            return ValuesUtil.LevelArea.MOUNTAINS;
        }
        if (levelArea.equals("CITY")) {
            
            return ValuesUtil.LevelArea.MOUNTAINS;
        }
        if (levelArea.equals("DESERT")) {
            
            return ValuesUtil.LevelArea.MOUNTAINS;
        }
        if (levelArea.equals("JUNGLE")) {
            
            return ValuesUtil.LevelArea.MOUNTAINS;
        }
        
        return ValuesUtil.LevelArea.STRONGHOLD;
    }
    
    /**Gets the dimensions of the level from the data
    @param data the level data
    @return the dimensions*/
    static public Vector3d getDim(String data) {
        
        //create a scanner to read through the data
        Scanner scan = new Scanner(data);
        
        //read through the file until we find the dimensions tag
        while (!scan.nextLine().equals("#DIM"));
        
        //read the dimensions
        float x = Integer.parseInt(scan.next());
        float y = Integer.parseInt(scan.next());
        float z = Integer.parseInt(scan.next());
        
        return new Vector3d(x, y, z);
    }
    
    /**Gets the map of the level as a 2d array of entity codes
    @param data the level data
    @param the level map*/
    static public List<List<String>> getMap(String data) {
        
        //create the 2d list
        List<List<String>> map = new ArrayList<List<String>>();
        
        //create a scanner to read through the data
        Scanner scan = new Scanner(data);
        
        //read through the file until we find the map tag
        while (!scan.nextLine().equals("#MAP"));
        
        String line = scan.nextLine();
        //read until we find the next tag
        while (!line.startsWith("#")) {
            
            //create a new list for each row
            List<String> rowList = new ArrayList<String>();
            //read the next line into a new scanner
            Scanner lineScan = new Scanner(scan.nextLine());
            
            //read the entity codes and add them to the row list
            while (lineScan.hasNext()) {
                
                rowList.add(lineScan.next());
            }
            
            //add the row to the map
            map.add(rowList);
            
            //read the next line
            line = scan.nextLine();
        }
        
        return map;
    }
    
    /**Creates the Entity corresponding to the given code and adds it
    to the ground or to the entity map
    @param code the entity code
    @param pos the x and y position of the entity
    @param entityMap the entity map
    @param ground the ground in case we need to add to it
    @param resources the resource manager
    @return the entity*/
    static public void createEntity(String code, Vector2d pos,
        Entity entityMap[][][], Ground ground,
        ResourceManager resources) {
        
        //get the type
        char type = code.charAt(0);
        //get the floor of the entity
        int z = Integer.parseInt("" + code.charAt(1));
        ///convert the position to a 3d vector with floor
        Vector3d pos3 = new Vector3d(pos.getX(), pos.getY(), z);
        //get the version
        char version = code.charAt(2);
        
        //find the entity type we need to create
        switch(type) {
        
            case 'G': {
                
                createGround(version, pos3, ground, resources);
                break;
            }
            case 'W': {
                
                createWall(version, pos3, entityMap, resources);
                break;
            }
            case 'R': {
                
                createRamp(version, pos3, entityMap, resources);
                break;
            }
            case 'S': {
                
                createSpawn(pos3, entityMap, resources);
                break;
            }
            case 'F': {
                
                createFinish(pos3, entityMap, resources);
                break;
            }
        }
    }
    
    //PRIVATE METHODS
    /**Adds the floor of the given version at the given pos
    @param version the version of floor
    @param pos the position of the floor
    @param ground the ground
    @param resources the resource manager*/
    static private void createGround(char version,
        Vector3d pos, Ground ground, ResourceManager resources) {
        
        //choose the tile of ground
        String tile = "north";
        int hor = (int) pos.getX();
        int vert = (int) pos.getY();
        
        if (hor % 2 == 0 && vert % 2 == 0) {
            
            tile = "south";
        }
        else if (hor % 2 == 0 && vert % 2 != 0) {
            
            tile = "west";
        }
        else if (hor % 2 != 0 && vert % 2 == 0) {
            
            tile = "east";
        }
        
        //find the version and add
        switch (version) {
        
            case 'a': {
                
                ground.add(
                    resources.getShape("plains_ground_grass_1_" + tile), pos);
                break;
            }
        }
    }
    
    /**Adds a wall to the entity map
    @param version the version of the wall
    @param pos the position of the wall
    @param entityMap the entity map
    @param resources the resource manager*/
    static private void createWall(char version, Vector3d pos,
        Entity entityMap[][][], ResourceManager resources) {
        
        //find the version and add
        switch (version) {
        
            case 'a': {
                
                entityMap[(int) pos.getZ()]
                    [(int) pos.getY()][(int) pos.getX()] =
                    new Wall(resources.getShape("plains_cliff_top_1"),
                        resources.getShape("plains_cliff_side_1"),
                        pos);
                break;
            }
        }
    }
    
    /**Adds a ramp to the entity map
    @param version the version of the ramp
    @param pos the position of the ramp
    @param entityMap the entity map
    @param resources the resource manager*/
    static private void createRamp(char version, Vector3d pos,
        Entity entityMap[][][], ResourceManager resources) {
        
        //find the version
        switch(version) {
        
            case 'a': {
                
                entityMap[(int) pos.getZ()]
                    [(int) pos.getY()][(int) pos.getX()] =
                    new Ramp(resources.getShape("plains_ramp_top_1"),
                        resources.getShape("plains_ramp_side_1"),
                        pos);
                break;
            }
        }
    }
    
    /**Adds a spawn point to the entity map
    @param pos the position of the spawn
    @param entityMap the entity map
    @param resources the resource manager*/
    static private void createSpawn(Vector3d pos,
        Entity entityMap[][][], ResourceManager resources) {
        
        entityMap[(int) pos.getZ()]
            [(int) pos.getY()][(int) pos.getX()] = 
            new Spawn(resources.getShape("spawn"),
                resources.getShape("spawn_inside"), pos);
    }
    
    /**Adds a finish point to the entity map
    @param pos the position of the finish
    @param entityMap the entity map
    @param resources the resources manager*/
    static private void createFinish(Vector3d pos,
        Entity entityMap[][][], ResourceManager resources) {
        
        entityMap[(int) pos.getZ()]
            [(int) pos.getY()][(int) pos.getX()] = 
            new Finish(resources.getShape("finish"),
                resources.getShape("finish_inside"), pos);
    }
}

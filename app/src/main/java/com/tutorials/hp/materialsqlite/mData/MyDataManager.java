package com.tutorials.hp.materialsqlite.mData;
import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.core.RushSearch;

/**
 * Created by Oclemy on 2017 for ProgrammingWizards TV Channel and http://www.camposha.inf
 - Our MyDataManager class.
 - We perfom all our SQLite CRUD operations here.
 - We INSERT,SELECT,UPDATE,DELETE and FIND.
 - We are using RushORM as our Object Relational Mapper.
 *
 */

public class MyDataManager {

    /*
  - Retrieve SQLite data using the RushSearch().find() method.
  - This returns a list of Galaxies.
   */
    public static ArrayList<Galaxy> getGalaxies() {
        List<Galaxy> galaxies = new RushSearch().find(Galaxy.class);
        return (ArrayList<Galaxy>) galaxies;
    }
    /*
    - Save Galaxy to sqlite database by calling save() method.
     */
    public static boolean add(Galaxy g) {
        try {
            g.save();
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /*
    - UPDATE SQLITE DATA GIVEN OLD GALAXY ID AND NEW GALAXY
     */
    public static boolean update(String id,Galaxy newGalaxy)
    {
        try
        {
            Galaxy g = new RushSearch().whereId(id).findSingle(Galaxy.class);
            g.setName(newGalaxy.getName());
            g.setDescription(newGalaxy.getDescription());
            g.save();

            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }
    /*
    - FIND SINGLE GALAXY FROM SQLITE GIVEN ID
     */
    public static Galaxy find(String id)
    {
        try
        {
            Galaxy g = new RushSearch().whereId(id).findSingle(Galaxy.class);
            return g;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /*
    - DELETE FROM SQLITE GIVEN THE GALAXY
     */
    public static boolean delete(Galaxy galaxy)
    {
        try
        {
            Galaxy g = new RushSearch().whereId(galaxy.getId()).findSingle(Galaxy.class);
            g.delete();
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}

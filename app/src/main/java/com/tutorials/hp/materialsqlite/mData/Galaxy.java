package com.tutorials.hp.materialsqlite.mData;

import java.util.Date;

import co.uk.rushorm.core.RushObject;
/**
  Created by Oclemy on 2017 for ProgrammingWizards TV Channel and http://www.camposha.info.
 - Our Galaxy class.
 - Is our data object.
 - Derives from RushObject. This makes it special in that our ORM will translate it to a sqlite database table.
 - It will inherit methods like save,delete,update,select etc and fields like id.
 */

public class Galaxy extends RushObject {

    private String name,description;
    private String date;

    public Galaxy(){}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }


}

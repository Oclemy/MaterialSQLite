package com.tutorials.hp.materialsqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.tutorials.hp.materialsqlite.mData.Galaxy;
import com.tutorials.hp.materialsqlite.mData.MyDataManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import me.riddhimanadib.formmaster.helper.FormBuildHelper;
import me.riddhimanadib.formmaster.model.FormElement;
import me.riddhimanadib.formmaster.model.FormHeader;
import me.riddhimanadib.formmaster.model.FormObject;
/*
- Our CrudActivity class.
- We perform our UI crud operation here. That is we get data from our forms and talk to MyDataManager to interact with db.
- This activity is special in that we use it for both adding new data and editing existing data. Also we use it for deleting.
- This is easy in that we only need to what the user clicked in the master activity and render our forms appropriately.
- Hence a boolean is passed to us from mainactivity showing whether it is for adding or editing.
 */
public class CrudActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FormBuildHelper mFormBuilder;
    Button saveBtn,deleteBtn;
    Galaxy galaxy;
    String id=null;
    Boolean isForUpdate = true;

    /*
    - When activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        this.receiveData();
        this.initializeViews();
    }

    private void initializeViews() {
        // initialize variables
        mRecyclerView = (RecyclerView) findViewById(R.id.newRecyclerView);
        saveBtn = (Button) findViewById(R.id.newBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        deleteBtn.setEnabled(isForUpdate);

        mFormBuilder = new FormBuildHelper(this, mRecyclerView);

        // declare form elements
        FormHeader header = FormHeader.createInstance().setTitle("Galaxy Info");
        final FormElement nameEditText = FormElement.createInstance().setType(FormElement.TYPE_EDITTEXT_TEXT_SINGLELINE).setTitle("Galaxy").setHint("Galaxy Name....");
        final FormElement descEditText = FormElement.createInstance().setType(FormElement.TYPE_EDITTEXT_TEXT_MULTILINE).setTitle("Description").setHint("Galaxy Description....");
        final FormElement publishDate = FormElement.createInstance().setType(FormElement.TYPE_PICKER_DATE).setTitle("Date");

        if (isForUpdate) {
            nameEditText.setValue(galaxy.getName());
            descEditText.setValue(galaxy.getDescription());
            publishDate.setValue(galaxy.getDate());
        }
        // add them in a list
        List<FormObject> formItems = new ArrayList<>();
        formItems.add(header);
        formItems.add(nameEditText);
        formItems.add(descEditText);
		formItems.add(publishDate);

        // build and display the form
        mFormBuilder.addFormElements(formItems);
        mFormBuilder.refreshView();

        //INSERT DATA OR UPDATE EXISTING DATA
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isForUpdate) {
                    //INSERT NEW DATA
                    Galaxy galaxy = new Galaxy();
                    galaxy.setName(nameEditText.getValue());
                    galaxy.setDescription(descEditText.getValue());
                    galaxy.setDate(publishDate.getValue());
                    if(MyDataManager.add(galaxy))
                    {
                        nameEditText.setValue("");
                        descEditText.setValue("");
						publishDate.setValue("");
                        mFormBuilder.refreshView();
                        Toast.makeText(getApplicationContext(), "Successfully Saved Data", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getApplicationContext(), "Unable To Insert data", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    if (galaxy != null) {
                        //UPDATE EXISTING DATA
                        Galaxy newGalaxy=new Galaxy();
                        newGalaxy.setName(nameEditText.getValue());
                        newGalaxy.setDescription(descEditText.getValue());
                        newGalaxy.setDate(publishDate.getValue());
                        if(MyDataManager.update(galaxy.getId(),newGalaxy))
                        {
                            Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getApplicationContext(), "Unable To Update", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Galaxy is null.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //DELETE DATA FROMSQLITE DATABASE
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isForUpdate)
                {
                    if (galaxy != null)
                    {
                        if(MyDataManager.delete(galaxy))
                        {
                            nameEditText.setValue("");
                            descEditText.setValue("");
							publishDate.setValue("");
                            mFormBuilder.refreshView();

                            deleteBtn.setEnabled(isForUpdate=false);
                            saveBtn.setEnabled(false);
                            Toast.makeText(getApplicationContext(), "Deleted Successfully.", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Unable To Delete", Toast.LENGTH_SHORT).show();

                        }

                    }
                }
            }
        });
    }

    /*
    - Receive data from MainActivity
     */
    private void receiveData() {
        try {
            Intent i = this.getIntent();
            id=i.getStringExtra("KEY_GALAXY");
            if(id == null)
            {
                isForUpdate=false;
            }else
            {
                galaxy=MyDataManager.find(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.tutorials.hp.materialsqlite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.TextViewAction;
import com.dexafree.materialList.view.MaterialListView;
import com.squareup.picasso.RequestCreator;
import com.tutorials.hp.materialsqlite.mData.Galaxy;
import com.tutorials.hp.materialsqlite.mData.MyDataManager;
import java.util.Random;
/*
- Our Mainactivity class.
- Derives from appcompatactivity.
- Will be our master view. It will contain a material listview to display our list of cards.
- Each card will comprise image,texts and two action buttons.
- New will be for opening crud activity for adding new data while edit will be for opening it for editing existing data.
- If it's for editing, we pass the id of the selected object to the crud activity.
 */
public class MainActivity extends AppCompatActivity {
    MaterialListView materialListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeViews();
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CrudActivity.class);
                Context c = MainActivity.this;
                c.startActivity(i);
            }
        });
    }

    /*
    - Initialize Material ListView
     */
    private void initializeViews() {
        materialListView = (MaterialListView) findViewById(R.id.material_listview);
    }

    /*
    - Bind data from database to Material ListView
     */
    private void bindData() {
        materialListView.getAdapter().clearAll();
        if(MyDataManager.getGalaxies().size()>0)
        {
            for (Galaxy g : MyDataManager.getGalaxies()) {
                this.createCard(g);
            }
        }

    }

    /*
    - Get random image to show in list from drawables
     */
    private int getRandomImage()
    {
        int[] images={R.drawable.andromeda,R.drawable.cartwheel,R.drawable.canismajoroverdensity,R.drawable.centaurusa};
        int image=new Random().nextInt(images.length);
        return images[image];
    }

    /*
    - Create a Card and add to Material List.
    - Pass the galaxy to display
     */
    private void createCard(final Galaxy g) {
        Card card = new Card.Builder(this)
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_basic_image_buttons_card_layout)
                //.setLayout(R.layout.material_image_with_buttons_card)
                //.setLayout(R.layout.material_basic_buttons_card)
                //.setLayout(R.layout.material_welcome_card_layout)
                //.setLayout(R.layout.material_small_image_card)
                //.setLayout(R.layout.material_big_image_card_layout)
                .setTitle(g.getName())
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setSubtitle(g.getDate())
                .setDescription(g.getDescription())
                .setDescriptionGravity(Gravity.CENTER_HORIZONTAL)
                .setDrawable(getRandomImage())
                .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                    @Override
                    public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                        //requestCreator.fit();
                        requestCreator.resize(121,121);
                    }
                })
                .addAction(R.id.left_text_button, new TextViewAction(this)
                        .setText("New")
                        .setTextResourceColor(R.color.colorPrimary)
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                Intent i = new Intent(MainActivity.this, CrudActivity.class);
                                Context c = MainActivity.this;
                                c.startActivity(i);
                            }
                        }))
                .addAction(R.id.right_text_button, new TextViewAction(this)
                        .setText("Edit")
                        .setTextResourceColor(R.color.orange_button)
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                Intent i = new Intent(MainActivity.this, CrudActivity.class);
                                i.putExtra("KEY_GALAXY", g.getId());
                                Context c = MainActivity.this;
                                c.startActivity(i);
                            }
                        }))
                .endConfig()
                .build();

        materialListView.getAdapter().add(card);
    }

    /*
    - When activity is resumed
     */
    @Override
    protected void onResume() {
        super.onResume();
        this.bindData();
    }
}

package com.hfad.starbuzz;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

public class DrinkActivity extends Activity
{
    public static final String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        //Get the drink from the intent
        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);

        //Create a cursor
        try
        {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            Cursor cursor = db.query("DRINK", new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?", new String[] {Integer.toString(drinkNo)}, null, null, null);

            //Move to the first record in the Cursor
            if (cursor.moveToFirst())
            {
                //Get the drink details from the cursor
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);

                //Populate the drink name
                TextView name = findViewById(R.id.name);
                name.setText(nameText);

                //Populate the drink description
                TextView description = findViewById(R.id.description);
                description.setText(descriptionText);

                //Populate the drink image
                ImageView photo = findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                //Populate the favorite checkbox
                CheckBox checkBox = findViewById(R.id.favorite);
                checkBox.setChecked(isFavorite);
            }
            cursor.close();
            db.close();
        }
        catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //Update the database when the checkbox is clicked
    public void onFavoriteClicked(View view)
    {
        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);
        new UpdateDrinkTask().execute(drinkNo);
    }

    //Inner class to update the drink
    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean>
    {
        ContentValues drinkValues;

        @Override
        protected void onPreExecute()
        {
            CheckBox favorite = findViewById(R.id.favorite);
            drinkValues = new ContentValues();
            drinkValues.put("FAVORITE", favorite.isChecked());
        }

        @Override
        protected Boolean doInBackground(Integer... integers)
        {
            int drinkNo = integers[0];
            SQLiteOpenHelper starbuzzDatabaseHelper =
                    new StarbuzzDatabaseHelper(DrinkActivity.this);
            try
            {
                SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
                db.update("DRINK", drinkValues,
                        "_id = ?", new String[] {Integer.toString(drinkNo)});
                db.close();
                return true;
            }
            catch (SQLiteException e)
            {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success)
        {
            if (!success)
            {
                Toast toast = Toast.makeText(DrinkActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}

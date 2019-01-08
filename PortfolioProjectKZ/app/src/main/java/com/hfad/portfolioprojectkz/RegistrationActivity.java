package com.hfad.portfolioprojectkz;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity
{
    private static EditText pass;
    private static EditText eMail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        pass = findViewById(R.id.password);
        eMail = findViewById(R.id.email);
    }

    public void onLoginClick(View view)
    {
        new ValidateUserInputTask().execute();
    }

    public void onSignUpClick(View view)
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private class ValidateUserInputTask extends AsyncTask<Void, Void, Boolean>
    {
        private String myPass;
        private String myEmail;

        @Override
        protected void onPreExecute()
        {
            myPass = pass.getText().toString();
            myEmail = eMail.getText().toString();
        }

        @Override
        protected Boolean doInBackground(Void... voids)
        {
            try
            {
                SQLiteOpenHelper db_manager = new PortfolioProjectDatabaseHelper(RegistrationActivity.this);
                SQLiteDatabase db = db_manager.getWritableDatabase();

                Cursor cursor = db.query("ACCOUNT", new String[]{"PASS"},
                        "EMAIL = ?", new String[]{myEmail}, null, null, null);

                if (cursor.moveToFirst())
                {
                    if (cursor.getString(0).equals(myPass))
                    {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("LOGGED_IN", 1);
                        db.update("ACCOUNT", contentValues, null, null);
                        db.close();

                        return true;
                    }
                    //If password from the db and inputted by user are different
                    else
                    {
                        db.close();
                        return false;
                    }
                }
                //If there is no such email address in the db
                else
                {
                    db.close();
                    return false;
                }
            }
            catch (SQLException ex)
            {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean status)
        {
            if (status)
            {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else
            {
                TextView loginFailed = findViewById(R.id.loginFailed);
                loginFailed.setText("Incorrect login or password!");
            }
        }
    }
}

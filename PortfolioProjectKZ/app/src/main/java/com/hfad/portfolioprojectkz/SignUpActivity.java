package com.hfad.portfolioprojectkz;

import android.content.ContentValues;
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

public class SignUpActivity extends AppCompatActivity
{
    private static EditText eMail;
    private static EditText pass;
    private static EditText repeatPass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        eMail = findViewById(R.id.email);
        pass = findViewById(R.id.yourPass);
        repeatPass = findViewById(R.id.yourPassRepeat);
    }

    public void onSaveChangeClick(View view)
    {
        String myEmail = eMail.getText().toString();
        String myPass = pass.getText().toString();
        String myRepeatPass = repeatPass.getText().toString();

        if (myEmail.isEmpty() || myPass.isEmpty() || myRepeatPass.isEmpty())
        {
            showErrorMessage();
        }
            else if (!myPass.equals(myRepeatPass))
            {
                showErrorMessage();
            }
        else
        {
            new SaveUserAccountTask().execute(myEmail, myPass);
        }
    }

    private void showStatus(boolean status)
    {
        TextView textStatus = findViewById(R.id.showStatus);
        if (status)
        {
            textStatus.setText(getResources().getText(R.string.updated_suc));
        }
        else
        {
            textStatus.setText(getResources().getText(R.string.updated_bad));
        }
    }

    private void showErrorMessage()
    {
        TextView errorMessage = findViewById(R.id.checkInput);
        errorMessage.setText(getResources().getText(R.string.check_input));
    }

    private class SaveUserAccountTask extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(String... strings)
        {
            ContentValues accountInfo = new ContentValues();
            accountInfo.put("NAME", "Your name");
            accountInfo.put("SURNAME", "Your surname");
            accountInfo.put("TEL", "tel");
            accountInfo.put("PASS", strings[1]);
            accountInfo.put("EMAIL", strings[0]);
            accountInfo.put("BIRTH", "birth");
            accountInfo.put("GENDER", 0);
            accountInfo.put("CITY", "CITY");
            accountInfo.put("CARD_NUMBER", "0-0000-0000");
            accountInfo.put("PHOTO_ID", 0);
            accountInfo.put("LOGGED_IN", 0);

            try
            {
                boolean OK = false;
                SQLiteOpenHelper db_manager = new PortfolioProjectDatabaseHelper(SignUpActivity.this);
                SQLiteDatabase db = db_manager.getWritableDatabase();
                if (db.insert("ACCOUNT", null, accountInfo) > 0)
                {
                    OK = true;
                }
                db.close();
                db_manager.close();

                return OK;
            }
            catch (SQLiteException ex)
            {
                return false;
            }
        }

        protected void onPostExecute(Boolean status)
        {
            showStatus(status);
        }
    }
}

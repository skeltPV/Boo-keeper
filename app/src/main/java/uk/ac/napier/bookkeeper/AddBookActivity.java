package uk.ac.napier.bookkeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBookActivity extends Activity
{
    private static final String TAG = "AddBookActivity";

    DatabaseHelper mDatabaseHelper;
    private Button save_button, list_button;
    private EditText userInput;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);
        userInput = (EditText) findViewById(R.id.userInput);
        save_button = (Button) findViewById(R.id.save_button);
        list_button = (Button) findViewById(R.id.list_button);

        mDatabaseHelper = new DatabaseHelper(this);

        save_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String newEntry = userInput.getText().toString();
                if(userInput.length()!=0) {
                    AddData(newEntry);
                    userInput.setText("");
                }else{
                    toastMessage(" You must put something in the text field!");
                }
            }
        });

        list_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBookActivity.this, ListBookActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddData(String newEntry)
    {
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if(insertData)
        {
            toastMessage("Saved");
        }else{
            toastMessage("Somethings went wrong");
        }
    }

    /*customisable Toast
    * @param message
    */
    private void toastMessage(String message)
    {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

package uk.ac.napier.bookkeeper;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditBookActivity extends AppCompatActivity
{
    private static final String TAG = "EditBookActivity";

    private Button delButton,saveButton;
    private EditText editableBook;

    DatabaseHelper mDatabaseHelper;

    private String selectedBook;
    private int selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_book);
        saveButton = (Button) findViewById(R.id.saveButton);
        delButton = (Button) findViewById(R.id.delButton);
        editableBook = (EditText) findViewById(R.id.editableBook);
        mDatabaseHelper = new DatabaseHelper(this);

        //get Intent extra from the ListBookActivity
        Intent receivedIntent = getIntent();

        //get itemID passed as extra
        selectedID = receivedIntent.getIntExtra("id", -1); // -1 default value

        //get the name passed as extra
        selectedBook = receivedIntent.getStringExtra("name");

        //set the text to show the current selected name
        editableBook.setText(selectedBook);

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String item = editableBook.getText().toString();
                if(!item.equals("")){
                    mDatabaseHelper.updateName(item,selectedID,selectedBook);
                }else{
                    toastMessage("You must enter a book name");
                }
            }
        });

        delButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.delName(selectedID,selectedBook);
                editableBook.setText("");
                toastMessage("Removed from database");
            }
        });
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

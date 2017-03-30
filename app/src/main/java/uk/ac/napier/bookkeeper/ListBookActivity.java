package uk.ac.napier.bookkeeper;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListBookActivity extends AppCompatActivity
{
    private static final String TAG = "ListBookActivity";

    DatabaseHelper mDatabaseHelper;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

       populateListView();

        Button backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(ListBookActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void populateListView()
    {
        Log.d(TAG,"populateListView: Displaying data in the ListView.");

        //get data and append to the list
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext())
        {
            //get the value from the database column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));
        }

        //creat a list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
        mListView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
               String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick:You Clicked on " + name);

                Cursor data = mDatabaseHelper.getItemID(name); //get ID associated with that name
                int itemID = -1;
                while(data.moveToNext())
                {
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    //on selecting the item take to the edit screen + put extras
                    Intent editScreenIntent = new Intent(ListBookActivity.this,EditBookActivity.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name", name);
                    startActivity(editScreenIntent);
                }else{
                    toastMessage("No ID assosiated with that name");
                }
            }
        });

    }
    private void toastMessage(String message)
    {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

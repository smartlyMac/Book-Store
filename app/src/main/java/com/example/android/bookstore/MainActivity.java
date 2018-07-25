package com.example.android.bookstore;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.android.bookstore.data.BookContract.BookEntry;
import com.example.android.bookstore.data.BookDbHelper;

public class MainActivity extends AppCompatActivity {

    // Create a new BookDbHelper
    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new BookDbHelper(this);

        // Set onClickListener for add dummy book button
        Button dummyBookButton = findViewById(R.id.dummyBookButton);
        dummyBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                displayDbInfo();
            }
        });
        displayDbInfo();
    }

    private void displayDbInfo() {

        // Get a cursor that returns all rows in the books table
        Cursor cursor = getContentResolver().query(BookEntry.CONTENT_URI, null, null, null, null);

        TextView mainTextView = findViewById(R.id.text_view_main);

        try {
            mainTextView.setText(getString(R.string.mainTextView) + cursor.getCount());
            mainTextView.append("\n\n"
                    + BookEntry._ID + " - "
                    + BookEntry.PRODUCT_TITLE + " - "
                    + BookEntry.PRODUCT_AUTHOR + " - "
                    + BookEntry.PRICE + " - "
                    + BookEntry.QTY + " - "
                    + BookEntry.SUPPLIER_NAME + " - "
                    + BookEntry.SUPPLIER_TEL + "\n");

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(cursor.getColumnIndex(BookEntry._ID));
                String currentTitle = cursor.getString(cursor.getColumnIndex(BookEntry.PRODUCT_TITLE));
                String currentAuthor = cursor.getString(cursor.getColumnIndex(BookEntry.PRODUCT_AUTHOR));
                int currentPrice = cursor.getInt(cursor.getColumnIndex(BookEntry.PRICE)) / 100;
                int currentQty = cursor.getInt(cursor.getColumnIndex(BookEntry.QTY));
                String currentSupplier = cursor.getString(cursor.getColumnIndex(BookEntry.SUPPLIER_NAME));
                String currentSupplierTel = cursor.getString(cursor.getColumnIndex(BookEntry.SUPPLIER_TEL));

                mainTextView.append("\n"
                        + currentID + " - "
                        + currentTitle + " - "
                        + currentAuthor + " - "
                        + "£" + currentPrice + " - "
                        + currentQty + " - "
                        + currentSupplier + " - "
                        + currentSupplierTel
                );
            }

        } finally {
            return;
        }
    }

    private void insertData() {

        // Create the content values for the new book
        ContentValues values = new ContentValues();
        values.put(BookEntry.PRODUCT_TITLE, "A Universe From Nothing");
        values.put(BookEntry.PRODUCT_AUTHOR, "Lawrence Krauss");
        values.put(BookEntry.PRICE, 1099);
        values.put(BookEntry.QTY, 1);
        values.put(BookEntry.SUPPLIER_NAME, "Books R Us");
        values.put(BookEntry.SUPPLIER_TEL, "01234567890");

        // Add the new book to the database
        getContentResolver().insert(BookEntry.CONTENT_URI, values);

    }

}

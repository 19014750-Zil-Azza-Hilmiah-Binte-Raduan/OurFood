package sg.edu.rp.c346.id19014750.ourfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "food.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FOOD = "food";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE Food
        // (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,
        // singers TEXT, stars INTEGER, year INTEGER );
        String createFoodTableSql = "CREATE TABLE " + TABLE_FOOD + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_LOCATION + " TEXT, "
                + COLUMN_PRICE + " INTEGER, "
                + COLUMN_STARS + " INTEGER )";
        db.execSQL(createFoodTableSql);
        Log.i("info", createFoodTableSql + "\ncreated tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        onCreate(db);
    }

    public long insertFood(String name, String location, int price, int stars) {
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_STARS, stars);
        // Insert the row into the TABLE_FOOD
        long result = db.insert(TABLE_FOOD, null, values);
        // Close the database connection
        db.close();
        Log.d("SQL Insert","" + result);
        return result;
    }

    public ArrayList<Food> getAllFood() {
        ArrayList<Food> foodList = new ArrayList<Food>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + "," + COLUMN_LOCATION + ","
                + COLUMN_PRICE + ","
                + COLUMN_STARS + " FROM " + TABLE_FOOD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String location = cursor.getString(2);
                float price = cursor.getFloat(3);
                int stars = cursor.getInt(4);

                Food newFood = new Food(id, name, location, price, stars);
                foodList.add(newFood);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foodList;
    }

    public ArrayList<Food> getAllFoodByStars(int starsFilter) {
        ArrayList<Food> foodList = new ArrayList<Food>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_LOCATION, COLUMN_PRICE, COLUMN_STARS};
        String condition = COLUMN_STARS + ">= ?";
        String[] args = {String.valueOf(starsFilter)};

        //String selectQuery = "SELECT " + COLUMN_ID + ","
        //            + COLUMN_TITLE + ","
        //            + COLUMN_SINGERS + ","
        //            + COLUMN_YEAR + ","
        //            + COLUMN_STARS
        //            + " FROM " + TABLE_SONG;

        Cursor cursor;
        cursor = db.query(TABLE_FOOD, columns, condition, args, null, null, null, null);

        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String location = cursor.getString(2);
                float price = cursor.getFloat(3);
                int stars = cursor.getInt(4);

                Food newFood = new Food(id, name, location, price, stars);
                foodList.add(newFood);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return foodList;
    }



    public int updateFood(Food data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_LOCATION, data.getLocation());
        values.put(COLUMN_PRICE, data.getPrice());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_FOOD, values, condition, args);
        db.close();
        return result;
    }


    public int deleteFood(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_FOOD, condition, args);
        db.close();
        return result;
    }

    public ArrayList<String> getPrice() {
        ArrayList<String> codes = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_PRICE};

        Cursor cursor;
        cursor = db.query(true, TABLE_FOOD, columns, null, null, null, null, null, null);
        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                codes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return codes;
    }

    public ArrayList<Food> getAllFoodByYear(int yearFilter) {
        ArrayList<Food> foodList = new ArrayList<Food>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_LOCATION, COLUMN_PRICE, COLUMN_STARS};
        String condition = COLUMN_PRICE + "= ?";
        String[] args = {String.valueOf(yearFilter)};

        Cursor cursor;
        cursor = db.query(TABLE_FOOD, columns, condition, args, null, null, null, null);

        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String location = cursor.getString(2);
                float price = cursor.getFloat(3);
                int stars = cursor.getInt(4);

                Food newFood = new Food(id, name, location, price, stars);
                foodList.add(newFood);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return foodList;
    }
}
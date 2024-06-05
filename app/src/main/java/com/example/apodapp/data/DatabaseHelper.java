package com.example.apodapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Třída DatabaseHelper je pomocná třída pro správu lokální SQLite databáze,
 * která slouží ke cachování dat z NASA APOD.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "apodCache.db";  // Název databáze
    private static final int DATABASE_VERSION = 1;  // Verze databáze

    /**
     * Konstruktor třídy DatabaseHelper.
     * @param context Kontext aplikace, použitý pro inicializaci SQLiteOpenHelper.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Vytváří tabulku apod_cache v databázi při jejím prvním vytvoření.
     * @param db SQLite databáze, ve které se tabulka vytváří.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS apod_cache (" +
                        "date TEXT PRIMARY KEY," +        // Datum obrázku jako primární klíč
                        "title TEXT," +                   // Titulek obrázku
                        "explanation TEXT," +             // Popis obrázku
                        "url TEXT" +                      // URL adresa obrázku
                        ")"
        );
    }

    /**
     * Upgraduje databázi na novou verzi. Tato metoda je volána, pokud je
     * potřeba změnit strukturu databáze.
     * @param db SQLite databáze, která má být upgradována.
     * @param oldVersion Starší verze databáze.
     * @param newVersion Novější verze databáze.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS apod_cache");  // Smazání staré tabulky
        onCreate(db);  // Vytvoření nové tabulky
    }
}

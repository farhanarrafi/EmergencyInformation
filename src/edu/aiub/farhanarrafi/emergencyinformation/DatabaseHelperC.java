package edu.aiub.farhanarrafi.emergencyinformation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.aiub.farhanarrafi.emergencyinformation.DatabaseTablesC.*;

public class DatabaseHelperC extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "information.db";
	public static final String TABLE_NAME = "all";
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = ",";
	
	private final String CREATE_HOSPITAL = 
			"CREATE TABLE IF NOT EXISTS" + Hospital.TABLE_NAME + " (" +
			Hospital._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
			Hospital.COLUMN_ID + INTEGER_TYPE + COMMA_SEP +
			Hospital.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
			Hospital.COLUMN_ADDRESS + TEXT_TYPE + COMMA_SEP +
			" )";
	private final String DROP_HOSPITAL = 
			"DROP TABLE IF EXISTS " + Hospital.TABLE_NAME;
	

	private final String CREATE_NEWSPAPER = 
			"CREATE TABLE IF NOT EXISTS" + Newspaper.TABLE_NAME + " (" +
			Newspaper._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
			Newspaper.COLUMN_ID + INTEGER_TYPE + COMMA_SEP +
			Newspaper.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
			Newspaper.COLUMN_ADDRESS + TEXT_TYPE + COMMA_SEP +
			Newspaper.COLUMN_PHONE + TEXT_TYPE + COMMA_SEP +
			" )";
	
	private final String DROP_NEWSPAPER = 
			"DROP TABLE IF EXISTS " + Newspaper.TABLE_NAME;
	
	private final String CREATE_PHARMACY = 
			"CREATE TABLE IF NOT EXISTS" + Pharmacy.TABLE_NAME + " (" +
				Pharmacy._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
			Pharmacy.COLUMN_ID + INTEGER_TYPE + COMMA_SEP +
			Pharmacy.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
			Pharmacy.COLUMN_ADDRESS + TEXT_TYPE + COMMA_SEP +
			" )";
	private final String DROP_PHARMACY = 
			"DROP TABLE IF EXISTS " + Pharmacy.TABLE_NAME;
	
	private final String CREATE_BLOODBANK = 
			"CREATE TABLE IF NOT EXISTS" + BloodBank.TABLE_NAME + " (" +
			BloodBank._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
			BloodBank.COLUMN_ID + INTEGER_TYPE + COMMA_SEP +
			BloodBank.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
			BloodBank.COLUMN_ADDRESS + TEXT_TYPE + COMMA_SEP +
			" )";
	private final String DROP_BLOODBANK = 
			"DROP TABLE IF EXISTS " + BloodBank.TABLE_NAME;
	
	private final String CREATE_NGO = 
			"CREATE TABLE IF NOT EXISTS" + Ngo.TABLE_NAME + " (" +
			Ngo._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
			Ngo.COLUMN_ID + INTEGER_TYPE + COMMA_SEP +
			Ngo.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
			Ngo.COLUMN_ADDRESS + TEXT_TYPE + COMMA_SEP +
			" )";
	private final String DROP_NGO = 
			"DROP TABLE IF EXISTS " + Pharmacy.TABLE_NAME;
	
	private final String CREATE_RAB = 
			"CREATE TABLE IF NOT EXISTS" + Rab.TABLE_NAME + " (" +
			Rab._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
			Rab.COLUMN_ID + INTEGER_TYPE + COMMA_SEP +
			Rab.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
			Rab.COLUMN_ADDRESS + TEXT_TYPE + COMMA_SEP +
			" )";
	private final String DROP_RAB = 
			"DROP TABLE IF EXISTS " + Rab.TABLE_NAME;
	
	
	public DatabaseHelperC(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BLOODBANK);
		db.execSQL(CREATE_HOSPITAL);
		db.execSQL(CREATE_NEWSPAPER);
		db.execSQL(CREATE_PHARMACY);
		db.execSQL(CREATE_NGO);
		db.execSQL(CREATE_RAB);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		db.execSQL(DROP_BLOODBANK);
		db.execSQL(DROP_HOSPITAL);
		db.execSQL(DROP_NEWSPAPER);
		db.execSQL(DROP_PHARMACY);
		db.execSQL(DROP_NGO);
		db.execSQL(DROP_RAB);
		
		onCreate(db);
	}
	
	/**
	 * This portion will be implemented later.
	 */
//	public String createTable(TableStructure tableStructure) {
//		String SQL_CREATE = 
//				"CREATE TABLE IF NOT EXISTS" + tableStructure.TABLE_NAME + " (" +
//				tableStructure._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
//				tableStructure.COLUMN_ID + INTEGER_TYPE + COMMA_SEP +
//				tableStructure.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
//				tableStructure.COLUMN_ADDRESS + TEXT_TYPE + COMMA_SEP +
//				" )";
//		
//		return SQL_CREATE;
//	}
//	
//	public String dropTable(TableStructure tableStructure) {
//		String SQL_DROP = "DROP TABLE IF EXISTS " + tableStructure.TABLE_NAME;
//		return SQL_DROP;
//	}

}

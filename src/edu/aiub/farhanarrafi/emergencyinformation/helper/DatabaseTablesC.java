package edu.aiub.farhanarrafi.emergencyinformation.helper;

import android.provider.BaseColumns;

public final class DatabaseTablesC {
	
	public DatabaseTablesC() {}
	
	/**
	 * This class will be introduced in later version of the program.
	 * 
	 * All other classes should be designed to extend this class,
	 * so that we can use TableStructure to refer other subclasses.
	 * @author Farhan @ farhanarrafi@gmail.com
	 *
	 */
//	public static abstract class TableStructure implements BaseColumns {
//
//		public static final String TABLE_NAME = null;
//		public static final String COLUMN_ID = null;
//		public static final String COLUMN_NAME = null;
//		public static final String COLUMN_ADDRESS = null;
//	}
	
	public static abstract class Hospital implements BaseColumns {
		public static final String TABLE_NAME = "hospital";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_ADDRESS = "address";
	}
	
	public static abstract class Dental implements BaseColumns {
		public static final String TABLE_NAME = "dental";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_ADDRESS = "address";
	}
	
	public static abstract class Newspaper implements BaseColumns {
		public static final String TABLE_NAME = "newspaper";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_PHONE = "phone";
		public static final String COLUMN_ADDRESS = "address";
	}
	
	public static abstract class Pharmacy implements BaseColumns {
		public static final String TABLE_NAME = "pharmacy";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_ADDRESS = "address";
	}
	
	public static abstract class BloodBank implements BaseColumns {
		public static final String TABLE_NAME = "bloodbank";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_ADDRESS = "address";
	}
	
	public static abstract class Ngo implements BaseColumns {
		public static final String TABLE_NAME = "ngo";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_ADDRESS = "address";
	}
	
	public static abstract class Rab implements BaseColumns {
		public static final String TABLE_NAME = "rab";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_ADDRESS = "address";
	}
	
	
}

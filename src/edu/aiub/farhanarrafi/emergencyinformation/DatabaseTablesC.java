package edu.aiub.farhanarrafi.emergencyinformation;

import android.provider.BaseColumns;

public final class DatabaseTablesC {
	
	public DatabaseTablesC() {}
	
	public static abstract class Hospital implements BaseColumns {
		public static final String TABLE_NAME = "hospital";
		public static final String COLUMN_NAME_HOSPITAL_ID = "hospitalid";
		public static final String COLUMN_NAME_HOSPITAL_NAME = "name";
		public static final String COLUMN_NAME_HOSPITAL_ADDRESS = "address";
	}
	
	public static abstract class Newspaper implements BaseColumns {
		public static final String TABLE_NAME = "newspaper";
		public static final String COLUMN_NAME_HOSPITAL_ID = "newspaperid";
		public static final String COLUMN_NAME_HOSPITAL_NAME = "name";
		public static final String COLUMN_NAME_HOSPITAL_PHONE = "phone";
		public static final String COLUMN_NAME_HOSPITAL_ADDRESS = "address";
	}
	
	public static abstract class Pharmacy implements BaseColumns {
		public static final String TABLE_NAME = "pharmacy";
		public static final String COLUMN_NAME_HOSPITAL_ID = "pharmacyid";
		public static final String COLUMN_NAME_HOSPITAL_NAME = "name";
		public static final String COLUMN_NAME_HOSPITAL_ADDRESS = "address";
	}
	
	public static abstract class BloodBank implements BaseColumns {
		public static final String TABLE_NAME = "bloodbank";
		public static final String COLUMN_NAME_HOSPITAL_ID = "bloodbankid";
		public static final String COLUMN_NAME_HOSPITAL_NAME = "name";
		public static final String COLUMN_NAME_HOSPITAL_ADDRESS = "address";
	}
	
	public static abstract class Ngo implements BaseColumns {
		public static final String TABLE_NAME = "ngo";
		public static final String COLUMN_NAME_HOSPITAL_ID = "ngoid";
		public static final String COLUMN_NAME_HOSPITAL_NAME = "name";
		public static final String COLUMN_NAME_HOSPITAL_ADDRESS = "address";
	}
	
	public static abstract class Rab implements BaseColumns {
		public static final String TABLE_NAME = "rab";
		public static final String COLUMN_NAME_HOSPITAL_ID = "rabid";
		public static final String COLUMN_NAME_HOSPITAL_NAME = "name";
		public static final String COLUMN_NAME_HOSPITAL_ADDRESS = "address";
	}
	
	
}

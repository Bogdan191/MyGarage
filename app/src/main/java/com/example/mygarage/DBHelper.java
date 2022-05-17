package com.example.mygarage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mygarage.models.CarModel;
import com.example.mygarage.models.DocumentsModel;
import com.example.mygarage.models.ServiceHistoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBHelper extends SQLiteOpenHelper {

    public static final String MY_CARS_TABLE = "MY_CARS_TABLE";
    public static final String COLUMN_CAR_MAKE = "CAR_MARK";
    public static final String COLUMN_CAR_MODEL = "CAR_MODEL";
    public static final String COLUMN_CAR_COLOR = "CAR_COLOR";
    public static final String COLUMN_CAR_MANUFACTURED_DATA = "CAR_MANUFACTURED_DATA";
    public static final String COLUMN_CAR_EMISSION = "CAR_EMISSION";
    public static final String COLUMN_CAR_ENGINE = "CAR_ENGINE";
    public static final String COLUMN_CAR_FUEL = "CAR_FUEL";
    public static final String COLUMN_CAR_HP = "CAR_HP";
    public static final String COLUMN_CAR_RIM_SIZE = "CAR_RIM_SIZE";
    public static final String COLUMN_CAR_CURRENT_VALUE = "CAR_CURRENT_VALUE";
    public static final String COLUMN_CAR_ODOMETER = "CAR_ODOMETER";
    public static final String COLUMN_CAR_MANUAL_GEARBOX = "CAR_MANUAL_GEARBOX";
    public static final String COLUMN_CAR_IMAGE = "CAR_IMAGE";
    public static final String COLUMN_CAR_ID = "ID";
    public static final String COLUMN_DOCUMENTS_ID = "DOCUMENTS_ID";
    public static final String COLUMN_SERVICE_HISTORY_ID = "SERVICE_HISTORY_ID";

    public static final String COLUMN_ITP_END_DATE = "ITP_END_DATE";
    public static final String COLUMN_INSURANCE_END_DATE = "INSURANCE_END_DATE";
    public static final String COLUMN_ROAD_TAX = "ROAD_TAX";

    public static final String COLUMN_SERVICE_MADE_DATE = "SERVICE_MADE_DATE";
    public static final String COLUMNS_DETAILS = "DETAILS";
    public static final String SERVICE_HISTORY_TABLE = "SERVICE_HISTORY_TABLE";
    public static final String DOCUMENTS_TABLE = "DOCUMENTS_TABLE";


    public DBHelper(@Nullable Context context) {

        super(context, "cars.db", null, 1);
    }

    //apelata prima data cand o baza de date este accesata. aici ar trebui sa fie codul pentru a crea o noua baza de date
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + MY_CARS_TABLE + " (" + COLUMN_CAR_ID + " TEXT PRIMARY KEY, " + COLUMN_CAR_MAKE + " TEXT, " +
                COLUMN_CAR_MODEL + " TEXT, "+ COLUMN_CAR_COLOR + " TEXT, " + COLUMN_CAR_MANUFACTURED_DATA + " TEXT, " + COLUMN_CAR_EMISSION + " TEXT, " +
                COLUMN_CAR_ENGINE + " TEXT,"+ COLUMN_CAR_FUEL + " TEXT, " + COLUMN_CAR_HP + " INTEGER, " + COLUMN_CAR_RIM_SIZE + " TEXT, " +
                COLUMN_CAR_CURRENT_VALUE + ", " + COLUMN_CAR_ODOMETER + " INTEGER, " + COLUMN_CAR_MANUAL_GEARBOX + " BOOL, " + COLUMN_CAR_IMAGE + " BLOB NOT NULL, " +
                COLUMN_DOCUMENTS_ID + " TEXT, " + COLUMN_SERVICE_HISTORY_ID + " TEXT, CONSTRAINT FK_DOCUMENTS FOREIGN KEY("+COLUMN_DOCUMENTS_ID + ")" +
                " REFERENCES " + DOCUMENTS_TABLE + "(" + COLUMN_CAR_ID + ")," +
                " CONSTRAINT FK_SERVICE FOREIGN KEY(" + COLUMN_SERVICE_HISTORY_ID + ") REFERENCES " + SERVICE_HISTORY_TABLE + "(" + COLUMN_CAR_ID + ")  )";

        db.execSQL(createTableStatement);


        createTableStatement = "CREATE TABLE " + DOCUMENTS_TABLE + " (" + COLUMN_DOCUMENTS_ID + " TEXT PRIMARY KEY,  " + COLUMN_ITP_END_DATE + " TEXT, " + COLUMN_INSURANCE_END_DATE + " TEXT, " +
                COLUMN_ROAD_TAX + " TEXT, " + COLUMN_CAR_ID + " TEXT, CONSTRAINT FK_CAR_ID FOREIGN KEY(" + COLUMN_CAR_ID + ") REFERENCES " + MY_CARS_TABLE + "(" + COLUMN_CAR_ID + "))";

        db.execSQL(createTableStatement);


        createTableStatement = "CREATE TABLE " + SERVICE_HISTORY_TABLE + "(" + COLUMN_SERVICE_HISTORY_ID + " TEXT PRIMARY KEY, " + COLUMN_SERVICE_MADE_DATE + " TEXT, " + COLUMNS_DETAILS + " TEXT, "
                                + COLUMN_CAR_ID + " TEXT, CONSTRAINT FK_CAR_ID FOREIGN KEY(" + COLUMN_CAR_ID + ")REFERENCES " + MY_CARS_TABLE + "(" + COLUMN_CAR_ID + "))";

        db.execSQL(createTableStatement);

    }

    //folosita atunci cand versiunea bazei de date se schimba
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ MY_CARS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ DOCUMENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ SERVICE_HISTORY_TABLE);
        onCreate(db);
    }

    public boolean addCarToDB(CarModel carModel){

        SQLiteDatabase db = this.getWritableDatabase();
        //hashmap
        ContentValues cv =  new ContentValues();

        cv.put(COLUMN_CAR_ID, carModel.getId());
        cv.put(COLUMN_CAR_MAKE, carModel.getMake());
        cv.put(COLUMN_CAR_MODEL, carModel.getModel());
        cv.put(COLUMN_CAR_COLOR, carModel.getColor());
        cv.put(COLUMN_CAR_MANUFACTURED_DATA, carModel.getManufactured_date());
        cv.put(COLUMN_CAR_EMISSION, carModel.getEmission_standard());
        cv.put(COLUMN_CAR_ENGINE, carModel.getEngine_capacity());
        cv.put(COLUMN_CAR_FUEL, carModel.getFuel_type());
        cv.put(COLUMN_CAR_HP, carModel.getHorse_power());
        cv.put(COLUMN_CAR_RIM_SIZE, carModel.getRim_size());
        cv.put(COLUMN_CAR_CURRENT_VALUE, carModel.getCurrent_market_value());
        cv.put(COLUMN_CAR_ODOMETER, carModel.getOdometer());
        cv.put(COLUMN_CAR_MANUAL_GEARBOX, carModel.isManual_gearbox());
        cv.put(COLUMN_CAR_IMAGE, carModel.getCarImage());
        cv.put(COLUMN_DOCUMENTS_ID, carModel.getDocuments_id());
        cv.put(COLUMN_SERVICE_HISTORY_ID, carModel.getService_history_id());

        long insert = db.insert(MY_CARS_TABLE, null, cv);
        return insert != -1;
    }

    public List<CarModel> getCars() {

        List<CarModel> returnCarsList = new ArrayList<>();

        String queryString = "SELECT * FROM " + MY_CARS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            //mergi printre resultate si pune-le in lista
            do {
                String carID = cursor.getString(0);
                String carMake = cursor.getString(1);
                String carModel = cursor.getString(2);
                String carColor = cursor.getString(3);
                String carManufacturedData = cursor.getString(4);
                String carEmission = cursor.getString(5);
                String carEngine = cursor.getString(6);
                String carFuel = cursor.getString(7);
                int carHP = cursor.getInt(8);
                String carRimSize = cursor.getString(9);
                String carCurrentValue = cursor.getString(10);
                int carOdometer = cursor.getInt(11);
                boolean carHasManualGearbox = cursor.getInt(12) == 1 ? true : false;
                byte[] carImage = cursor.getBlob(13);
                String documentsId = cursor.getString(14);
                String serviceHistoryId = cursor.getString(15);

                CarModel carModel1 = new CarModel(carID, carMake, carModel, carColor, carManufacturedData, carEmission, carEngine, carFuel, carHP, carRimSize,
                        carCurrentValue, carOdometer, carHasManualGearbox, carImage, documentsId, serviceHistoryId);

                returnCarsList.add(carModel1);

            }while(cursor.moveToNext());

        }else {

            //eroare la selectarea datelor din tabelul "my_cars_table"


        }
        db.close();
        return returnCarsList;
    }

    public CarModel getCarById(String carId) {

        List<CarModel> cars = getCars();

        for (CarModel a: cars) {
            if(a.getId().equals(carId)){
                return a;
            }
        }

        return null;
    }

    public boolean updateCarOdometer(String carId, String carOdometer) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CAR_ODOMETER, carOdometer);
        db.update(MY_CARS_TABLE, cv, COLUMN_CAR_ID + "=?", new String[] {carId});
        return true;
    }

    public boolean updateCarColor(String carId, String carColor) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CAR_COLOR, carColor);
        db.update(MY_CARS_TABLE, cv, COLUMN_CAR_ID + "=?", new String[] {carId});
        return true;
    }

    public boolean deleteCar(String carId) {

        String queryString = " DELETE FROM " + MY_CARS_TABLE + " WHERE " + COLUMN_CAR_ID + " LIKE '%" + carId + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(queryString);
        if(getCarById(carId) == null){

            queryString = " DELETE FROM " + DOCUMENTS_TABLE + " WHERE " + COLUMN_CAR_ID + " LIKE '%" + carId + "%'";
            db = this.getReadableDatabase();
            db.execSQL(queryString);

            String serviceID = carId.concat("serviceHistory");
            queryString = " DELETE FROM " + SERVICE_HISTORY_TABLE + " WHERE " + COLUMN_SERVICE_HISTORY_ID + " LIKE '%" + serviceID + "%'";
            db = this.getReadableDatabase();
            db.execSQL(queryString);

            db.close();

            return true;
        }
        db.close();
        return false;
    }

    public boolean addDocsToDB(DocumentsModel documentsModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        //hashmap
        ContentValues cv =  new ContentValues();

        cv.put(COLUMN_DOCUMENTS_ID, documentsModel.getId());
        cv.put(COLUMN_ITP_END_DATE, documentsModel.getItp_end_date());
        cv.put(COLUMN_INSURANCE_END_DATE, documentsModel.getInsurance_end_date());
        cv.put(COLUMN_ROAD_TAX, documentsModel.getRoad_tax());
        cv.put(COLUMN_CAR_ID, documentsModel.getCar_id());

        long insert = db.insert(DOCUMENTS_TABLE, null, cv);
        return insert != -1;

    }

    public DocumentsModel getDocumentsOfCar(String carId) {
        String queryString = "SELECT * FROM " + DOCUMENTS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            //mergi printre resultate si pune-le in lista
            do {
                String docRefCarID = cursor.getString(4);
                if(docRefCarID.equals(carId)) {
                    String docId = cursor.getString(0);
                    String docITP = cursor.getString(1);
                    String docInsurance = cursor.getString(2);
                    String docRoadTax = cursor.getString(3);

                    DocumentsModel document = new DocumentsModel(docId, docITP, docInsurance, docRoadTax, docRefCarID);
                    return document;
                }

            }while(cursor.moveToNext());

        }else {

            //TODO: eroare la selectarea datelor din tabelul "documents table"
        }

        db.close();
        return null;
    }

    public boolean updateCarDocs(String typeOfDocs, String carId, String newDate){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        switch (typeOfDocs){
            case "UPDATE_ITP":

                cv.put(COLUMN_ITP_END_DATE, newDate);
                db.update(DOCUMENTS_TABLE, cv, COLUMN_CAR_ID + "=?", new String[] {carId});
                return true;

            case "UPDATE_INSURANCE":

                cv.put(COLUMN_INSURANCE_END_DATE, newDate);
                db.update(DOCUMENTS_TABLE, cv, COLUMN_CAR_ID + "=?", new String[] {carId});
                return true;

            case "UPDATE_ROAD_TAX":

                cv.put(COLUMN_ROAD_TAX, newDate);
                db.update(DOCUMENTS_TABLE, cv, COLUMN_CAR_ID + "=?", new String[] {carId});
                return true;

        }
        return false;

    }

    public boolean addServiceHistoryToDB(ServiceHistoryModel serviceHistoryModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        //hashmap
        ContentValues cv =  new ContentValues();

        cv.put(COLUMN_SERVICE_HISTORY_ID, serviceHistoryModel.getId());
        cv.put(COLUMN_SERVICE_MADE_DATE, serviceHistoryModel.getService_made_date());
        cv.put(COLUMNS_DETAILS, serviceHistoryModel.getDetails());
        cv.put(COLUMN_CAR_ID, serviceHistoryModel.getCarID());

        long insert = db.insert(SERVICE_HISTORY_TABLE, null, cv);
        return insert != -1;

    }

    public List<ServiceHistoryModel> getServiceHistoryOfCar(String carId) {
        String queryString = "SELECT * FROM " + SERVICE_HISTORY_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        List<ServiceHistoryModel> serviceInfoCar = new ArrayList<>();



        if(cursor.moveToFirst()) {
            //mergi printre resultate si pune-le in lista
            do {

                String serviceCarID = cursor.getString(3);
                if(carId.equals(serviceCarID)) {

                    String serviceID = cursor.getString(0);
                    String serviceMadeDate = cursor.getString(1);
                    String serviceDetails = cursor.getString(2);

                    ServiceHistoryModel serviceHistoryModel = new ServiceHistoryModel(serviceID, serviceMadeDate, serviceDetails, carId);
                    serviceInfoCar.add(serviceHistoryModel);
                }

            }while(cursor.moveToNext());

        }else {

            //TODO: eroare la selectarea datelor din tabelul "service table"
        }

        db.close();
        return serviceInfoCar;
    }


    public boolean AddNewServiceInfoCar(String date, String details, String carId) {
        SQLiteDatabase db = this.getWritableDatabase();
        //hashmap
        ContentValues cv =  new ContentValues();

        Random  rand = new Random();
        int randServiceID = rand.nextInt(101);
        String serviceId = "CarServiceHistory" + randServiceID;
        cv.put(COLUMN_SERVICE_HISTORY_ID, serviceId);
        cv.put(COLUMN_SERVICE_MADE_DATE, date);
        cv.put(COLUMNS_DETAILS, details);
        cv.put(COLUMN_CAR_ID, carId);

        long insert = db.insert(SERVICE_HISTORY_TABLE, null, cv);
        return insert != -1;

    }
}

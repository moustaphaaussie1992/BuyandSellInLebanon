package com.musta.buyandsellinlebanon.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.musta.buyandsellinlebanon.db.cars.Cars;
import com.musta.buyandsellinlebanon.db.cars.CarsDao;
import com.musta.buyandsellinlebanon.db.cars_sub.CarsSub;
import com.musta.buyandsellinlebanon.db.cars_sub.CarsSubDao;
import com.musta.buyandsellinlebanon.db.places.Places;
import com.musta.buyandsellinlebanon.db.places.PlacesDao;


@Database(entities = {
        Places.class,
        Cars.class,
        CarsSub.class,
//        Books.class,
}, version = 6, exportSchema = true)
public abstract class AppRoomDatabase extends RoomDatabase {

    public abstract PlacesDao placesDao();

    public abstract CarsDao carsDao();

    public abstract CarsSubDao carsSubDao();
//    public abstract BooksDao booksDao();


//    static final Migration MIGRATION_1_2 = new Migration(2, 3) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE favorites "
//                    + " ADD COLUMN listens long");
//        }
//    };


    private static volatile AppRoomDatabase INSTANCE;

    public static AppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, "buy_and_sell_db")
                            //.addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
//                            .addMigrations(MIGRATION_1_2,MIGRATION_2_3,MIGRATION_3_4)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
//            new PopulateDbAsync(INSTANCE).execute();
        }
    };


//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//
//        private final BooksDao mDao;
//
//        PopulateDbAsync(AppRoomDatabase db) {
//            mDao = db.booksDao();
//        }
//
//        @Override
//        protected Void doInBackground(final Void... params) {
//            //           mDao.deleteAll();
////            BannerAds bannerAds = new BannerAds();
////            bannerAds.setBrand("2");
////            bannerAds.setCreated_at(2);
////            bannerAds.setDescription("2");
////            bannerAds.setImage("2");
////            bannerAds.setIs_published(2);
////            bannerAds.setTitle("2");
////            bannerAds.setTitle_en("2");
////            bannerAds.setUpdated_at(2);
////
////            mDao.insert(bannerAds);
//            return null;
//        }
//    }


}
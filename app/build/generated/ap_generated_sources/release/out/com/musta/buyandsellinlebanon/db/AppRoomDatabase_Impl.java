package com.musta.buyandsellinlebanon.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.musta.buyandsellinlebanon.db.cars.CarsDao;
import com.musta.buyandsellinlebanon.db.cars.CarsDao_Impl;
import com.musta.buyandsellinlebanon.db.cars_sub.CarsSubDao;
import com.musta.buyandsellinlebanon.db.cars_sub.CarsSubDao_Impl;
import com.musta.buyandsellinlebanon.db.places.PlacesDao;
import com.musta.buyandsellinlebanon.db.places.PlacesDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class AppRoomDatabase_Impl extends AppRoomDatabase {
  private volatile PlacesDao _placesDao;

  private volatile CarsDao _carsDao;

  private volatile CarsSubDao _carsSubDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(6) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `places` (`place_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `place_name` TEXT, `place_name_ar` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `cars` (`car_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `car_name` TEXT, `car_name_ar` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `cars_sub` (`model_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `model_name` TEXT, `model_name_ar` TEXT, `car_id` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"a93f65b14d180e46d9a83244313caf1e\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `places`");
        _db.execSQL("DROP TABLE IF EXISTS `cars`");
        _db.execSQL("DROP TABLE IF EXISTS `cars_sub`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPlaces = new HashMap<String, TableInfo.Column>(3);
        _columnsPlaces.put("place_id", new TableInfo.Column("place_id", "INTEGER", true, 1));
        _columnsPlaces.put("place_name", new TableInfo.Column("place_name", "TEXT", false, 0));
        _columnsPlaces.put("place_name_ar", new TableInfo.Column("place_name_ar", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlaces = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlaces = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlaces = new TableInfo("places", _columnsPlaces, _foreignKeysPlaces, _indicesPlaces);
        final TableInfo _existingPlaces = TableInfo.read(_db, "places");
        if (! _infoPlaces.equals(_existingPlaces)) {
          throw new IllegalStateException("Migration didn't properly handle places(com.musta.buyandsellinlebanon.db.places.Places).\n"
                  + " Expected:\n" + _infoPlaces + "\n"
                  + " Found:\n" + _existingPlaces);
        }
        final HashMap<String, TableInfo.Column> _columnsCars = new HashMap<String, TableInfo.Column>(3);
        _columnsCars.put("car_id", new TableInfo.Column("car_id", "INTEGER", true, 1));
        _columnsCars.put("car_name", new TableInfo.Column("car_name", "TEXT", false, 0));
        _columnsCars.put("car_name_ar", new TableInfo.Column("car_name_ar", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCars = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCars = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCars = new TableInfo("cars", _columnsCars, _foreignKeysCars, _indicesCars);
        final TableInfo _existingCars = TableInfo.read(_db, "cars");
        if (! _infoCars.equals(_existingCars)) {
          throw new IllegalStateException("Migration didn't properly handle cars(com.musta.buyandsellinlebanon.db.cars.Cars).\n"
                  + " Expected:\n" + _infoCars + "\n"
                  + " Found:\n" + _existingCars);
        }
        final HashMap<String, TableInfo.Column> _columnsCarsSub = new HashMap<String, TableInfo.Column>(4);
        _columnsCarsSub.put("model_id", new TableInfo.Column("model_id", "INTEGER", true, 1));
        _columnsCarsSub.put("model_name", new TableInfo.Column("model_name", "TEXT", false, 0));
        _columnsCarsSub.put("model_name_ar", new TableInfo.Column("model_name_ar", "TEXT", false, 0));
        _columnsCarsSub.put("car_id", new TableInfo.Column("car_id", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCarsSub = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCarsSub = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCarsSub = new TableInfo("cars_sub", _columnsCarsSub, _foreignKeysCarsSub, _indicesCarsSub);
        final TableInfo _existingCarsSub = TableInfo.read(_db, "cars_sub");
        if (! _infoCarsSub.equals(_existingCarsSub)) {
          throw new IllegalStateException("Migration didn't properly handle cars_sub(com.musta.buyandsellinlebanon.db.cars_sub.CarsSub).\n"
                  + " Expected:\n" + _infoCarsSub + "\n"
                  + " Found:\n" + _existingCarsSub);
        }
      }
    }, "a93f65b14d180e46d9a83244313caf1e", "7d0c7901d442e316a02bfbae9a8378e7");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "places","cars","cars_sub");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `places`");
      _db.execSQL("DELETE FROM `cars`");
      _db.execSQL("DELETE FROM `cars_sub`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public PlacesDao placesDao() {
    if (_placesDao != null) {
      return _placesDao;
    } else {
      synchronized(this) {
        if(_placesDao == null) {
          _placesDao = new PlacesDao_Impl(this);
        }
        return _placesDao;
      }
    }
  }

  @Override
  public CarsDao carsDao() {
    if (_carsDao != null) {
      return _carsDao;
    } else {
      synchronized(this) {
        if(_carsDao == null) {
          _carsDao = new CarsDao_Impl(this);
        }
        return _carsDao;
      }
    }
  }

  @Override
  public CarsSubDao carsSubDao() {
    if (_carsSubDao != null) {
      return _carsSubDao;
    } else {
      synchronized(this) {
        if(_carsSubDao == null) {
          _carsSubDao = new CarsSubDao_Impl(this);
        }
        return _carsSubDao;
      }
    }
  }
}

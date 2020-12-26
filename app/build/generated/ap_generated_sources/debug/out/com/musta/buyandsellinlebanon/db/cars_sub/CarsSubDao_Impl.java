package com.musta.buyandsellinlebanon.db.cars_sub;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class CarsSubDao_Impl implements CarsSubDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCarsSub;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public CarsSubDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCarsSub = new EntityInsertionAdapter<CarsSub>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `cars_sub`(`model_id`,`model_name`,`model_name_ar`,`car_id`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CarsSub value) {
        stmt.bindLong(1, value.getModel_id());
        if (value.getModel_name() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getModel_name());
        }
        if (value.getModel_name_ar() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getModel_name_ar());
        }
        stmt.bindLong(4, value.getCar_id());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM cars_sub";
        return _query;
      }
    };
  }

  @Override
  public void insert(CarsSub carsSub) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfCarsSub.insert(carsSub);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<CarsSub>> getLiveDataCarsSub() {
    final String _sql = "SELECT * from cars_sub";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<CarsSub>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<CarsSub> compute() {
        if (_observer == null) {
          _observer = new Observer("cars_sub") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfModelId = _cursor.getColumnIndexOrThrow("model_id");
          final int _cursorIndexOfModelName = _cursor.getColumnIndexOrThrow("model_name");
          final int _cursorIndexOfModelNameAr = _cursor.getColumnIndexOrThrow("model_name_ar");
          final int _cursorIndexOfCarId = _cursor.getColumnIndexOrThrow("car_id");
          final List<CarsSub> _result = new ArrayList<CarsSub>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CarsSub _item;
            _item = new CarsSub();
            final long _tmpModel_id;
            _tmpModel_id = _cursor.getLong(_cursorIndexOfModelId);
            _item.setModel_id(_tmpModel_id);
            final String _tmpModel_name;
            _tmpModel_name = _cursor.getString(_cursorIndexOfModelName);
            _item.setModel_name(_tmpModel_name);
            final String _tmpModel_name_ar;
            _tmpModel_name_ar = _cursor.getString(_cursorIndexOfModelNameAr);
            _item.setModel_name_ar(_tmpModel_name_ar);
            final int _tmpCar_id;
            _tmpCar_id = _cursor.getInt(_cursorIndexOfCarId);
            _item.setCar_id(_tmpCar_id);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<CarsSub>> getLiveDataCarsSubByCarId(long carId) {
    final String _sql = "SELECT * from cars_sub where car_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, carId);
    return new ComputableLiveData<List<CarsSub>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<CarsSub> compute() {
        if (_observer == null) {
          _observer = new Observer("cars_sub") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfModelId = _cursor.getColumnIndexOrThrow("model_id");
          final int _cursorIndexOfModelName = _cursor.getColumnIndexOrThrow("model_name");
          final int _cursorIndexOfModelNameAr = _cursor.getColumnIndexOrThrow("model_name_ar");
          final int _cursorIndexOfCarId = _cursor.getColumnIndexOrThrow("car_id");
          final List<CarsSub> _result = new ArrayList<CarsSub>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CarsSub _item;
            _item = new CarsSub();
            final long _tmpModel_id;
            _tmpModel_id = _cursor.getLong(_cursorIndexOfModelId);
            _item.setModel_id(_tmpModel_id);
            final String _tmpModel_name;
            _tmpModel_name = _cursor.getString(_cursorIndexOfModelName);
            _item.setModel_name(_tmpModel_name);
            final String _tmpModel_name_ar;
            _tmpModel_name_ar = _cursor.getString(_cursorIndexOfModelNameAr);
            _item.setModel_name_ar(_tmpModel_name_ar);
            final int _tmpCar_id;
            _tmpCar_id = _cursor.getInt(_cursorIndexOfCarId);
            _item.setCar_id(_tmpCar_id);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}

package com.musta.buyandsellinlebanon.db.cars;

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
public final class CarsDao_Impl implements CarsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCars;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public CarsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCars = new EntityInsertionAdapter<Cars>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `cars`(`car_id`,`car_name`,`car_name_ar`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cars value) {
        stmt.bindLong(1, value.getCar_id());
        if (value.getCar_name() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCar_name());
        }
        if (value.getCar_name_ar() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCar_name_ar());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM cars";
        return _query;
      }
    };
  }

  @Override
  public void insert(Cars cars) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfCars.insert(cars);
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
  public LiveData<List<Cars>> getLiveDataCars() {
    final String _sql = "SELECT * from cars";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Cars>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<Cars> compute() {
        if (_observer == null) {
          _observer = new Observer("cars") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfCarId = _cursor.getColumnIndexOrThrow("car_id");
          final int _cursorIndexOfCarName = _cursor.getColumnIndexOrThrow("car_name");
          final int _cursorIndexOfCarNameAr = _cursor.getColumnIndexOrThrow("car_name_ar");
          final List<Cars> _result = new ArrayList<Cars>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Cars _item;
            _item = new Cars();
            final long _tmpCar_id;
            _tmpCar_id = _cursor.getLong(_cursorIndexOfCarId);
            _item.setCar_id(_tmpCar_id);
            final String _tmpCar_name;
            _tmpCar_name = _cursor.getString(_cursorIndexOfCarName);
            _item.setCar_name(_tmpCar_name);
            final String _tmpCar_name_ar;
            _tmpCar_name_ar = _cursor.getString(_cursorIndexOfCarNameAr);
            _item.setCar_name_ar(_tmpCar_name_ar);
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

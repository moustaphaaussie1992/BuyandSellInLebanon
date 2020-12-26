package com.musta.buyandsellinlebanon.db.places;

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
public final class PlacesDao_Impl implements PlacesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPlaces;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public PlacesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlaces = new EntityInsertionAdapter<Places>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `places`(`place_id`,`place_name`,`place_name_ar`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Places value) {
        stmt.bindLong(1, value.getPlace_id());
        if (value.getPlace_name() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPlace_name());
        }
        if (value.getPlace_name_ar() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPlace_name_ar());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM places";
        return _query;
      }
    };
  }

  @Override
  public void insert(Places places) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPlaces.insert(places);
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
  public LiveData<List<Places>> getLiveDataPlaces() {
    final String _sql = "SELECT * from places";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Places>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<Places> compute() {
        if (_observer == null) {
          _observer = new Observer("places") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfPlaceId = _cursor.getColumnIndexOrThrow("place_id");
          final int _cursorIndexOfPlaceName = _cursor.getColumnIndexOrThrow("place_name");
          final int _cursorIndexOfPlaceNameAr = _cursor.getColumnIndexOrThrow("place_name_ar");
          final List<Places> _result = new ArrayList<Places>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Places _item;
            _item = new Places();
            final long _tmpPlace_id;
            _tmpPlace_id = _cursor.getLong(_cursorIndexOfPlaceId);
            _item.setPlace_id(_tmpPlace_id);
            final String _tmpPlace_name;
            _tmpPlace_name = _cursor.getString(_cursorIndexOfPlaceName);
            _item.setPlace_name(_tmpPlace_name);
            final String _tmpPlace_name_ar;
            _tmpPlace_name_ar = _cursor.getString(_cursorIndexOfPlaceNameAr);
            _item.setPlace_name_ar(_tmpPlace_name_ar);
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

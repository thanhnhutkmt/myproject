/**
 * Licensed to Open-Ones Group under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Open-Ones Group licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package nhutLT.soft.SQLite;

import java.lang.reflect.Method;
import java.nio.channels.spi.AbstractSelectableChannel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author nhutlt
 */
public class DBAdapter {

    private myDBCreater DBCreater;
    private SQLiteDatabase myDB;
    private Context DBContext;
    public static final String DBName = "NhutDB.db";
    public static final String tableName = "users";
    public static final String createDBString = "create table " + tableName + " (_id integer primary "
            + "key autoincrement, name text not null, age integer not null);";
    public static final int ver = 1;

    public class myDBCreater extends SQLiteOpenHelper {

        /**
         * @param context
         * @param name
         * @param factory
         * @param version
         */
        public myDBCreater(Context context) {
            super(context, DBName, null, ver);
            // TODO Auto-generated constructor stub           
        }

        /**
         * [Explain the description for this method here].
         * 
         * @param db
         * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(createDBString);
        }
        /**
         * [Explain the description for this method here].
         * 
         * @param db
         * @param oldVersion
         * @param newVersion
         * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("Drop table if exists");
            onCreate(db);
        }
    }

    public DBAdapter(Context context) {
   //     DBContext = context;
        DBCreater = new myDBCreater(context);
    }

    public long insertDB(ContentValues data) {
        myDB = DBCreater.getWritableDatabase();
        long code = myDB.insert(tableName, null, data);
        myDB.close();
        return code;
    }

    public Cursor queryDB() {
        myDB = DBCreater.getReadableDatabase();
        return myDB.query(tableName, null, null, null, null, null, null, null);
    }

    public void closeDB() {
        DBCreater.close();
    }
}

package com.tty.desh.storage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.tty.desh.model.User
import java.sql.SQLException

class DatabaseHelper(var context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(STRUCT_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    var dbForUser = DBForUser()

    inner class DBForUser: IDBForModel<User>{
        override fun getIds(): ArrayList<Int> {
            val cursor = readableDatabase.rawQuery("select _id from $NAME_USER", arrayOf<String>())
            val data = ArrayList<Int>()
            if (cursor.moveToFirst() && cursor.count > 0){
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("_id"))
                    data.add(id)
                } while (cursor.moveToNext())
            }
            try {
                cursor.close()
            } catch (e: SQLException){
                e.printStackTrace()
            }
        }

        override fun getData(id: Int): User {
            val cursor = readableDatabase.rawQuery("select * from $NAME_USER where _id = ?", arrayOf(id.toString()))
            if (cursor.moveToFirst() && cursor.count > 0){
                return User(
                    id,
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("nickname")),
                    cursor.getString(cursor.getColumnIndex("token")),
                    cursor.getString(cursor.getColumnIndex("update_time")),
                    false
                )
            }
            try {
                cursor.close()
            } catch (e:SQLException){
                e.printStackTrace()
            }
        }

        override fun updateData(id: Int, data: User) {

        }

    }

    companion object{
        const val DB_NAME = "desh"
        const val DB_VERSION = 1
        const val NAME_USER = "user"
        const val NAME_SETTING = "settings"

        private const val STRUCT_USER = "create table $NAME_USER (_id integer primary key autoincrement, name varchar(40) not null, nickname varchar(40) not null, token varchar(255), update_time varchar(40) not null)"

    }
}
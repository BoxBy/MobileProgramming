package com.example.myrecipe

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MartDBHelper (val context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
        companion object {
            val DB_NAME = "mydb1011.db"
            val DB_VERSION = 1
            val TABLE_NAME = "mylocation4"
            val mart_num="mart_number"
            val lat="latitude"
            val lng="longitude"
            val name="name"
            val city="City"
            val location="location"
        }
        var data: ArrayList<MartData> = ArrayList()

        fun search_cityin_Mart(names:String):ArrayList<MartData>{
            data.clear()
            val strsql="select * from $TABLE_NAME where $city like'$names%';"
            val db=readableDatabase
            val cursor=db.rawQuery(strsql,null)
            val flag=cursor.count!=0
            if(flag) {
                cursor.moveToFirst()
                do {

                    var mar_num = cursor.getString(0).toInt()

                    var lat = cursor.getString(1).toString()
                    if(lat=="-1")
                        continue
                    var long = cursor.getString(2).toString()
                    var naems=cursor.getString(5).toString()
                    var city = cursor.getString(3).toString()
                    var locations = cursor.getString(4).toString()
                    data.add(MartData(mar_num, city, naems, locations, lat, long))
                } while (cursor.moveToNext())
            }

            cursor.close()
            db.close()
            return data
        }
        /*
            fun returns_location(names:String):locations
            {
                val strsql="select * from $TABLE_NAME where $title like'$names%';"
                val db=readableDatabase
                val cursor=db.rawQuery(strsql,null)
                val flag=cursor.count!=0
                    var id=cursor.getString(0).toInt()
                    var lat=cursor.getString(1).toDouble()
                    var lng=cursor.getString(2).toDouble()
                    var title=cursor.getString(3).toString()
                    var content=cursor.getString(4).toString()
                    var alpa=locations(id,lat,lng,title,content)
                    cursor.close()
                    db.close()
                    return alpa





            }

             */
        fun search_title(names:String):String{

            val strsql="select * from $TABLE_NAME where $city like'$names%';"
            val db=readableDatabase
            val cursor=db.rawQuery(strsql,null)
            val flag=cursor.count!=0
            if(flag)
            {

                var beta=cursor.getString(4).toString()
                cursor.close()
                db.close()
                return beta

            }

            return "1"

        }

        fun getAllRecord():ArrayList<MartData> {
            data.clear()
            val strsql = "select * from $TABLE_NAME;"
            val db = readableDatabase
            val cursor = db.rawQuery(strsql, null)
            while(cursor.moveToNext()){
                var mar_num = cursor.getString(0).toInt()
                var lat = cursor.getString(1).toString()
                if(lat=="-1")
                    continue
                var long = cursor.getString(2).toString()
                var naems=cursor.getString(5).toString()
                var city = cursor.getString(3).toString()
                var locations = cursor.getString(4).toString()
                data.add(MartData(mar_num, city, naems, locations, lat, long))
            }
            cursor.close()
            db.close()
            return data
        }


        fun insertProduct(locations : MartData): Boolean {


            val values = ContentValues()
            values.put(mart_num,locations.mart_num)
            if(locations.latitude=="")
                return false
            values.put(lat ,locations.latitude.toDouble())
            values.put(lng ,locations.lontitude.toDouble())
            values.put(city ,locations.city)
            values.put(location ,locations.location)
            values.put(name, locations.name)
            val db = writableDatabase
            if (db.insert(TABLE_NAME, null, values) > 0) {

                db.close()
                return true

            } else {
                db.close()
                return false
            }
        }




        override fun onCreate(db: SQLiteDatabase?) {
            val create_table = "create table if not exists $TABLE_NAME(" +
                    "$mart_num integer primary key autoincrement," +
                    "$lat double," +
                    "$lng double," +
                    "$city text," +
                    "$location text," +
                    "$name text);"
            db!!.execSQL(create_table)


        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            val drop_table = "drop table if exists $TABLE_NAME;"
            db!!.execSQL(drop_table)
            onCreate(db)

        }
}
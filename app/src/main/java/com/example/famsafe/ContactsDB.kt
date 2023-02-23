package com.example.famsafe

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ContactModel::class],version=1, exportSchema = false)
public abstract class ContactsDB :RoomDatabase(){
    abstract fun contactsDao(): ContactsDao

    companion object{
        @Volatile
        private var INSTANCE: ContactsDB?=null
        fun DataBase(context:Context) : ContactsDB{
            INSTANCE?.let{
                return it
            }
            return synchronized(ContactsDB::class.java){
                val instance= Room.databaseBuilder(context.applicationContext,ContactsDB::class.java,"FamSafeDB").build()
                INSTANCE=instance
                instance
            }

        }

    }
}
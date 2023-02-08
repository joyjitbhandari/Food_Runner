package com.joyjit.foodrunner.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FoodEntity::class], version = 2)
abstract class FoodDatabase:RoomDatabase() {

    abstract fun foodDao(): FoodDao
}

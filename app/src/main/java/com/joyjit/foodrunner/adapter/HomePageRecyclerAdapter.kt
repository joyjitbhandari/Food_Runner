package com.joyjit.foodrunner.adapter

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.joyjit.foodrunner.R
import com.joyjit.foodrunner.database.FoodDatabase
import com.joyjit.foodrunner.database.FoodEntity
import com.joyjit.foodrunner.model.Food
import com.squareup.picasso.Picasso

class HomePageRecyclerAdapter(val context: Context, val itemList: ArrayList<Food>) :
    RecyclerView.Adapter<HomePageRecyclerAdapter.homepageViewholder>(){

    override fun onCreateViewHolder(parent: ViewGroup, Viewtype: Int): homepageViewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_homepage_single_row_item, parent, false)
        return homepageViewholder(view)
    }


    override fun onBindViewHolder(holder: homepageViewholder, position: Int) {
        val food = itemList[position]
        holder.txtFoodName.text = food.foodName
        holder.txtFoodPrice.text = food.foodPrice
        holder.txtFoodRating.text = food.foodRating


        Picasso.get().load(food.foodImage).error(R.drawable.default_cover_photo)
            .into(holder.imageFoodImage)


        holder.content.setOnClickListener {
            Toast.makeText(context, "Clicked On ${holder.txtFoodName.text}", Toast.LENGTH_SHORT)
                .show()
        }

        val foodEntity = FoodEntity(
            food.foodId.toInt(),
            holder.txtFoodName.text.toString(),
            holder.txtFoodRating.text.toString(),
            holder.txtFoodPrice.text.toString(),
            food.foodImage
        )

        val checkFav = DBAsyncTask(context ,foodEntity,1).execute()
        val isFav = checkFav.get()
        if(isFav){
           holder.imgFoodfav.setBackgroundResource(R.drawable.ic_favourite)
        }else{
           holder.imgFoodfav.setBackgroundResource(R.drawable.ic_unfavourite)
        }

        holder.imgFoodfav.setOnClickListener {
            if (!DBAsyncTask(context, foodEntity, 1).execute().get()) {
                val async = DBAsyncTask(context, foodEntity, 2).execute()
                val result = async.get()
                if (result) {
                    holder.imgFoodfav.setBackgroundResource(R.drawable.ic_favourite)
                } else {
                    Toast.makeText(context, "Some Error Occurred!!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                val async = DBAsyncTask(context, foodEntity, 3).execute()
                val result = async.get()
                if (result) {
                    holder.imgFoodfav.setBackgroundResource(R.drawable.ic_unfavourite)
                } else {
                    Toast.makeText(context, "Some Error Occurred!!!", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    inner class homepageViewholder(view: View) : RecyclerView.ViewHolder(view) {
        val txtFoodName: TextView = view.findViewById(R.id.txtFoodName)
        val txtFoodPrice: TextView = view.findViewById(R.id.txtFoodPrice)
        val txtFoodRating: TextView = view.findViewById(R.id.txtFoodRating)
        val imageFoodImage: ImageView = view.findViewById(R.id.imgFoodImage)
        val content: LinearLayout = view.findViewById(R.id.content)
        val imgFoodfav: ImageView = view.findViewById(R.id.imgFoodfav)
    }

    class DBAsyncTask(val context: Context, val foodEntity: FoodEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        /*
        Mode 1 -> Check Db if the book is favourite or not
        Mode 2 -> Save the book int DB as favourite
        Mode 3 -> Remove the favourite book
        */
        val db = Room.databaseBuilder(context, FoodDatabase::class.java, "foods_db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    //Check Db if the book is favourite or not
                    val food: FoodEntity? = db.foodDao().getFoodById(foodEntity.food_id.toString())
                    db.close()
                    return food != null
                }
                2 -> {
                    //Save the book int DB as favourite
                    db.foodDao().insertFood(foodEntity)
                    db.close()
                    return true
                }
                3 -> {
                    //Remove the favourite book
                    db.foodDao().deleteFood(foodEntity)
                    db.close()
                    return true
                }
            }

            return false
        }
    }
}

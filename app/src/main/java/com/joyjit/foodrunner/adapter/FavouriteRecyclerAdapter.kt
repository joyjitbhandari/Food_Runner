package com.joyjit.foodrunner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joyjit.foodrunner.R
import com.joyjit.foodrunner.database.FoodEntity
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context, val foodList: List<FoodEntity>) :
    RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_favourite_single_row, parent, false)

        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val food = foodList[position]

        holder.txtFavFoodName.text = food.foodName
        holder.txtFavFoodRating.text = food.foodRating
        holder.txtFavFoodPrice.text = food.foodPrice
        Picasso.get().load(food.foodImage).error(R.drawable.default_cover_photo)
            .into(holder.imgFavFoodImage)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtFavFoodName: TextView = view.findViewById(R.id.txtFavFoodName)
        val txtFavFoodPrice: TextView = view.findViewById(R.id.txtFavFoodPrice)
        val txtFavFoodRating: TextView = view.findViewById(R.id.txtFavFoodRating)
        val imgFavFoodImage: ImageView = view.findViewById(R.id.imgFavFoodImage)
        val FavContent: LinearLayout = view.findViewById(R.id.FavContent)
    }
}
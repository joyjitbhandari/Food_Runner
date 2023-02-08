package com.joyjit.foodrunner.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.joyjit.foodrunner.R
import com.joyjit.foodrunner.adapter.FavouriteRecyclerAdapter
import com.joyjit.foodrunner.database.FoodDatabase
import com.joyjit.foodrunner.database.FoodEntity


class FavouritesFragment : Fragment() {
    lateinit var recyclerFavourite: RecyclerView
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FavouriteRecyclerAdapter

    var dbFoodList = listOf<FoodEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favouries, container, false)
        recyclerFavourite = view.findViewById(R.id.recyclerFavourites)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout.visibility = View.VISIBLE

        layoutManager = GridLayoutManager(activity as Context,2)

        dbFoodList = RetrieveFavourites(activity as Context).execute().get()

        if(activity != null){
            progressLayout.visibility = View.GONE
            recyclerAdapter = FavouriteRecyclerAdapter(activity as Context, dbFoodList)
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite.layoutManager = layoutManager
        }

        return view
    }

    class RetrieveFavourites(val context: Context):AsyncTask<Void,Void,List<FoodEntity>>(){
        override fun doInBackground(vararg params: Void?): List<FoodEntity> {
            val db = Room.databaseBuilder(context, FoodDatabase::class.java,"foods_db").build()

            return db.foodDao().getALLFoods()
        }

    }
}
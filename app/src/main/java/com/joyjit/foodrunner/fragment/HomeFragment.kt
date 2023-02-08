package com.joyjit.foodrunner.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.joyjit.foodrunner.R
import com.joyjit.foodrunner.adapter.HomePageRecyclerAdapter
import com.joyjit.foodrunner.model.Food
import com.joyjit.foodrunner.utility.ConnectionManager
import org.json.JSONException
import kotlin.collections.HashMap


@Suppress("UNREACHABLE_CODE")
class HomeFragment : Fragment() {
    lateinit var Recyclerhome: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var recyclerAdapter: HomePageRecyclerAdapter

    lateinit var progressLayout: RelativeLayout

    lateinit var progressBar : ProgressBar

   val foodinfoList = kotlin.collections.arrayListOf<Food>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        Recyclerhome = view.findViewById(R.id.Recyclerhome)
        layoutManager = LinearLayoutManager(activity)

        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout.visibility = View.VISIBLE

        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v2/restaurants/fetch_result"

        if (ConnectionManager().CheckConnectivity(activity as Context)){
            val jsonObjectRequest = object: JsonObjectRequest(
                Method.GET,
                url,
                null,
                Response.Listener {response->
                    // here will be handle the response
                    try {
                        progressLayout.visibility = View.GONE
                        val data = response.getJSONObject("data")
                        val success = data.getBoolean("success")
                        if (success) {
                            val jsonArray = data.getJSONArray("data")
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                val restaurant = Food(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("rating"),
                                    jsonObject.getString("cost_for_one"),
                                    jsonObject.getString("image_url")
                                )
                                foodinfoList.add(restaurant)
                                recyclerAdapter =
                                    HomePageRecyclerAdapter(activity as Context, foodinfoList)
                                Recyclerhome.adapter = recyclerAdapter
                                Recyclerhome.layoutManager = layoutManager
                            }


                        }else{
                            Toast.makeText(
                                activity as Context,
                                "Some Error Occurred!!!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }catch (e: JSONException){
                        Toast.makeText(
                            activity as Context,
                            "Some unexpected error occurred!!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener {
                    // here will be handle error
                    Toast.makeText(activity as Context, "volly Error occurred!!!!", Toast.LENGTH_SHORT).show()
                }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"]="application/json"
                    headers["token"] = "9bf534118365f1"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        }else{
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection Not Found.")

            dialog.setPositiveButton("Open Settings"){text, listener->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit"){text, listener->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }

        return view
    }

}
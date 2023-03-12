package com.cbi_solar.cbisolar

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.cbi_solar.ChangePasswordsActivity
import com.cbi_solar.DashBoardMenuListModel
import com.cbi_solar.cbisolar.adapter.DashBoardMenuList
import com.cbi_solar.cbisolar.databinding.ActivityMainBinding
import com.cbi_solar.helper.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.JsonObject
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.picasso.PicassoImageLoaderFactory
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    fun closeDrawerBar() {
        var mDrawerLayout: DrawerLayout = binding.drawerLayout

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers()
        }
    }

    fun setData() {
        binding.includeNavigation.txtNameAndId.text =
            "" + MyApplication.ReadStringPreferences(ApiContants.PREF_F_name)
        binding.txtName.text = "" + MyApplication.ReadStringPreferences(ApiContants.PREF_F_name)
    }

    @SuppressLint("WrongConstant")
    fun clickListener() {
        var mDrawerLayout: DrawerLayout = binding.drawerLayout

        binding.imgMenu.setOnClickListener {
            if (!mDrawerLayout.isDrawerOpen(Gravity.START)) mDrawerLayout.openDrawer(Gravity.START)
            else mDrawerLayout.closeDrawer(Gravity.END)
        }

        binding.includeNavigation.layoutSignOut.setOnClickListener {
            closeDrawerBar()
            AlertDialog.Builder(this)
                .setMessage("Are you sure you want to sign out?")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { dialog, id -> MyApplication.logout(true) }
                .setNegativeButton("No", null)
                .show()
        }

        binding.bottomNev.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    closeDrawerBar()
                    setProductList()
                    setBanner_list()
                }
                R.id.winners -> {
                    startActivity(Intent(this@MainActivity, DiscountActivity::class.java))
                }
                R.id.store -> {
                    startActivity(Intent(this@MainActivity, StoreListActivity::class.java))
                }
                R.id.profile -> {
                    startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
                }
            }
            true
        })
        binding.includeNavigation.layoutServiceRequest.setOnClickListener {
            closeDrawerBar()
            startActivity(Intent(this@MainActivity, ServicesForm::class.java))
        }

        binding.includeNavigation.layoutTrackStatus.setOnClickListener {
            closeDrawerBar()
            startActivity(Intent(this@MainActivity, TrackStatusActivity::class.java))
        }

        binding.includeNavigation.layoutChangePassword.setOnClickListener {
            closeDrawerBar()
            startActivity(Intent(this@MainActivity, ChangePasswordsActivity::class.java))
        }

        binding.includeNavigation.layoutPrivacyPolicy.setOnClickListener {
            closeDrawerBar()
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(com.cbi_solar.helper.ApiContants.PREF_privacypolicy)
                )
            )
        }

        binding.imgNotifaction.setOnClickListener {
            closeDrawerBar()
            startActivity(Intent(this@MainActivity, NotificationListActivity::class.java))
        }
    }

    var progressDialog: SweetAlertDialog? = null
    val listBanner: MutableList<String> = mutableListOf()
    val listTitle: MutableList<String> = mutableListOf()
    lateinit var binding: ActivityMainBinding
    private lateinit var imageSlider: ImageSlider

    fun setBanner_list() {
        progressDialog!!.show()
        listBanner.clear()
        listTitle.clear()
        val apiInterface: ApiInterface =
            RetrofitManager().instance!!.create(ApiInterface::class.java)

        apiInterface.banner_list()?.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    " " + t.message.toString(),
                    Toast.LENGTH_LONG
                )
                    .show()
                progressDialog!!.dismiss()
                adapter?.setData(java.util.ArrayList<DashBoardMenuListModel>())
            }

            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                progressDialog!!.dismiss()


                try {


                    val jsonObject = JSONObject(response.body().toString())

                    if (jsonObject.getBoolean("status")) {
                        var jsonArray = jsonObject.getJSONArray("responseBody")

                        if (jsonArray.length() > 0) {

                            for (i in 0 until jsonArray.length()) {

                                var json = JSONObject(jsonArray.getJSONObject(i).toString())
                                listBanner.add(ApiContants.PREF_ImageUrl + json.getString("banner_img"))
                                listTitle.add("")
                            }
                            if (listBanner.size > 0) {
                                imageSlider.adapter = SliderAdapter(
                                    this@MainActivity,
                                    PicassoImageLoaderFactory(),
                                    imageUrls = listBanner,
                                    descriptions = listTitle
                                )
                            }
                        } else {
                            binding.c1.visibility = View.GONE
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@MainActivity,
                        " " + resources.getString(R.string.error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                if (response.body() == null) {
                    adapter!!.setData(ArrayList<DashBoardMenuListModel>())
                } else {
                    adapter!!.setData(listProduct)
                }
            }

        })
    }

    fun setProductList() {
        progressDialog!!.show()
        listProduct.clear()
        val apiInterface: ApiInterface =
            RetrofitManager().instance!!.create(ApiInterface::class.java)

        apiInterface.product_list()?.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    " " + t.message.toString(),
                    Toast.LENGTH_LONG
                )
                    .show()
                progressDialog!!.dismiss()
                adapter?.setData(java.util.ArrayList<DashBoardMenuListModel>())
            }

            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                progressDialog!!.dismiss()
                try {
                    val jsonObject = JSONObject(response.body().toString())
                    if (jsonObject.getBoolean("status")) {
                        var jsonArray = jsonObject.getJSONArray("responseBody")
                        if (jsonArray.length() > 0) {
                            for (i in 0 until jsonArray.length()) {
                                var json = JSONObject(jsonArray.getJSONObject(i).toString())

                                val data = DashBoardMenuListModel()
                                data.product_name = json.getString("product_name")
                                data.price = json.getString("price")
                                data.product_img = json.getString("product_img")
                                data.isactive = json.getString("isactive")
                                data.id = json.getString("id")
                                data.model = json.getString("model")
                                data.discount = json.getString("discount")

                                if (json.has("mrp"))
                                    data.mrp = json.getString("mrp")
                                else data.mrp = "0"

                                listProduct.add(data)
                            }
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@MainActivity,
                        " " + resources.getString(R.string.error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                if (response.body() == null) {
                    adapter!!.setData(ArrayList<DashBoardMenuListModel>())
                } else {
                    adapter!!.setData(listProduct)
                }
            }

        })
    }


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.drawerLayout.bringToFront()

        progressDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        progressDialog!!.progressHelper.barColor = R.color.theme_color
        progressDialog!!.titleText = "Loading ..."
        progressDialog!!.setCancelable(false)

        imageSlider = findViewById(R.id.image_slider)


        setData()
        clickListener()
        initRecyclerView()
        setProductList()
        setBanner_list()


    }

    fun initGlide(): RequestManager? {
        val options: RequestOptions = RequestOptions()
            .placeholder(R.drawable.ic_baseline_store_24)
            .error(R.drawable.red_button_background)
        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }

    var listProduct: ArrayList<DashBoardMenuListModel> = ArrayList()
    var adapter: DashBoardMenuList? = null
    fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        val itemDecorator = VerticalSpacingItemDecorator(20)
        binding.recyclerView.addItemDecoration(itemDecorator)
        adapter = DashBoardMenuList(initGlide()!!)
        binding.recyclerView.adapter = adapter
    }

    private var exit = false
    override fun onBackPressed() {
        closeDrawerBar()
        if (exit) {
            finish() // finish activity
        } else {
            Toast.makeText(
                this, "Press Back again to Exit.",
                Toast.LENGTH_SHORT
            ).show()
            exit = true
            Handler().postDelayed({ exit = false }, 3 * 1000)

        }
    }
}
package com.cbi_solar.cbisolar

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.cbi_solar.DashBoardMenuListModel
import com.cbi_solar.WinnerListModel
import com.cbi_solar.cbisolar.adapter.DashBoardMenuList
import com.cbi_solar.cbisolar.adapter.WinnerListAdapter
import com.cbi_solar.cbisolar.databinding.ActivityDiscountBinding
import com.cbi_solar.helper.ApiInterface
import com.cbi_solar.helper.RetrofitManager
import com.cbi_solar.helper.VerticalSpacingItemDecorator
import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscountActivity : AppCompatActivity() {

    var progressDialog: SweetAlertDialog? = null
    var listProduct: ArrayList<WinnerListModel> = ArrayList()
    var adapter: WinnerListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discount)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_discount)
        binding.toolbar.txtTitle.text = "Winners List"
        binding.toolbar.imgBack.setOnClickListener { finish() }
        progressDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        progressDialog!!.progressHelper.barColor = R.color.theme_color
        progressDialog!!.titleText = "Loading ..."
        progressDialog!!.setCancelable(false)

        initRecyclerView()
        setProductList()

    }
    fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
        val itemDecorator = VerticalSpacingItemDecorator(20)
        binding.recyclerView.addItemDecoration(itemDecorator)
        adapter = WinnerListAdapter(initGlide()!!)
        binding.recyclerView.adapter = adapter
    }
    lateinit var binding: ActivityDiscountBinding

    fun initGlide(): RequestManager? {
        val options: RequestOptions = RequestOptions()
            .placeholder(R.drawable.ic_baseline_store_24)
            .error(R.drawable.red_button_background)
        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }

    fun setProductList() {
        progressDialog!!.show()
        listProduct.clear()
        val apiInterface: ApiInterface =
            RetrofitManager().instance!!.create(ApiInterface::class.java)

        apiInterface.winner_list()?.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(
                    this@DiscountActivity,
                    " " + t.message.toString(),
                    Toast.LENGTH_LONG
                )
                    .show()
                progressDialog!!.dismiss()
                adapter?.setData(java.util.ArrayList<WinnerListModel>())
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

                                val data = WinnerListModel()
                                data.title = json.getString("title")
//                                data.position = json.getString("position")
                                data.prize = json.getString("prize")
                                data.winner_name = json.getString("winner_name")
                                data.token = json.getString("token_no")
                                listProduct.add(data)
                            }
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@DiscountActivity,
                        " " + resources.getString(R.string.error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                if (response.body() == null) {
                    adapter!!.setData(ArrayList<WinnerListModel>())
                } else {
                    adapter!!.setData(listProduct)
                }
            }

        })
    }


}
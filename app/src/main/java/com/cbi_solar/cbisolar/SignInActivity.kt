package com.cbi_solar.cbisolar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.cbi_solar.cbisolar.databinding.ActivitySignInBinding
import com.cbi_solar.helper.ApiContants
import com.cbi_solar.helper.ApiInterface
import com.cbi_solar.helper.MyApplication
import com.cbi_solar.helper.RetrofitManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding
    var progressDialog: SweetAlertDialog? = null
    var TAG = "@@SignInActivity"

    fun ClickListener() {

        binding.btnSignIn.setOnClickListener {
            binding.l1.setBackgroundResource(R.drawable.edit_txtbg)
            binding.l2.setBackgroundResource(R.drawable.edit_txtbg)
            when {
                binding.txtNumber.text.isEmpty() -> {
                    Utility.showSnackBar(this, "Please enter Number")
                    binding.txtNumber.setBackgroundResource(R.drawable.edit_txt_error)
                    binding.l1.setBackgroundResource(R.drawable.edit_txt_error)
                }
                binding.txtPassword.text.isEmpty() -> {
                    Utility.showSnackBar(this, "Please enter password")
                    binding.l2.setBackgroundResource(R.drawable.edit_txt_error)
                }

                else -> {
                    signIn()
                }
            }
        }
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.txtForgotPassword.setOnClickListener {
            Utility.showSnackBar(this, "Please try later.")
        }
    }


    fun signIn() {
        progressDialog!!.show()
        val apiInterface: ApiInterface =
            RetrofitManager().instance!!.create(ApiInterface::class.java)

        apiInterface.signIn(
            binding.txtNumber.text.toString().trim(),
            binding.txtPassword.text.toString().trim(),
            "1234"
        ).enqueue(object :
            Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(
                    this@SignInActivity,
                    " " + resources.getString(R.string.error),
                    Toast.LENGTH_LONG
                )
                    .show()
                Log.e(TAG, "onFailure: " + t.message)
                progressDialog!!.dismiss()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                progressDialog!!.dismiss()
                try {
                    if (response.isSuccessful) {

                        Log.d(TAG, "onResponse: " + response.body().toString())
                        val jsonObject = JSONObject(response.body().toString())

                        Toast.makeText(this@SignInActivity," "+jsonObject.getString("msg"),Toast.LENGTH_LONG).show()

                        if (jsonObject.getBoolean("status")) {

                            var jsonArray = jsonObject.getJSONArray("responseBody")

                            if (jsonArray.length() > 0) {

                                var data = JSONObject(jsonArray.getJSONObject(0).toString())

                                MyApplication.writeStringPreference(
                                    ApiContants.id,
                                    data.getString("id")
                                )
                                MyApplication.writeStringPreference(
                                    ApiContants.PREF_F_name,
                                    data.getString("name")
                                )
                                MyApplication.writeStringPreference(ApiContants.login, "true")

                                startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                                finish()

                            }
                        }
                         else {
                            Utility.showDialog(
                                this@SignInActivity,
                                SweetAlertDialog.WARNING_TYPE,
                                resources.getString(R.string.error)
                            )

                            Toast.makeText(
                                this@SignInActivity,
                                "Bad Response ! ",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@SignInActivity,
                            " " + resources.getString(R.string.error),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(
                        this@SignInActivity,
                        " " + resources.getString(R.string.error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }

        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
/*
        val widthDp = resources.displayMetrics.run { widthPixels / density }
        val heightDp = resources.displayMetrics.run { heightPixels / density }

        var width:Int= widthDp.roundToInt()
        binding.l1.setMargins(top = (widthDp+100).roundToInt())*/

/*        val params: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(this.binding.l1.getWidth(), this.binding.l1.getHeight())
        params.setMargins(left, top, right, bottom)
        this.binding.l1.setLayoutParams(params)*/

        progressDialog = SweetAlertDialog(this@SignInActivity, SweetAlertDialog.PROGRESS_TYPE)
        progressDialog!!.progressHelper.barColor = R.color.theme_color
        progressDialog!!.titleText = "Loading ..."
        progressDialog!!.setCancelable(false)

        ClickListener()
    }
}
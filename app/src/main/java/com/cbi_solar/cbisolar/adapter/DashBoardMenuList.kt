package com.cbi_solar.cbisolar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.cbi_solar.DashBoardMenuListModel
import com.cbi_solar.cbisolar.databinding.AdapterDashboardMenuBinding
import com.cbi_solar.helper.Utility

class DashBoardMenuList(var requestManager: RequestManager) :
    RecyclerView.Adapter<DashBoardMenuList.ViewHolder>() {

    lateinit var context: Context
    private var mOptionList: ArrayList<DashBoardMenuListModel> =
        java.util.ArrayList<DashBoardMenuListModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewProductCategoryBinding: AdapterDashboardMenuBinding =
            AdapterDashboardMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        context = parent.context
        return ViewHolder(viewProductCategoryBinding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = getItem(position)


        try {
            Utility.setImage(context, mOptionList.get(position).product_img, holder.binding.img)
            holder.binding.txtName.text = mOptionList.get(position).product_name
            holder.binding.txtDiscount.text = mOptionList.get(position).discount
            holder.binding.txtPrice.text = mOptionList.get(position).price
            holder.binding.txtModel.text = mOptionList.get(position).model
            holder.binding.txtMrp.text = mOptionList.get(position).mrp

            if (mOptionList.get(position).mrp == "0") {
                holder.binding.txtMrp.visibility = View.GONE
            } else {
                holder.binding.txtMrp.visibility = View.VISIBLE
            }
        } catch (e: Exception) {

        }

    }

    fun setData(mOptionList: ArrayList<DashBoardMenuListModel>) {
        this.mOptionList = mOptionList
        notifyDataSetChanged()
    }

    fun updateList(list: ArrayList<DashBoardMenuListModel>) {
        mOptionList = list
        notifyDataSetChanged()
    }


    private fun getItem(index: Int): String {
        return mOptionList[index].toString()
    }

    override fun getItemCount(): Int {
        return mOptionList.size
    }


    inner class ViewHolder(val binding: AdapterDashboardMenuBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        override fun onClick(v: View?) {
            ////
        }
    }

}
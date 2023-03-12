package com.cbi_solar.cbisolar.adapter

import android.content.Context
import android.content.Intent
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.cbi_solar.NotificationModel
import com.cbi_solar.StoreModel
import com.cbi_solar.cbisolar.R
import com.cbi_solar.cbisolar.databinding.AdapterNotificationBinding
import com.cbi_solar.cbisolar.databinding.AdapterStorelistBinding

class NotifactionAdapter(var requestManager: RequestManager) : RecyclerView.Adapter<NotifactionAdapter.ViewHolder>() {

    lateinit var context: Context
    private var mOptionList: ArrayList<NotificationModel> = java.util.ArrayList<NotificationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewProductCategoryBinding: AdapterNotificationBinding =
            AdapterNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        context=parent.context
        return ViewHolder(viewProductCategoryBinding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = getItem(position)

        holder.binding.item.setOnClickListener {
//            context.startActivity(Intent(context,ClinicDetailActivity::class.java))
        }

        holder.binding.txtName.setText(mOptionList.get(position).title)
        holder.binding.txtContact.setText(mOptionList.get(position).des)
//        holder.binding.txtAddress.setText(mOptionList.get(position).address)



    }
    fun setData(mOptionList: ArrayList<NotificationModel>) {
        this.mOptionList = mOptionList
        notifyDataSetChanged()
    }

    fun updateList(list: ArrayList<NotificationModel>) {
        mOptionList = list
        notifyDataSetChanged()
    }


    private fun getItem(index: Int): String {
        return mOptionList[index].toString()
    }

    override fun getItemCount(): Int {
        return mOptionList.size
    }


    inner class ViewHolder(val binding: AdapterNotificationBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        override fun onClick(v: View?) {
            ////
        }
    }

}
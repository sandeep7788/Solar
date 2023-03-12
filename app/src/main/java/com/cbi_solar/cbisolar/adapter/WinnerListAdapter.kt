package com.cbi_solar.cbisolar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.cbi_solar.WinnerListModel
import com.cbi_solar.cbisolar.databinding.AdapterWinnerBinding

class WinnerListAdapter(var requestManager: RequestManager) :
    RecyclerView.Adapter<WinnerListAdapter.ViewHolder>() {

    lateinit var context: Context
    private var mOptionList: ArrayList<WinnerListModel> =
        java.util.ArrayList<WinnerListModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewProductCategoryBinding: AdapterWinnerBinding =
            AdapterWinnerBinding.inflate(
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

            holder.binding.txtTitle.text = mOptionList.get(position).title
            holder.binding.txtName.text = mOptionList.get(position).winner_name
            holder.binding.txtPosition.text = mOptionList.get(position).position
            holder.binding.txtPrize.text = mOptionList.get(position).prize

        } catch (e: Exception) {

        }

    }

    fun setData(mOptionList: ArrayList<WinnerListModel>) {
        this.mOptionList = mOptionList
        notifyDataSetChanged()
    }

    fun updateList(list: ArrayList<WinnerListModel>) {
        mOptionList = list
        notifyDataSetChanged()
    }


    private fun getItem(index: Int): String {
        return mOptionList[index].toString()
    }

    override fun getItemCount(): Int {
        return mOptionList.size
    }


    inner class ViewHolder(val binding: AdapterWinnerBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        override fun onClick(v: View?) {
            ////
        }
    }

}
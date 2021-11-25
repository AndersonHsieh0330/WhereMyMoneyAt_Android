package com.AndersonHsieh.wmma_wheremymoneyat.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.AndersonHsieh.wmma_wheremymoneyat.R
import com.AndersonHsieh.wmma_wheremymoneyat.databinding.TransactionRecyclerItemsBinding
import com.AndersonHsieh.wmma_wheremymoneyat.model.Transaction

class RecyclerAdapter(private val dataSet: List<Transaction>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    inner class ViewHolder(val binding:TransactionRecyclerItemsBinding): RecyclerView.ViewHolder(binding.root) {
        //view holder is a container of the actual elements of each element in the list item
        //each item in the recyclerview gets a viewHolder


        //since we don't have that many views inside of each recyclerview item
        //storing a single view binding object can void lots of unnecessary code

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        // Create a new viewfinder, which defines the UI of the list item
        // create a container of the view elements

        var binding = TransactionRecyclerItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        //bind the data set and the views in the viewholder
        //in this case, a single view binding variable takes care of all the views

        holder.binding.testtest.text = "eeeeeee";
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}
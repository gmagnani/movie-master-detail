package com.example.moviemasterdetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemasterdetail.R
import com.example.moviemasterdetail.model.Movie

class MovieAdapter(
    private var list: List<Movie>,
    private val onClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.VH>() {

    inner class VH(item: View): RecyclerView.ViewHolder(item) {
        val tv = item.findViewById<TextView>(R.id.tvTitle)
        fun bind(m: Movie) {
            tv.text = m.title
            itemView.setOnClickListener { onClick(m) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(list[position])
    override fun getItemCount() = list.size

    fun update(newList: List<Movie>) {
        list = newList
        notifyDataSetChanged()
    }
}

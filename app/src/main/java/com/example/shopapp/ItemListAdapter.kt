package com.example.shopapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ItemLayoutBinding

class ItemListAdapter(
    private val items: List<Item>
) : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    // Опционально: колбэк при клике (рекомендуется передавать через лямбду вместо context)
    var onItemClick: ((Item) -> Unit)? = null

    // ViewHolder теперь хранит binding
    inner class ViewHolder(
        private val binding: ItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(items[position])
                }
            }
        }

        fun bind(item: Item) {
            binding.title.text = item.title
            binding.price.text = String.format("%.2f", item.price)
            // Если есть ImageView и картинка — загрузите через Glide/Coil:
            // Glide.with(binding.imageView).load(item.imageUrl).into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
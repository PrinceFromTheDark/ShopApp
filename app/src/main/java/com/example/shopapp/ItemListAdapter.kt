package com.example.shopapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ItemLayoutBinding
import com.example.shopapp.dto.GameDTO

class ItemListAdapter(
    private val sessionManager: SessionManager,
    private val items: List<GameDTO>
) : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    var onItemClick: ((GameDTO) -> Unit)? = {

    }

    var onAddToCart: ((GameDTO) -> Unit)? = { game ->
        addItemToCart(sessionManager, game)
    }

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

            binding.addToCartButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onAddToCart?.invoke(items[position])
                }
            }
        }

        fun bind(item: GameDTO) {
            binding.title.text = item.title
            binding.price.text = String.format("%.2f", item.price)

            // Загрузка изображения из base64
            val res = loadBase64Image(item.logo)
            if (res != null) binding.image.setImageBitmap(res as Bitmap)
            else binding.image.setImageResource(R.drawable.ic_launcher_foreground)
        }

//        private fun loadBase64Image(base64String: String?) {
//            if (!base64String.isNullOrEmpty()) {
//                try {
//                    // Убираем префикс data:image если есть
//                    val cleanBase64 = base64String.substringAfter(",")
//
//                    // Декодируем base64 в байтовый массив
//                    val imageBytes = Base64.decode(cleanBase64, Base64.DEFAULT)
//
//                    // Создаем Bitmap из байтового массива
//                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//
//                    // Устанавливаем изображение в ImageView
//                    binding.image.setImageBitmap(bitmap)
//
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    // Устанавливаем изображение-заглушку при ошибке
//                    binding.image.setImageResource(R.drawable.ic_launcher_foreground)
//                }
//            } else {
//                // Если изображение отсутствует, устанавливаем заглушку
//                binding.image.setImageResource(R.drawable.ic_launcher_foreground)
//            }
//        }
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
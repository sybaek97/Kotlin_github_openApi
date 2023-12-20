package com.crepass.kotlin_github_openapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.crepass.kotlin_github_openapi.databinding.ItemUserBinding
import com.crepass.kotlin_github_openapi.model.User

class UserAdapter(val onClick:(User)->Unit) : ListAdapter<User, UserAdapter.ViewHolder>(diffUtil) {


    inner class ViewHolder(private val viewBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: User) {
            viewBinding.usernameTextView.text = item.username
            viewBinding.root.setOnClickListener{
                onClick(item)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(currentList[position])
    }
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {//고유값만 같고 다른건 다 똑같을떄..?
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {//안에 있는 내용물도 같은지?
                return oldItem == newItem
            }

        }
    }




}
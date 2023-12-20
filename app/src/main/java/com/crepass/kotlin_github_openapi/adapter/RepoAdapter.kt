package com.crepass.kotlin_github_openapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.crepass.kotlin_github_openapi.databinding.ItemRepoBinding
import com.crepass.kotlin_github_openapi.model.Repo

class RepoAdapter(private val onClick:(Repo)->Unit) :ListAdapter<Repo,RepoAdapter.ViewHolder>(diffUtil){

    inner class ViewHolder(private val viewBinding:ItemRepoBinding) :
        RecyclerView.ViewHolder(viewBinding.root){

        fun bind(item: Repo) {
            viewBinding.apply {
                repoNameTextView.text=item.name
                descriptionTextView.text=item.description
                starCountTextView.text=item.starCount.toString()
                forkCountTextView.text="${item.forksCount}"
                root.setOnClickListener{
                    onClick(item)
                }
            }

        }

    }

    companion object{
        val diffUtil=object :DiffUtil.ItemCallback<Repo>(){
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
               return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem==newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])

    }

}
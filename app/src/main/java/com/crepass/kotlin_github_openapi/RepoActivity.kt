package com.crepass.kotlin_github_openapi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crepass.kotlin_github_openapi.ApiClient.retrofit
import com.crepass.kotlin_github_openapi.adapter.RepoAdapter
import com.crepass.kotlin_github_openapi.databinding.ActivityRepoBinding
import com.crepass.kotlin_github_openapi.model.Repo
import com.crepass.kotlin_github_openapi.network.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepoActivity : AppCompatActivity() {
    private lateinit var reAdapter: RepoAdapter
    private lateinit var binding: ActivityRepoBinding

    private var page = 0
    private var hasMore = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRepoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val username = intent.getStringExtra("username") ?: return
        binding.usernameTextView.text = username
        reAdapter = RepoAdapter {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.htmlUrl))
            startActivity(intent)
        }
        val linearLayoutManager = LinearLayoutManager(this@RepoActivity)
        binding.repoRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = reAdapter
        }

        binding.repoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalCount = linearLayoutManager.itemCount
                val lastVisiblePosition =
                    linearLayoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisiblePosition >= (totalCount - 1) && hasMore) {

                    page += 1
                    listRepo(username, page)
                }

            }
        })//리사이클러뷰가 스크롤 되고 있나 확인가능

        listRepo(username, 0)

    }


    private fun listRepo(username: String, page: Int) {
        val githubService = retrofit.create(GithubService::class.java)
        githubService.listRepos(username, page).enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                Log.e("MAinActivity", response.body().toString())
                hasMore = response.body()?.count() == 30
                reAdapter.submitList(reAdapter.currentList + response.body().orEmpty())

            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {

            }

        })
    }

}
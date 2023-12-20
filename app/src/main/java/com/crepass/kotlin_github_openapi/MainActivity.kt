package com.crepass.kotlin_github_openapi


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.crepass.kotlin_github_openapi.ApiClient.retrofit
import com.crepass.kotlin_github_openapi.adapter.UserAdapter
import com.crepass.kotlin_github_openapi.databinding.ActivityMainBinding
import com.crepass.kotlin_github_openapi.model.Repo
import com.crepass.kotlin_github_openapi.model.UserDto
import com.crepass.kotlin_github_openapi.network.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter


    private val handler=Handler(Looper.getMainLooper())
    private var searchFor=""

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)


//        githubService.listRepos("squar").enqueue(object :Callback<List<Repo>>{
//            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
//                Log.e("MAinActivity",response.body().toString())
//            }
//
//            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
//
//            }
//
//        })
        userAdapter = UserAdapter{
            val intent=Intent(this@MainActivity,RepoActivity::class.java)
            intent.putExtra("username",it.username)
            startActivity(intent)
        }


        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)//리사이클러 뷰에 있는 context가 들어감
            adapter = userAdapter
        }

        val runnable= Runnable {
            searchUser()
        }

        binding.searchEditText.addTextChangedListener{

            searchFor=it.toString()
            handler.removeCallbacks(runnable)//이전 검색어를 지우고
            handler.postDelayed(//3초이내에 검색어가 없으면 검색을 함
                runnable,
                300,

            )
        }


    }

    private fun searchUser() {
        val githubService = retrofit.create(GithubService::class.java)
        githubService.searchUsers(searchFor).enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                Log.e("MAinActivity", "SearchUser" + response.body().toString())
                userAdapter.submitList(response.body()?.items)
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                Toast.makeText(this@MainActivity,"에러가 발생했습니다",Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }

        })
    }

}
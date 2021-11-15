package com.example.githubuserrview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.githubuserrview.databinding.ActivitySearchBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySearchBinding
    //private lateinit var rvRetro: RecyclerView

    companion object {
        private const val TAG = "SearchActivity"
        private const val EXTRA_LOGIN = "login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val floatingActionButton: FloatingActionButton = findViewById(R.id.fab2)
        floatingActionButton.setOnClickListener(this)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRetro.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerViewRetro.addItemDecoration(itemDecoration)

        val login = intent.getStringExtra(EXTRA_LOGIN)

        //rvRetro = findViewById(R.id.recycler_view_retro)
        //rvRetro.setHasFixedSize(true)

        findUser()
    }

    //untuk mengeksekusi request secara asynchronous dg function "findUSer()" :
    private fun findUser() {
        showLoading(true)
            val client = ApiConfig.getApiService().getSearchResult(EXTRA_LOGIN)
            client.enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            val listResult = response.body()?.items as List<ItemsItem>
                            setResultData(responseBody.items)
                        }
                    }else{
                        Log.d(TAG, "onFailure: ${response.message()}")
                    }
                }

                //terpanggil ketika tidak dapat menghubungi server
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    showLoading(false)
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fab2 -> {
                val moveIntent = Intent(this@SearchActivity, ResultActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }

    private fun setResultData(itemku: List<ItemsItem>){
        Glide.with(this@SearchActivity)
            .load(itemku.map { it.avatarUrl })
            .into(findViewById(R.id.iv_photo_retro))

        val listResult = ArrayList<String>()
        for (item in  itemku) {
            listResult.add(item.login)
        }
        val adapter = Adapter2(listResult)
        binding.recyclerViewRetro.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}
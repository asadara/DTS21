package com.example.githubuserrview

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserrview.DetailUserGithubActivity.Companion.EXTRA_PACKAGE
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var recyclerView: RecyclerView
    private val list = ArrayList<Package>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GitHubUserRview)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = getString(R.string.bar_title_info)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)

        list.addAll(listGitHub)
        showListGh()

        val floatingActionButton: FloatingActionButton = findViewById(R.id.fab)
        floatingActionButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.fab -> {
                val moveIntent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }

    private val listGitHub: ArrayList<Package>
        @SuppressLint("Recycle")
        get() {
            val dataId = resources.getStringArray(R.array.username)
            val dataNama = resources.getStringArray(R.array.surename)
            val dataFoto = resources.obtainTypedArray(R.array.avatar)
            val dataLokasi = resources.getStringArray(R.array.location)
            val dataRepository = resources.getIntArray(R.array.repository)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataFollowers = resources.getIntArray(R.array.followers)
            val dataFollowing = resources.getIntArray(R.array.following)
            val dataDeskripsi = resources.getStringArray(R.array.description)

            val listgh = ArrayList<Package>()

            for (i in dataId.indices) {
                val gh = Package(
                    dataId[i],
                    dataNama[i],
                    dataFoto.getResourceId(i, -1),
                    dataLokasi[i],
                    dataRepository[i],
                    dataCompany[i],
                    dataFollowers[i],
                    dataFollowing[i],
                    dataDeskripsi[i]
                )
                listgh.add(gh)
            }
            return listgh
        }

    private fun showSelectedItem(gh: Package) {
        val intentDetail = Intent(this, DetailUserGithubActivity::class.java)
        intentDetail.putExtra(EXTRA_PACKAGE, gh)
        startActivity(intentDetail)
    }

    private fun showListGh() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager = GridLayoutManager(this, 2)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        val listPackageAdapter = Adapter1(list)
        recyclerView.adapter = listPackageAdapter

        listPackageAdapter.setOnItemClickCallback(object : Adapter1.OnItemClickCallback {
            override fun onItemClicked(data: Package) {
                showSelectedItem(data)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflaterSearch = menuInflater
        inflaterSearch.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                val moveIntent = Intent(this@MainActivity, SearchActivity::class.java)
                val login = "EXTRA_LOGIN"
                moveIntent.putExtra(login,query.toString())
                startActivity(moveIntent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }
}
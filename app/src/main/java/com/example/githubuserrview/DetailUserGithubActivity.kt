package com.example.githubuserrview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserrview.databinding.ActivityDetailGhBinding

class DetailUserGithubActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGhBinding

    companion object {
        const val EXTRA_PACKAGE = "extra_package"
    }

    @SuppressLint("Recycle", "ResourceType", "SetWorldReadable", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGhBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.bar_title_detail)

        val listPackage = intent.getParcelableExtra<Package>(EXTRA_PACKAGE) as Package

        binding.ivDetailPhoto.setImageResource(listPackage.photo)
        binding.tvDetailId.text = listPackage.username
        binding.tvDetailName.text = "Nama Asli : " + listPackage.surename
        binding.tvDetailProfile.text = """
            Lokasi : ${listPackage.location}
            Jumlah Karya : ${listPackage.repository} repositories
            Bekerja di Perusahaan : ${listPackage.company}
            Jumlah Followers : ${listPackage.followers}
            Following : ${listPackage.following}
            
            Sekilas tentang ${listPackage.surename} :
        """.trimIndent()
        binding.tvDetailDescription.text = listPackage.description


        val sharePackage = Intent()
        val contentShare = """
            Id Github User : ${listPackage.username}
            Nama User : ${listPackage.username}
            Lokasi : ${listPackage.location}
            Jumlah Karya : ${listPackage.repository} repositories
            Bekerja di Perusahaan : ${listPackage.company}
            Jumlah Followers : ${listPackage.followers}
            Mengikuti : ${listPackage.following}
            Sekilas tentang : ${listPackage.description}
        """.trimIndent()
        sharePackage.type = "text/plain"
        sharePackage.putExtra(Intent.EXTRA_TEXT, contentShare)
        sharePackage.action = Intent.ACTION_SEND
        sharePackage.putExtra(Intent.EXTRA_SUBJECT, "judul kirim")

        binding.btnShare.setOnClickListener {
            startActivity(Intent.createChooser(sharePackage, "Share Content(s) Via"))
        }

        binding.btnLike.setOnClickListener {
            Toast.makeText(this, "Like it, ${listPackage.surename}", Toast.LENGTH_SHORT).show()
        }
    }
}


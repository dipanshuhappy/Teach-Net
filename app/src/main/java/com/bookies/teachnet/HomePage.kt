package com.bookies.teachnet

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomePage : AppCompatActivity() {
    private lateinit var drawer:DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val teacherListView=findViewById<ListView>(R.id.teacher_listview)
        val teacherListAdapter=TeacherListAdapter(getTeacherInfo(),this)
        teacherListView.adapter=teacherListAdapter
        teacherListView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val toPostPageIntent=Intent(this@HomePage,TeacherPostPage::class.java)
                startActivity((toPostPageIntent))
            }
        initialiseNavBar()
        handleNavEvents()
    }
    private fun initialiseNavBar(){
        val toolbar=findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawer= findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_nav, R.string.close_nav)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }
    private fun handleNavEvents(){
        val navigationView=findViewById<NavigationView>(R.id.nav_view)

        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.rate_app -> {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_SEND,
                                Uri.parse("market://details?id=$packageName")
                            )
                        )
                    } catch (E: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                            )
                        )
                    }
                    item.isChecked = false
                    return@OnNavigationItemSelectedListener false
                }
                R.id.share_app -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    val shareSubject =
                        "DOWNLOAD SCIGUIDE A SCIENCE STUDENT BEST GUIDE"
                    val shareBody =
                        """
                        THIS APP HAS PAST QUESTION,FORMULAS,NOTES AND A SPECIALISED CALCULATOR CLICK ON THE LINK BELOW TO DOWNLOAD
                        https://play.google.com/store/apps/details?id=$packageName
                        """.trimIndent()
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    startActivity(Intent.createChooser(shareIntent, "Share using "))
                    return@OnNavigationItemSelectedListener false
                }
                R.id.bug -> {
                    drawer.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                        GravityCompat.START
                    )
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_nav, BlankFragment())
                        .addToBackStack(null).commit()
                }
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        })

    }
    private fun getTeacherInfo():List<TeacherProp>{
        val database:FirebaseDatabase = FirebaseDatabase.getInstance()
        var ref=database.reference.child("TeacherList")
        ref.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val value:String= snapshot.value as String
                Log.d("RECEIVED TEACHER LIST", "json is $value")

            }

        })
        val jsonInfo:String="[\t\n" +
                "\t{\t\n" +
                "\t\t\t\"name\":\"Batholome Orgh\",\n" +
                "\t\t\t\"subject\":\"physics\",\n" +
                "\t\t\t\"displayPic\":\"soon\",\n" +
                "\t\t\t\"notification\":\"1\"\n" +
                "\t},\n" +
                "\t{\t\n" +
                "\t\t\t\"name\":\"Sracth Underpants\",\n" +
                "\t\t\t\"subject\":\"Economics\",\n" +
                "\t\t\t\"displayPic\":\"soon\",\n" +
                "\t\t\t\"notification\":\"2\"\n" +
                "\t}\n" +
                "]\n" +
                "\n"
        val mapper= ObjectMapper().registerModule(KotlinModule())
        var teacherList: List<TeacherProp> = mapper.readValue(jsonInfo)
        teacherList.forEach {
            println(it.name)
        }
        return teacherList


    }
}

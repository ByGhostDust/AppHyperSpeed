package com.ghostdust.apphyperspeed.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ghostdust.apphyperspeed.R
import com.ghostdust.apphyperspeed.adapters.RolesAdapter
import com.ghostdust.apphyperspeed.modelos.Usuario
import com.ghostdust.apphyperspeed.utils.Sharepref
import com.google.gson.Gson

class SelectRolesActivity : AppCompatActivity() {
    var recyclerViewRoles: RecyclerView? = null
    var user: Usuario? = null
    var adapater: RolesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_roles)

        recyclerViewRoles = findViewById(R.id.recyclearview_roles)
        recyclerViewRoles?.layoutManager = LinearLayoutManager(this)
        getUsuariofromSession()

        adapater = RolesAdapter(this, user?.roles!!)
        recyclerViewRoles?.adapter = adapater
    }
    private fun getUsuariofromSession() {
        val sharepref = Sharepref(this)
        val gson = Gson()
        if (!sharepref.getData("usuario").isNullOrBlank()) {
            // Si el usuario existe en la sesión, asigna los datos al miembro user
            user = gson.fromJson(sharepref.getData("usuario"), Usuario::class.java)
        }
    }
}
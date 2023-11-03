package com.ghostdust.apphyperspeed.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ghostdust.apphyperspeed.R
import com.ghostdust.apphyperspeed.activitys.administrador.administrador_homeActivity
import com.ghostdust.apphyperspeed.activitys.cliente.home.ClientehomeActivity
import com.ghostdust.apphyperspeed.activitys.repartidor.Repartidor_homeActivity
import com.ghostdust.apphyperspeed.utils.Sharepref
import com.ghostdust.apphyperspeed.modelos.Rol

class RolesAdapter(private val context: Activity, private val roles: ArrayList<Rol>) : RecyclerView.Adapter<RolesAdapter.RolesViewHolder>() {

    private val sharepref = Sharepref(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RolesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_roles, parent, false)
        return RolesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return roles.size
    }

    override fun onBindViewHolder(holder: RolesViewHolder, position: Int) {
        val rol = roles[position]
        holder.textViewRol.text = rol.nombre
        Glide.with(context).load(rol.imagen).into(holder.imageViewRol)

        holder.itemView.setOnClickListener { gotoRol(rol) }
    }

    private fun gotoRol(rol: Rol) {
        val intent = when (rol.nombre) {
            "ADMINISTRADOR" -> {
                sharepref.save("rol", "ADMINISTRADOR")
                Intent(context, administrador_homeActivity::class.java)
            }
            "USUARIO" -> {
                sharepref.save("rol", "USUARIO")
                Intent(context, ClientehomeActivity::class.java)
            }
            "REPARTIDOR" -> {
                sharepref.save("rol", "REPARTIDOR")
                Intent(context, Repartidor_homeActivity::class.java)
            }
            else -> null
        }

        intent?.let {
            context.startActivity(it)
        }
    }

    class RolesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewRol: TextView = view.findViewById(R.id.text_rol)
        val imageViewRol: ImageView = view.findViewById(R.id.imagenv_rol)
    }
}
package com.example.famsafe
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class memberAdapter(private val listMembers: List<MemberModel>) : RecyclerView.Adapter<memberAdapter.ViewHolder>() {
    class ViewHolder(val item:  View) : RecyclerView.ViewHolder(item) {
        val img_user=item.findViewById<ImageView>(R.id.profile)
        val name_user=item.findViewById<TextView>(R.id.name_user)
        val address=item.findViewById<TextView>(R.id.add)
        val battery=item.findViewById<TextView>(R.id.battery_text)
        val distance = item.findViewById<TextView>(R.id.distance_text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): memberAdapter.ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val item=inflater.inflate(R.layout.item_member,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: memberAdapter.ViewHolder, position: Int) {
        val item=listMembers[position]
        holder.name_user.text=item.name
        holder.address.text=item.address
        holder.battery.text=item.battery
        holder.distance.text=item.distance


    }

    override fun getItemCount(): Int {
        return listMembers.size
    }

}
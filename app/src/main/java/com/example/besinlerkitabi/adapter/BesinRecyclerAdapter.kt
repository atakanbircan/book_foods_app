package com.example.besinlerkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.besinlerkitabi.R
import com.example.besinlerkitabi.model.Besin
import com.example.besinlerkitabi.util.benimEklentim
import com.example.besinlerkitabi.util.gorselIndir
import com.example.besinlerkitabi.util.placeHolderYap
import com.example.besinlerkitabi.view.BesinListesiFragmentDirections


class BesinRecyclerAdapter(val besinListesi:ArrayList<Besin>):
    RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>() {

    class BesinViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val isim:TextView=itemView.findViewById(R.id.isim)
        val kalori:TextView=itemView.findViewById(R.id.kalori)
        var imageView:ImageView=itemView.findViewById(R.id.imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view= inflater.inflate(R.layout.besin_recycler_row,parent,false)
        return BesinViewHolder(view)
        //recyclerrow ile bağlantı sağlanır.
    }

    override fun getItemCount(): Int {
      return besinListesi.size
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.isim.text=besinListesi.get(position).besinIsim
        holder.kalori.text=besinListesi.get(position).besinKalori

       // holder.itemView.imageView=besinListesi.get(position).besinGorsel

        //görsel eklenecek

        holder.itemView.setOnClickListener{
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(besinListesi.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.imageView.gorselIndir(besinListesi.get(position).besinGorsel,
            placeHolderYap(holder.itemView.context)
        )

    }

    fun besinListesiniGuncelle(yeniBesinListesi: List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }
}
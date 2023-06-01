package com.example.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.besinlerkitabi.adapter.BesinRecyclerAdapter
import com.example.besinlerkitabi.databinding.FragmentBesinListesiBinding
import com.example.besinlerkitabi.viewmodel.BesinListesiViewModel

//import kotlinx.android.synthetic.main.fragment_besin_detayi.*
//import kotlinx.android.synthetic.main.fragment_besin_listesi.*


class BesinListesiFragment : Fragment() {

    private lateinit var viewModel:BesinListesiViewModel
    private val recyclerBesinAdapter=BesinRecyclerAdapter(arrayListOf())

    private lateinit var binding:FragmentBesinListesiBinding
    private lateinit var besinListesiButton:Button
    lateinit var besinListesiRecycler:RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var besinYukleniyor:ProgressBar
    private lateinit var besinHataMesaji:TextView



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentBesinListesiBinding.inflate(inflater,container,false)
        val view =binding.root
        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(BesinListesiViewModel::class.java)//view model initilaze ettik
        viewModel.refreshData()


        besinListesiRecycler=binding.besinListRecycler
        besinListesiRecycler.layoutManager= LinearLayoutManager(context)
        besinListesiRecycler.adapter=recyclerBesinAdapter
        besinYukleniyor=binding.besinYukleniyor
        besinHataMesaji=binding.besinHataMesaji
        swipeRefreshLayout=binding.swipeRefreshLayout


        swipeRefreshLayout.setOnRefreshListener {
            besinYukleniyor.visibility=View.VISIBLE
            besinHataMesaji.visibility = View.GONE
            besinListesiRecycler.visibility= View.GONE
            viewModel.refreshFromInternet()
            swipeRefreshLayout.isRefreshing=false
        }



        observeLiveData()

    }

    fun observeLiveData(){

        viewModel.besinler.observe(viewLifecycleOwner, Observer { besinler ->
            besinler?.let {

                besinListesiRecycler.visibility = View.VISIBLE
                recyclerBesinAdapter.besinListesiniGuncelle(besinler)
            }
        })

        viewModel.besinHataMesaji.observe(viewLifecycleOwner,Observer{ hata->
            hata?.let {
                if (it){
                    binding.besinListRecycler.visibility= View.GONE
                    binding.besinHataMesaji.visibility= View.VISIBLE
                }else{
                    binding.besinHataMesaji.visibility=View.GONE
                }
            }
        })
        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { yukleniyor ->
            yukleniyor?.let {
                if (it){
                    besinListesiRecycler.visibility = View.GONE
                    binding.besinHataMesaji.visibility = View.GONE
                    binding.besinYukleniyor.visibility = View.VISIBLE
                }else{
                    binding.besinYukleniyor.visibility = View.GONE

                }
            }
        })
    }


}
package com.example.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.besinlerkitabi.databinding.FragmentBesinDetayiBinding
import com.example.besinlerkitabi.model.Besin
import com.example.besinlerkitabi.util.gorselIndir
import com.example.besinlerkitabi.util.placeHolderYap
import com.example.besinlerkitabi.viewmodel.BesinDetayiViewModel


class BesinDetayiFragment : Fragment() {
    private lateinit var binding: FragmentBesinDetayiBinding
    private lateinit var besin_detayi_button:Button

    private lateinit var viewModel :BesinDetayiViewModel

    private var besinId = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBesinDetayiBinding.inflate(inflater,container,false)

        //val bundle:BesinDetayiFragmentArgs by navArgs()// argları almak için yarattık

        //Log.e("BESINDETAYIARGUMENT", bundle.besinId.toString())
        val view = binding.root
       /* binding.besinDetayiButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_besinDetayiFragment_to_besinListesiFragment)

        }*/
        return view


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            besinId= com.example.besinlerkitabi.view.BesinDetayiFragmentArgs.fromBundle(it).besinId

        }

        viewModel = ViewModelProviders.of(this).get(BesinDetayiViewModel::class.java)
        viewModel.roomVerisiniAl(besinId)



        observeLiveData()

    }

    fun observeLiveData(){

        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer {besin->
            besin?.let {

                binding.besinIsim.text = it.besinIsim
                binding.besinKalori.text = it.besinKalori
                binding.besinKarbonhidrat.text = it.besinKarbonhidrat
                binding.besinProtein.text = it.besinProtein
                binding.besinYag.text = it.besinYag

                context?.let {
                    binding.besinImage.gorselIndir(besin.besinGorsel, placeHolderYap(it.applicationContext))
                }

            }
        })
    }
}
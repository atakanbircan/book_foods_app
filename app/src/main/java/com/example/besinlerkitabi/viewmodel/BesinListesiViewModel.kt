package com.example.besinlerkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.besinlerkitabi.model.Besin
import com.example.besinlerkitabi.servis.BesinAPIServis
import com.example.besinlerkitabi.servis.BesinDatabase
import com.example.besinlerkitabi.util.SpecSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application): BaseViewModel(application) {

    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()
    private var updatingTimePeriod = 10*60*1000*1000*1000L   //en baştaki dakika cinsinden vermek istediğimiz değer ve çok küçük olan nano saliseye dödüdrdük çarparak.

    private val besinApiServis = BesinAPIServis()
    private  val disposable = CompositeDisposable()
    private val specSharedPreferences=SpecSharedPreferences(getApplication())

    fun refreshData(){

        val recordedTime=specSharedPreferences.getTime()
        if (recordedTime != null && recordedTime!=0L && System.nanoTime() - recordedTime < updatingTimePeriod){
            //SQLite dan alır çünkü updatingTimePeriod
            verileriSQLitanAl()
        }else{
            verileriInternettenAl()
        }
    }
    fun refreshFromInternet(){
        verileriInternettenAl()
    }

    private fun verileriSQLitanAl(){
        launch {
            val besinList =BesinDatabase(getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinList)
        }
    }
    private fun verileriInternettenAl(){
        besinYukleniyor.value=true


        //IO, Default,UI thats some of threads types
        disposable.add(
            besinApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){
                    override fun onSuccess(t: List<Besin>) {
                        //başarılı olursa
                        sqliteSakla(t)
                    }

                    override fun onError(e: Throwable) {
                        //hata alırsa
                        besinHataMesaji.value=true
                        besinYukleniyor.value=false
                       // e.printStackTrace()
                    }

                })
        )
    }

    private fun besinleriGoster(besinlerListesi :List<Besin>){
        besinler.value=besinlerListesi
        besinHataMesaji.value=false
        besinYukleniyor.value=false
    }

    private fun sqliteSakla(besinListesi:List<Besin>){
        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidListesi = dao.insertAll(*besinListesi.toTypedArray()) // (toTypedArray() ile)gelen list verisini direk yazmadık tek tek versin istedik ondan.
            var i =0
            while (i<besinListesi.size){
                besinListesi[i].uuid = uuidListesi[i].toInt()
                i=i+1
            }
            besinleriGoster(besinListesi)
        }

        specSharedPreferences.timerecord(System.nanoTime())
    }

}

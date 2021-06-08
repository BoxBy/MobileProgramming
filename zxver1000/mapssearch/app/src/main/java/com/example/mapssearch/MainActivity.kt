package com.example.mapssearch

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.mapssearch.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.lang.Exception
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var googleMap: GoogleMap
    lateinit var loc:LatLng
    val z = 6372.8 * 1000
    val arrloc=ArrayList<LatLng>()
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback : LocationCallback
    lateinit var geocoder :Geocoder
    var addresslist=ArrayList<String>()

    var alpa=10.0
    var MM="1"
    var num=0
    var MartList:ArrayList<Mart> =  ArrayList()
    var beta=10.0
    var flag=true
    var startupdate=false
    var locationManager:LocationManager?=null
  lateinit var mapFragment:SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initmap()
     //  initSpinner()
        initmart()
       inita()
    }

    fun readfilescan(scan: Scanner) {
        val tag=scan.nextLine()
        var split=tag.split("}")
        var i=0
        for(a in split)
        {
            if(a.contains("]"))
                break

            if(a.contains("마트"))
            {
                var latitude="-1"
                var longtitude="-1"
                var location="-1"
                var b=a.split("\"SIGUN_NM\":\"")
                var c=b[1].split(",")
                var g=  c[0].replace("\"","")
                var city=g
                var h=c[2].split("MARKET_NM\":")
                var m=h[1].replace("\"","")
                var name=m
                var z=c[5].split("REFINE_ROADNM_ADDR\":")
                if(z.size==1)
                {
                    continue
                }
                if(z.size>1) {
                    var  alb = z[1].replace("\"", "")
                    location=alb
                }

                var nm=c[7].split("REFINE_WGS84_LAT\":")
                if(nm.size>1) {
                    MM = nm[1].replace("\"", "")
                    latitude=MM
                    if(latitude=="")
                    {
                        continue
                    }
                }

                var gamma = c[8].split("REFINE_WGS84_LOGT\":")

                if(gamma.size>1) {
                    var mg = gamma[1].replace("\"", "")
                    longtitude = mg
                }
                num++
                MartList.add(Mart(num,city,name,location,latitude,longtitude))
               // val bbeta=Mart(num,city,name,location,latitude,longtitude)
               //firebase //rdb.child("${num}").setValue(bbeta)
            }
        }

        scan.close()


    }
    fun initmart()
    {
        val scan= Scanner(resources.openRawResource(R.raw.words))
        readfilescan(scan)

    }


    fun icon(distance:Int,a:Mart){

            val option = MarkerOptions()
            option.position(LatLng(a.latitude.toDouble(),a.lontitude.toDouble()))
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            if(distance>=1000) {
                var beta=(distance.toDouble()/1000)
               option.title(a.name+ "   "+"총거리 : ${beta}km   " )
            }
        else
            {
                option.title(a.name + "   " + "총거리 : ${distance} m")
            }
        option.snippet(a.location)
            val mk1 = googleMap.addMarker(option)
            mk1.showInfoWindow()
        arrloc.add(LatLng(a.latitude.toDouble(),a.lontitude.toDouble()))
            val ggg=PolylineOptions().color(Color.GREEN).addAll(arrloc)
        val ggg1=PolylineOptions().color(Color.GREEN).add(LatLng(a.latitude.toDouble(),a.lontitude.toDouble()))

            googleMap.addPolyline(ggg)
           // initmapListener()

    }

    fun inita()
    {

     locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        geocoder= Geocoder(this,Locale.KOREA)
        binding.current.setOnClickListener {


        }

       binding.button.setOnClickListener {
           arrloc.clear()
           googleMap.clear()
         val sear=binding.edit.text.toString()
           try {
               var address = geocoder.getFromLocationName(sear, 1)
              while(address.size==0)
              {
                  address=geocoder.getFromLocationName(sear, 1)
              }
              if(address.size>0) {
                  alpa = address.get(0).latitude
                  beta = address.get(0).longitude
                  val ramma = LatLng(alpa, beta)
                 // loc = LatLng(alpa, beta)
                  loc = LatLng( alpa, beta)
                  arrloc.add( LatLng( alpa, beta))
                  setCurrentLocation(loc)
              }
              }catch(e:Exception)
           {
            Log.i("ㅇ","주소변환실패")
           }
           var minlat=10000
           var maxlong=100

           for (a in MartList)
           {
              val distances= getdistance(alpa,beta,a.latitude.toDouble(),a.lontitude.toDouble())
               if(distances<=2000)
               //전방 2KM이내 검색해주기
               {
                   if(a.latitude=="")
                   {
                       try {
                           var address = geocoder.getFromLocationName(a.location, 1)
                           while(address.size==0)
                           {
                               address=geocoder.getFromLocationName(a.location, 1)
                           }
                           if(address.size>0) {
                               alpa = address.get(0).latitude
                               beta = address.get(0).longitude
                               //   val ramma = LatLng(alpa, beta)
                               // loc = LatLng(alpa, beta)
                               a.latitude=alpa.toString()
                               a.lontitude=beta.toString()

                           }
                       }catch(e:Exception)
                       {
                           Log.i("ㅇ","주소변환실패")
                       }

                   }

                   icon(distances,a)

               }
           }



       }

    }
    fun initlocation()
    {
      fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        locationRequest=LocationRequest.create().apply{
            interval=1000000
            fastestInterval=50000
            priority=LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationCallback=object:LocationCallback()
        {
            override fun onLocationResult(location: LocationResult) {
               if(location.locations.size==0) return

           //     loc= LatLng(location.locations[location.locations.size-1].latitude,
             //   location.locations[location.locations.size-1].longitude)
               // setCurrentLocation(loc)
                 Log.i("location","Locationcallback()")

            }

        }


    }

    private fun setCurrentLocation(location: LatLng) {
       val option=MarkerOptions()
        option.position(loc)
        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        option.title("현재 위치")

        googleMap.addMarker(option)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,16.0f))

    }



    fun initSpinner()
    {
        val adapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item
        ,ArrayList<String>())
        adapter.add("Hybrid")
        adapter.add("Normal")
        adapter.add("Satellite")
        adapter.add("Terrian")
        binding.apply{
            spinner.adapter=adapter
            spinner.setSelection(1)//초기는1번으로함
            spinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    //아이템선택될때
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                 when(position){
                     0->{
                         googleMap.mapType=GoogleMap.MAP_TYPE_HYBRID
                     }
                     1->{

                         googleMap.mapType=GoogleMap.MAP_TYPE_NORMAL
                     }
                     2->{
                         googleMap.mapType=GoogleMap.MAP_TYPE_SATELLITE
                     }
                     3->{
                         googleMap.mapType=GoogleMap.MAP_TYPE_TERRAIN
                     }
                 }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }
    fun stoplocationupdate()
    {

        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        startupdate=false

    }

    override fun onResume() {
        super.onResume()
        if(!startupdate)
            startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        startLocationUpdates()
    }

    fun getlastlocation(){
        if(ActivityCompat.checkSelfPermission(
                        this, android.Manifest.permission.ACCESS_FINE_LOCATION
        )!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                    this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,

            )  ,100

            )
        }
        else {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                    if (it != null) {
                        loc = LatLng(it.latitude, it.longitude)
                        setCurrentLocation(loc)

                }

            }
        }
    }
     fun startLocationUpdates(){
         if(ActivityCompat.checkSelfPermission(
                         this, android.Manifest.permission.ACCESS_FINE_LOCATION
                 )!=PackageManager.PERMISSION_GRANTED)
         {
         ActivityCompat.requestPermissions(
                 this,
                 arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,),100
         )
         }
         else{
             if(!checkLocationservices())//gps가지금켜져있나?
             {
                 showlocationservicesetting()
             }
             else{
                 startupdate=true
                 fusedLocationProviderClient.requestLocationUpdates(
                         locationRequest,locationCallback,Looper.getMainLooper()
                 )

                 Log.i("location","")
                 fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                     if (it != null) {
                         loc = LatLng(it.latitude, it.longitude)
                         setCurrentLocation(loc)
                     }
                 }
             }
         }
     }

    private fun showlocationservicesetting() {
        val builder= AlertDialog.Builder(this)
        builder.setTitle("위치서비스비활성화")
        builder.setMessage(
                "앱사용하기위해 위치서비ㅡ필요"
        )
        builder.setPositiveButton("설정",DialogInterface.OnClickListener{
            dialog,id->
            val GpsSetting= Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivityForResult(GpsSetting,1000)
        })
        builder.setNegativeButton("취소"
        ,DialogInterface.OnClickListener{
            dialog,id->
            dialog.dismiss()
            setCurrentLocation(loc)
        })
        builder.create().show()
    }

    private fun checkLocationservices(): Boolean {
           val locationManager=getSystemService(LOCATION_SERVICE) as LocationManager
        return(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==100)
        {
          if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
          {

              startLocationUpdates()
             // getlastlocation()

          }
            else
          {
              setCurrentLocation(loc)
          }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)
        {
            1000->{
                if(checkLocationservices())
                {
                    Toast.makeText(this,"활성화됨",Toast.LENGTH_SHORT).show()
                    startLocationUpdates()
                }
                }

        }
    }

    private fun initmap()
    {
       initlocation()
        mapFragment=supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync{
            googleMap=it //구글맵초기화
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,16.0f))
//           googleMap.setMinZoomPreference(10.0f)//가장작은줌레벨설정
//            googleMap.setMaxZoomPreference(18.0f)
            /*
            for(a in MartList) {
                val option = MarkerOptions()
                if(a.latitude=="")
                {
                    try {
                        var address = geocoder.getFromLocationName(a.location, 1)
                        while(address.size==0)
                        {
                            address=geocoder.getFromLocationName(a.location, 1)
                        }
                        if(address.size>0) {
                            alpa = address.get(0).latitude
                            beta = address.get(0).longitude
                         //   val ramma = LatLng(alpa, beta)
                            // loc = LatLng(alpa, beta)
                          a.latitude=alpa.toString()
                            a.lontitude=beta.toString()

                        }
                    }catch(e:Exception)
                    {
                        Log.i("ㅇ","주소변환실패")
                    }

                }
                option.position(LatLng(a.latitude.toDouble(),a.lontitude.toDouble()))
                option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                option.title(a.name)
                option.snippet(a.location)

                val mk1 = googleMap.addMarker(option)
                mk1.showInfoWindow()
                initmapListener()
            }
*/

        }
        //메인 함수가 초기화했으니 this
    }

    private fun initmapListener() {
        googleMap.setOnMapClickListener {
            arrloc.add(it)
           // googleMap.clear()//그린거다지움
            val option=MarkerOptions()
            option.position(it)
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            googleMap.addMarker(option)

         //   val option2=PolylineOptions().color(Color.GREEN).addAll(arrloc)
         //   googleMap.addPolyline(option2)

            val option2=PolygonOptions().fillColor(Color.argb(100,255,255,0))
                .strokeColor(Color.GREEN).addAll(arrloc)
               googleMap.addPolygon(option2)
        }
    }


    fun getdistance(lat1:Double,lon1:Double,lat2:Double,lon2:Double):Int
    {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2).pow(2.0) + Math.sin(dLon / 2).pow(2.0) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
        val c = 2 * Math.asin(Math.sqrt(a))
        return (z * c).toInt()
    }


}
package com.ips.sx.ipark;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.ips.sx.ipark.tools.ActivityManager;
import com.ips.sx.ipark.tools.MyApplication;
import com.ips.sx.ipark.tools.ParkOpeart;
import com.ips.sx.ipark.view.BottomDialog;
import com.ips.sx.ipark.view.SettingsActivity;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


public class MainActivity extends ActivityManager {
    //view
    private MapView mapview = null; //地图控件
    private FloatingActionButton  gotoMylocationButton = null ; //回到我的位置
    //用户设置
    private ImageView user = null;
    //
    private  int   value  = 0;

    //地图操作与定位
    private BaiduMap baiduMap = null; //地图
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    //当前的经纬度
    private  LatLng  myLatlng  =  null;
    //是否第一次定位成功
    private  int isFirstLocation = 0;

    private  Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        button = new Button(MainActivity.this);

        initView();  //初始化界面
        initMap(); //初始化地图
        getLocation(); //获取位置
        listener();

    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void Oreceive(ParkOpeart opeart){
        if (opeart.getOperat()==1){
            MyApplication.MyToast(opeart.getDate());
            button.setText(opeart.getDate());
        }

    }


    //设置监听事件
    private void listener() {
        gotoMylocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToMtLocation(baiduMap,myLatlng,17.0f);
                MyApplication.MyToast("已回到当前位置！");

                LatLng latLng = new LatLng(myLatlng.latitude+0.001,myLatlng.longitude);
          //      OverlayOptions text = new TextOptions().bgColor(Color.GRAY).text("0/1").fontSize(25).position(latLng);
         //       baiduMap.addOverlay(text);

                //创建InfoWindow展示的view

                button.setBackgroundResource(R.drawable.jiantou_xml);
                button.setHeight(20);
                button.setWidth(20);
           //     button.setText("0/1");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BottomDialog dialog = new BottomDialog(MainActivity.this,R.style.MyAnimDialog);
                        dialog.show();
                    }
                });





                //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                InfoWindow mInfoWindow = new InfoWindow(button, latLng, value);

                //显示InfoWindow
                baiduMap.showInfoWindow(mInfoWindow);
                baiduMap.showMapPoi(true);

            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

    }

    //初始化地图
    private void initMap(){
        baiduMap = mapview.getMap(); //获取地图
      //  baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);



        mLocationClient = new LocationClient(MyApplication.getContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数

        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
//可选，设置返回经纬度坐标类型，默认gcj02
//gcj02：国测局坐标；
//bd09ll：百度经纬度坐标；
//bd09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(1000);
//可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
//可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5*60*1000);
//可选，7.2版本新增能力
//如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setIsNeedLocationDescribe(true);
//可选，是否需要位置描述信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的位置信息，此处必须为true

        option.setIsNeedAddress(true);

        mLocationClient.setLocOption(option);
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明

        option.setIsNeedLocationPoiList(true);
//可选，是否需要周边POI信息，默认为不需要，即参数为false
//如果开发者需要获得周边POI信息，此处必须为true

        mLocationClient.start();





    }

    //初始化界面
    private void initView() {
        //去除原来的标题栏
        setStatueBar(ActivityManager.STATUEBAR_NOACTIONBAR);
        setContentView(R.layout.activity_main);
        //获取地图控件
        mapview = findViewById(R.id.activity_main_mapview);
        gotoMylocationButton = findViewById( R.id.activity_main_floatbutton);
        user=findViewById(R.id.activity_main_user);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }

    public void getLocation() {


    }

    //定位成功回调接口
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

            //如果是第一次定位成功，直接跳到当前位置
            if(isFirstLocation<=3){
                moveToMtLocation(baiduMap,latitude,longitude,17.0f);
                isFirstLocation++;
            }


            List<Poi> poiList = location.getPoiList();
            //获取周边POI信息
            //POI信息包括POI ID、名称等，具体信息请参照类参考中POI类的相关说明

            myLatlng = new LatLng(location.getLatitude(),location.getLongitude());

//            MyApplication.MyToast("位置"+location.getLocationDescribe());
//            MyApplication.PrintLog("----------------","位置"+location.getLocationDescribe(),MyApplication.LOG_LEVEL_DEBUUG);
//            for(Poi p:poiList){
//                MyApplication.PrintLog("----------------","poi"+p.getName(),MyApplication.LOG_LEVEL_DEBUUG);
//            }


        }

    }

    //移动到当前的位置
    private void moveToMtLocation(BaiduMap baiduMap,double v, double v1, float rank) {
        LatLng latLng = new LatLng(v,v1);     // 建立经纬度
        MapStatusUpdate updat=MapStatusUpdateFactory.newLatLng(latLng);   //更新经纬度
        baiduMap.animateMapStatus(updat);    //更新位置
        updat = MapStatusUpdateFactory.zoomTo(rank);//缩放级别
        baiduMap.animateMapStatus(updat);  //更新级别

        //设置我的位置
        MyLocationData.Builder locationBuider = new MyLocationData.Builder();
        locationBuider.latitude(v);
        locationBuider.longitude(v1);
        baiduMap.setMyLocationData(locationBuider.build());

        MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,true, BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background),0xAAFFFF88,0xAA00FF00);
        baiduMap.setMyLocationConfiguration(myLocationConfiguration);
        baiduMap.setMyLocationEnabled(true);

    }
    //移动到当前的位置
    private void moveToMtLocation(BaiduMap baiduMap,LatLng latLng, float rank) {


        //设置我的位置
        MyLocationData.Builder locationBuider = new MyLocationData.Builder();
        locationBuider.latitude(latLng.latitude);
        locationBuider.longitude(latLng.longitude);
        baiduMap.setMyLocationData(locationBuider.build());


        //更新位置和缩放级别
        MapStatusUpdate  updat=MapStatusUpdateFactory.newLatLng(latLng);   //更新经纬度
        baiduMap.animateMapStatus(updat);    //更新位置

         updat = MapStatusUpdateFactory.zoomTo(rank);//缩放级别
        baiduMap.animateMapStatus(updat);  //更新级别

        //显示当前我的位置
        MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,true,      BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background),0xAAFFFF88,0xAA00FF00);
        baiduMap.setMyLocationConfiguration(myLocationConfiguration);
        baiduMap.setMyLocationEnabled(true);

    }

    //到达某个位置提醒
    public class MyNotifyLister extends BDNotifyListener {
        public void onNotify(BDLocation mlocation, float distance){
            //已到达设置监听位置附近
        }
    }


}

package com.yahier.pincan.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.yahier.pincan.R;
import com.yahier.pincan.model.Position;
import com.yahier.pincan.utils.LogUtils;


/**
 * Created by yahier on 2018/2/7.
 * 在地图上 选择聚餐位置 ;百度地图的api是非常不友好了
 */

public class MapChooseLocationActivity extends BaseActivity implements OnGetGeoCoderResultListener {
    final String TAG = "MapChooseLocationActivity";
    private AppCompatEditText inputAddress;
    //百度地图变量
    BaiduMap mBaiduMap;
    LocationClient mLocClient;
    MapView mMapView;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float mCurrentAccracy;
    private int mCurrentDirection = 0;
    public MyLocationListenner myListener = new MyLocationListenner();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_choose_location);
        initViews();
        initLocate();
    }

    private void initViews() {
        inputAddress = findViewById(R.id.inputAddress);
        findViewById(R.id.btnLocate).setOnClickListener(this::moveToMapCenter);
        findViewById(R.id.tvSearch).setOnClickListener(this::search);
    }

    private void search(View view) {
        LogUtils.logE(TAG, "search");
        String address = inputAddress.getText().toString();
        if (address.trim().equals("")) {
            return;
        }
        LogUtils.logE(TAG, "search go");
    }

    //定位到地图中央
    private void moveToMapCenter(View view) {
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(new LatLng(mCurrentLat, mCurrentLon))
                .zoom(18.0f)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }

    String selectedPosition;

    private void initLocate() {
        mMapView = findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setBuildingsEnabled(true);//自行添加的
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);//设置了这个，location中才有街道信息了
        mLocClient.setLocOption(option);
        mLocClient.start();

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            /**
             * 单击地图
             */
            public void onMapClick(LatLng point) {
            }

            /**
             * 单击地图中的POI点
             */
            public boolean onMapPoiClick(MapPoi poi) {
                //  touchType = "单击POI点";
                //  currentPt = poi.getPosition();
                //  updateMapState();
                LogUtils.logE(TAG + " onMapPoiClick", poi.getName());//只返回了最后一段的地址信息，没有包括城市街道等
                selectedPosition = poi.getName();
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(poi.getPosition()).newVersion(0));

                return false;
            }
        });
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);

    }

    GeoCoder mSearch;

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        LogUtils.logE(TAG, "onGetReverseGeoCodeResult " + result.getAddress());
        Intent intent = getIntent();
        Position position = new Position();
        position.address = result.getAddress() + selectedPosition;
        position.latitude = result.getLocation().latitude;
        position.longitude = result.getLocation().longitude;
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            LogUtils.logE(TAG, location.getAddrStr());
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                moveToMapCenter(null);
            }
        }

    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}

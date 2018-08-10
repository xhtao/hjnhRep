package com.xht.cmsample;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xht.cmsdk.CMParams;
import com.xht.cmsdk.CMSDK;
import com.xht.cmsdk.callback.CMEventListener;
import com.xht.cmsdk.enums.ChannelType;
import com.xht.cmsdk.enums.OperateType;
import com.xht.cmsdk.enums.ShareType;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView = null;
    private List<BtnLvItemData> btnDataList = null;
    private int[] iconArray = {R.drawable.tn_icon_wx, R.drawable.tn_icon_qq, R.drawable.tn_icon_wb, R.drawable.tn_icon_ali};
    private int[] nameArray = {
            R.string.btn_login_wx, R.string.btn_login_qq, R.string.btn_login_wb, R.string.btn_login_ali,
            R.string.btn_pay_wx, R.string.btn_pay_qq, R.string.btn_pay_wb, R.string.btn_pay_ali,
            R.string.btn_share_wx, R.string.btn_share_qq, R.string.btn_share_wb, R.string.btn_share_ali
    };

    private ProgressBar connectBar = null;
    private boolean canClicked = true;

    private CMSDK cmsdk = null;
    private String appId = "wx5c8e39a102ac7c0c";
    private String secret = "58456f7da5cec66a3b3f98f774e562a0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init(){
        listView = (ListView) findViewById(R.id.lv_btn);
        btnDataList = new ArrayList<BtnLvItemData>();
        for (int i = 0; i < nameArray.length; i++){
            int _icon = iconArray[i%4];
            int _name = nameArray[i];
            BtnLvItemData tData = new BtnLvItemData(_name, _icon);
            btnDataList.add(tData);
        }

        BtnLvAdapter adapter = new BtnLvAdapter(this, btnDataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        connectBar = (ProgressBar) findViewById(R.id.connectBar);
        connectLoading("hide");
    }

    private void initData(){
        //初始化sdk
        cmsdk = CMSDK.getInstance();
    }

    private void connectLoading(String type){
        switch (type){
            case "show":
                if (canClicked == false){ return; }
                canClicked = false;
                listView.setEnabled(canClicked);
                connectBar.setVisibility(View.VISIBLE);
                break;
            case "hide":
                if (canClicked == true){ return; }
                canClicked = true;
                listView.setEnabled(canClicked);
                connectBar.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (canClicked == false){ return; }
        Toast.makeText(MainActivity.this, "item 被点击：" + position, Toast.LENGTH_SHORT).show();
        switch (position){
            case 0:
                CMParams paramsL = new CMParams.LoginBuilder(this)
                        .appID(appId)
                        .appSecret(secret)
                        .channelType(ChannelType.TypeWeChat)
                        .operateType(OperateType.TypeLogin)
                        .build();
                cmsdk.setParams(paramsL)
                        .setEventListener(new CMEventListener() {
                            @Override
                            public void onRequestStart() {
                                connectLoading("show");
                            }

                            @Override
                            public void onEventSuccess(int code, String jsonString) {
                                connectLoading("hide");
                            }

                            @Override
                            public void onEventFailed(int code) {
                                connectLoading("hide");
                                Toast.makeText(MainActivity.this, "登陆失败:" + code, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onEventCancel(int code) {
                                connectLoading("hide");
                            }
                        });
                cmsdk.CMLogin();
                break;
            case 4:

                break;
            case 8:
                CMParams paramsS = new CMParams.ShareBuilder(this)
                        .appID(appId)
                        .channelType(ChannelType.TypeWeChat)
                        .operateType(OperateType.TypeShare)
                        .shareType(ShareType.WebPage)
                        .shareTitle("百度一下")
                        .shareDescription("百度一下，你就知道... ...")
                        .shareBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.share_iamge))
                        .sharePosition(ShareType.Timeline)
                        .shareUrl("http://www.baidu.com")
                        .build();
                cmsdk.setParams(paramsS)
                        .setEventListener(new CMEventListener() {
                            @Override
                            public void onRequestStart() {
                                Toast.makeText(MainActivity.this, "http 请求开始", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onEventSuccess(int code, String jsonString) {
                                Toast.makeText(MainActivity.this, "分享成功:" + code, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onEventFailed(int code) {
                                Toast.makeText(MainActivity.this, "分享失败:" + code, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onEventCancel(int code) {
                                Toast.makeText(MainActivity.this, "分享取消:" + code, Toast.LENGTH_SHORT).show();
                            }
                        });
                cmsdk.CMShare();
                break;
        }
    }
}

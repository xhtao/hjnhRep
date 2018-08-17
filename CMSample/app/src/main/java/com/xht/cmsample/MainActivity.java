package com.xht.cmsample;

import android.content.Intent;
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
import com.xht.cmsdk.error.CMError;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cmsdk.onActivityResult(requestCode, resultCode, data);
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
                            public void onEventSuccess(CMError cmError) {
                                connectLoading("hide");
                            }

                            @Override
                            public void onEventFailed(CMError cmError) {
                                connectLoading("hide");
                                Toast.makeText(MainActivity.this, "登陆失败:" + cmError.getErrorCode(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onEventCancel(CMError cmError) {
                                connectLoading("hide");
                            }
                        });
                cmsdk.CMLogin();
                break;
            case 1:
                CMParams paramsQL = new CMParams.LoginBuilder(this)
                        .appID("222222")
                        .channelType(ChannelType.TypeQQ)
                        .operateType(OperateType.TypeLogin)
                        .build();
                cmsdk.setParams(paramsQL)
                        .setEventListener(new CMEventListener() {
                            @Override
                            public void onRequestStart() {
                            }

                            @Override
                            public void onEventSuccess(CMError cmError) {
                                try {
                                    JSONObject jsonObject = new JSONObject(cmError.getErrorData());

                                    Toast.makeText(MainActivity.this, jsonObject.getString("nickname"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onEventFailed(CMError cmError) {
                                Toast.makeText(MainActivity.this, "失败：" + cmError.getErrorCode(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onEventCancel(CMError cmError) {
                                Toast.makeText(MainActivity.this, "取消：" + cmError.getErrorCode(), Toast.LENGTH_SHORT).show();
                            }
                        });
                cmsdk.CMLogin();
                break;
            case 4:
                CMParams paramsP = new CMParams.PayBuilder(this)
                        .appID(appId)
                        .appSecret(secret)
                        .channelType(ChannelType.TypeWeChat)
                        .operateType(OperateType.TypePay)
                        .mchID("10000100")
                        .orderNum("1415659990")
                        .itemName("APP支付测试")
                        .itemDetail("支付测试")
                        .itemPrice(1)
                        .notifyUrl("http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php")
                        .signKey("0CB01533B8C1EF103065174F50BCA001")
                        .build();
                cmsdk.setParams(paramsP)
                        .setEventListener(new CMEventListener() {
                            @Override
                            public void onRequestStart() {

                            }

                            @Override
                            public void onEventSuccess(CMError cmError) {

                            }

                            @Override
                            public void onEventFailed(CMError cmError) {

                            }

                            @Override
                            public void onEventCancel(CMError cmError) {

                            }
                        });
                cmsdk.CMPay();
                break;
            case 8:
                CMParams paramsS = new CMParams.ShareBuilder(this)
                        .appID(appId)
                        .channelType(ChannelType.TypeWeChat)
                        .operateType(OperateType.TypeShare)
                        .shareType(ShareType.Image)
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
                            public void onEventSuccess(CMError cmError) {
                                Toast.makeText(MainActivity.this, "分享成功:" + cmError.getErrorCode(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onEventFailed(CMError cmError) {
                                Toast.makeText(MainActivity.this, "分享失败:" + cmError.getErrorCode(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onEventCancel(CMError cmError) {
                                Toast.makeText(MainActivity.this, "分享取消:" + cmError.getErrorCode(), Toast.LENGTH_SHORT).show();
                            }
                        });
                cmsdk.CMShare();
                break;
            case 9:
                ArrayList<String> imgList = new ArrayList<>();
                imgList.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1261189303,2074276937&fm=15&gp=0.jpg");
                imgList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3801589511,4285793770&fm=27&gp=0.jpg");
                imgList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1278190881,1035053171&fm=15&gp=0.jpg");
                imgList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=573011341,2994726092&fm=15&gp=0.jpg");
                imgList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1373489527,3677276876&fm=15&gp=0.jpg");
                imgList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2789295378,2052150349&fm=15&gp=0.jpg");
                CMParams paramsQS = new CMParams.ShareBuilder(this)
                        .appID("222222")
                        .channelType(ChannelType.TypeQQ)
                        .operateType(OperateType.TypeShare)
                        .shareType(ShareType.TextAndImage)
                        .sharePosition(ShareType.Session)
                        .appName(this.getResources().getString(R.string.app_name))
                        .shareTitle("QQ分享")
                        .shareDescription("百度一下，你就知道... ...")
                        .shareUrl("https://blog.csdn.net/qq_41138470/article/details/79017119")
                        .shareImgUrl(imgList)
                        .build();
                cmsdk.setParams(paramsQS)
                        .setEventListener(new CMEventListener() {
                            @Override
                            public void onRequestStart() {

                            }

                            @Override
                            public void onEventSuccess(CMError cmError) {
                                Toast.makeText(MainActivity.this, "分享成功:" + cmError.getErrorData(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onEventFailed(CMError cmError) {
                                Toast.makeText(MainActivity.this, "分享失败:" + cmError.getErrorCode(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onEventCancel(CMError cmError) {
                                Toast.makeText(MainActivity.this, "分享取消:" + cmError.getErrorCode(), Toast.LENGTH_SHORT).show();
                            }
                        });
                cmsdk.CMShare();
                break;
        }
    }
}

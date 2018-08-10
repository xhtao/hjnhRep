package com.xht.cmsample;

/**
 * Created by XIE on 2018/7/31.
 */

public class BtnLvItemData {
    private int btn_name = -1;
    private int btn_icon = -1;

    public BtnLvItemData(int btnName, int btnIcon){
        this.btn_name = btnName;
        this.btn_icon = btnIcon;
    }

    public int getBtn_name() {
        return btn_name;
    }

    public void setBtn_name(int btn_name) {
        this.btn_name = btn_name;
    }

    public int getBtn_icon() {
        return btn_icon;
    }

    public void setBtn_icon(int btn_icon) {
        this.btn_icon = btn_icon;
    }
}

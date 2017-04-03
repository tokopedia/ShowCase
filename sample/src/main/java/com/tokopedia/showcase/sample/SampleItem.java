package com.tokopedia.showcase.sample;

/**
 * Created by Hendry on 4/3/2017.
 */

class SampleItem {
    int iconRes;
    String title;
    String description;

    public SampleItem(int iconRes, String title, String description) {
        this.iconRes = iconRes;
        this.title = title;
        this.description = description;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

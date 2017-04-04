package com.tokopedia.showcase.sample;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<SampleItem> getSampleData(){
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem(R.mipmap.ic_launcher_round, "Red", "merah"));
        sampleItems.add(new SampleItem(R.mipmap.ic_launcher_round, "Yellow", "kuning"));
        sampleItems.add(new SampleItem(R.mipmap.ic_launcher_round, "Blue", "biru"));
        sampleItems.add(new SampleItem(R.mipmap.ic_launcher_round, "Green", "hijau"));
        sampleItems.add(new SampleItem(R.mipmap.ic_launcher_round, "Black", "hitam"));
        sampleItems.add(new SampleItem(R.mipmap.ic_launcher_round, "White", "putih"));
        sampleItems.add(new SampleItem(R.mipmap.ic_launcher_round, "Orange", "Oranye"));
        sampleItems.add(new SampleItem(R.mipmap.ic_launcher_round, "Gray", "Abu-abu"));
        sampleItems.add(new SampleItem(R.mipmap.ic_launcher_round, "Pink", "Merah muda"));
        sampleItems.add(new SampleItem(R.mipmap.ic_launcher_round, "Purple", "Ungu"));
        return sampleItems;
    }
}

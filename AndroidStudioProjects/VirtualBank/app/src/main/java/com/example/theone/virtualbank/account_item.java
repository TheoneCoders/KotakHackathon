package com.example.theone.virtualbank;

import android.graphics.Bitmap;

/**
 * Created by theone on 16/01/16.
 */
public class account_item {


        String desc,date;
        String title;

        public account_item(String desc, String title,String date) {
            super();
            this.date = date;
            this.title = title;
            this.desc=desc;
        }
        public String getDesc() {
            return desc;
        }

        public String getTitle() {
            return title;
        }

         public String getDate() {
        return date;
        }



}

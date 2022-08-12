package com.miqdad.e_news.helper

class HelperFunction {
    fun dateFormatter(date: String): String{
        // take first 11 index from string
        var temp = ""
        for(i in date.indices){
            if (i < 10){
                temp += date[i]
            }
        }
     return temp.replace("-", " ")
    }
    //MAKE LIMIT F VIEW

}
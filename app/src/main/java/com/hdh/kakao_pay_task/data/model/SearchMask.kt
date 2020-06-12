package com.hdh.kakao_pay_task.data.model

class SearchMask {
     val address : String = ""
     val count : String = ""
     val stores : ArrayList<Store> = ArrayList()


    class Store{
        val addr : String = ""
        val code : String = ""
        val created_at : String = ""
        val lat : String = ""
        val lng : String = ""
        val name : String = ""
        val remain_stat : String = ""
        val stock_at : String = ""
        val type : String = ""
    }
}
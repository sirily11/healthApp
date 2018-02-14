package com.example.qiweili.healthapp

/**
 * Created by qiweili on 2018/2/13.
 */
/**
class DataAPI(kills: Int, deaths: Int, assists: Int) {
    private val kills = kills
    private val deaths = deaths
    private val assists = assists

    fun getKDA(): String {
        return "$kills : $deaths : $assists"
    }
}
 **/
class DataAPI(account_id:Int, first_name:String, last_name:String, img_url:String){
    val account_id = account_id
    val first_name = first_name
    val last_name = last_name
    val img_url = img_url

}

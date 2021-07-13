package id.kotlin.hspbtool

class ApiEndPoint {
    companion object {

        private val SERVER = "http://103.84.233.186:8742/hspb_tool/"
        val crete_record_battery = SERVER+"create_record_battery.php"
        val read_record_battery = SERVER+"read_record_battery.php"
        val read_record_tap_time_card = SERVER+"read_record_tap_time_card.php"
        val read_record_keytag = SERVER+"read_record_keytag.php"
        val read_record_ac=SERVER+"read_record_ac.php"
       // val update_record_battery = SERVER+"update_record_battery.php"
        val login = SERVER+"login.php"
        val read_log_ventaza = SERVER+"read_log_ventaza.php"
        val read_log_ac = SERVER+"read_log_ac.php"
        val create_tap_record= SERVER+"create_record_tap_time_card.php"
        val create_keytag_record= SERVER+"create_record_keytag.php"
        val create_maintenance_ventaza= SERVER+"create_maintenance_ventaza.php"
        val create_maintenance_ac_room= SERVER+"create_maintenance_ac_room.php"
        //val log_time_card=SERVER+"read_record_tap_time_card.php"
        val change_password=SERVER+"user_change_password.php"
        val notification_tap_time_card=SERVER+"notification_tap_time_card.php"
        val notification_change_battery=SERVER+"notification_change_battery.php"
        val notification_ac_maintenance=SERVER+"notification_ac_maintenance.php"
        val register_account=SERVER+"register_account.php"
        val get_code_wo=SERVER+"get_code_wo.php"
        val get_code_wo_detail=SERVER+"get_code_wo_detail.php"
        val get_code_maintenance=SERVER+"get_code_maintenance.php"
        val get_data_item_work_order =SERVER+"get_data_item_work_order.php"
        val add_work_order = SERVER+"add_work_order.php"
        val add_work_order_detail = SERVER+"add_work_order_detail.php"
        val add_work_order_maintenance = SERVER+"add_work_order_maintenance.php"
        val add_work_order_data = SERVER+"add_work_order_data.php"
        val get_code_barang=SERVER+"get_code_barang.php"
        val add_work_order_data_and_item=SERVER+"add_work_order_data_with_item.php"
    }

}
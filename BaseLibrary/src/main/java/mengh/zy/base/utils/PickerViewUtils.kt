package mengh.zy.base.utils

import android.content.Context
import android.graphics.Color
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.google.gson.Gson
import com.google.gson.JsonParser
import mengh.zy.base.data.protocol.AddressJson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.ArrayList


/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/9/4$.
 * PS: Not easy to write code, please indicate.
 *
 *
 * Describe:
 */
object PickerViewUtils {
    fun initJsonData(context: Context, codeInterface: CodeInterface) {
        val json = getTermsString(context)
        val options1Items = gsonToList(json, AddressJson::class.java)
        val options2Items = ArrayList<ArrayList<String>>()
        val options3Items = ArrayList<ArrayList<ArrayList<AddressJson.CityBean.DistrictBean>>>()
        for (i in options1Items.indices) {//遍历省份
            val cityList = ArrayList<String>()//该省的城市列表（第二级）
            val provinceAreaList = ArrayList<ArrayList<AddressJson.CityBean.DistrictBean>>()//该省的所有地区列表（第三极）

            for (c in 0 until options1Items[i].city!!.size) {//遍历该省份的所有城市
                val cityName = options1Items[i].city!![c].name
                if (cityName != null) {
                    cityList.add(cityName)
                }//添加城市
                val cityAreaList = ArrayList<AddressJson.CityBean.DistrictBean>()//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (options1Items[i].city!![c].district == null || options1Items[i].city!![c].district!!.isEmpty()) {
                    cityAreaList.add(AddressJson.CityBean.DistrictBean())
                } else {
                    cityAreaList.addAll(options1Items[i].city!![c].district!!)
                }
                provinceAreaList.add(cityAreaList)//添加该省所有地区数据
            }

            options2Items.add(cityList)

            options3Items.add(provinceAreaList)
        }
        val pvOptions = OptionsPickerBuilder(context, OnOptionsSelectListener { options1, options2, options3, _ ->
            val code = options3Items[options1][options2][options3].code
            codeInterface.getCode(code)
            codeInterface.showAddress(options1Items[options1].province +
                    options2Items[options1][options2] +
                    options3Items[options1][options2][options3].name)
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build<Any>()

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items as List<MutableList<*>>?, options3Items as List<List<MutableList<*>>>?)//三级选择器
        pvOptions.show()

    }

    private fun getTermsString(context: Context): String? {
        val termsString = StringBuilder()
        val reader: BufferedReader
        try {
            reader = BufferedReader(
                    InputStreamReader(context.assets.open("address")))

            var str: String
            do {
                str = reader.readLine()
                termsString.append(str)
            }
            while ((reader.readLine()) != null)
            reader.close()
            return termsString.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    private fun <T> gsonToList(jsonString: String?, cls: Class<T>): List<T> {
        val list = ArrayList<T>()
        try {
            val gson = Gson()
            val array = JsonParser().parse(jsonString!!).asJsonArray
            for (jsonElement in array) {
                list.add(gson.fromJson(jsonElement, cls))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return list
    }

    interface CodeInterface {
        fun getCode(msg: String?)
        fun showAddress(msg: String)
    }
}

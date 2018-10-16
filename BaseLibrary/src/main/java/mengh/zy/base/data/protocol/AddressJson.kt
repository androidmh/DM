package mengh.zy.base.data.protocol

import com.contrarywind.interfaces.IPickerViewData

class AddressJson : IPickerViewData {

    /**
     * province : 北京市
     * city : [{"name":"北京市","district":[{"name":"东城区","code":"110101"},{"name":"西城区","code":"110102"},{"name":"朝阳区","code":"110105"},{"name":"丰台区","code":"110106"},{"name":"石景山区","code":"110107"},{"name":"海淀区","code":"110108"},{"name":"门头沟区","code":"110109"},{"name":"房山区","code":"110111"},{"name":"通州区","code":"110112"},{"name":"顺义区","code":"110113"},{"name":"昌平区","code":"110114"},{"name":"大兴区","code":"110115"},{"name":"怀柔区","code":"110116"},{"name":"平谷区","code":"110117"},{"name":"密云县","code":"110228"},{"name":"延庆县","code":"110229"}]}]
     */

    var province: String? = null
    var city: List<CityBean>? = null

    override fun getPickerViewText(): String? {
        return this.province
    }

    class CityBean {
        /**
         * name : 北京市
         * district : [{"name":"东城区","code":"110101"},{"name":"西城区","code":"110102"},{"name":"朝阳区","code":"110105"},{"name":"丰台区","code":"110106"},{"name":"石景山区","code":"110107"},{"name":"海淀区","code":"110108"},{"name":"门头沟区","code":"110109"},{"name":"房山区","code":"110111"},{"name":"通州区","code":"110112"},{"name":"顺义区","code":"110113"},{"name":"昌平区","code":"110114"},{"name":"大兴区","code":"110115"},{"name":"怀柔区","code":"110116"},{"name":"平谷区","code":"110117"},{"name":"密云县","code":"110228"},{"name":"延庆县","code":"110229"}]
         */

        var name: String? = null
        var district: List<DistrictBean>? = null

        class DistrictBean : IPickerViewData {
            /**
             * name : 东城区
             * code : 110101
             */

            var name: String? = null
            var code: String? = null

            override fun getPickerViewText(): String? {
                return this.name
            }
        }
    }
}

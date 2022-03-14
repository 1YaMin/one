package rjon.webapi.plugin;

import kd.bos.bill.IBillWebApiPlugin;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.entity.api.ApiResult;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.QueryServiceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商查询接口
 * 通过名称模糊搜索
 */

public class SupplierQueryPlugin implements IBillWebApiPlugin {

    @Override
    public ApiResult doCustomService(Map<String, Object> params) {
        String name = (String) params.get("name");       //供应商名称

        QFilter qFilter = new QFilter("name", QCP.like, "%"+name+"%");
        DynamicObjectCollection orgCollection = QueryServiceHelper.query("bd_supplier", "name, number", qFilter.toArray());

        List<Map<String, String>> dataList = new ArrayList<>();
        for (DynamicObject dynamicObject : orgCollection) {
            Map<String, String> dataItem = new HashMap<>();
            dataItem.put("name", dynamicObject.getString("name"));
            dataItem.put("number", dynamicObject.getString("number"));
            dataList.add(dataItem);
        }

        return ApiResult.success(dataList);

    }

}

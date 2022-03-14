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
 * 物料信息查询接口
 */

public class MaterialQueryPlugin implements IBillWebApiPlugin {

    @Override
    public ApiResult doCustomService(Map<String, Object> params) {
        String name = (String) params.get("name");       //物料名称

        QFilter qFilter = new QFilter("name", QCP.like, "%"+name+"%");
        DynamicObjectCollection orgCollection = QueryServiceHelper.query("bd_material", "name, number", qFilter.toArray());

        List<Map<String, String>> dataList = new ArrayList<>();
        for (DynamicObject dynamicObject : orgCollection) {
            Map<String, String> item = new HashMap<>();
            item.put("name", dynamicObject.getString("name"));
            item.put("number", dynamicObject.getString("number"));
            dataList.add(item);
        }

        return ApiResult.success(dataList);

    }

}

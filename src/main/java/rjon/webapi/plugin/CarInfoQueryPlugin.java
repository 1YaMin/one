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
 * 车底信息查询接口
 * 通过编码模糊搜索
 */

public class CarInfoQueryPlugin implements IBillWebApiPlugin {

    @Override
    public ApiResult doCustomService(Map<String, Object> params) {
        String number = (String) params.get("number");       //供应商名称

        QFilter qFilter = new QFilter("number", QCP.like, "%"+number+"%");
        DynamicObjectCollection orgCollection = QueryServiceHelper.query("rjon_carinfo", "number,rjon_batch,rjon_cartype.name", qFilter.toArray());

        List<Map<String, String>> dataList = new ArrayList<>();
        for (DynamicObject dynamicObject : orgCollection) {
            Map<String, String> dataItem = new HashMap<>();
            dataItem.put("number", dynamicObject.getString("number"));                      //车底号
            dataItem.put("batch", dynamicObject.getString("rjon_batch"));                   //批次
            dataItem.put("cartypeName", dynamicObject.getString("rjon_cartype.name"));     //车型名称
            dataList.add(dataItem);
        }

        return ApiResult.success(dataList);

    }

}

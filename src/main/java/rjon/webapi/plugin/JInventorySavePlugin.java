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

public class JInventorySavePlugin implements IBillWebApiPlugin {

    @Override
    public ApiResult doCustomService(Map<String, Object> params) {
        String multiorghead =(String) params.get("multiorghead");

        QFilter qFilter = new QFilter("multiorghead.id", QCP.like,"%"+multiorghead+"%");
        DynamicObjectCollection orgCollection = QueryServiceHelper.query("im_invaccreport", "reportlistap.material.masterid.name", qFilter.toArray());

        List<Map<String,String>> rogList = new ArrayList<>();
        for (DynamicObject rog : orgCollection) {
            Map<String,String> orgMap = new HashMap<>();
            orgMap.put("name",rog.getString("reportlistap.material.masterid.name"));
            rogList.add(orgMap);
        }
        return ApiResult.success(rogList);

    }
}

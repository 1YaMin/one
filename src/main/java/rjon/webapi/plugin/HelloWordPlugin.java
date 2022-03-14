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

public class HelloWordPlugin implements IBillWebApiPlugin {
    @Override
    public ApiResult doCustomService(Map<String, Object> params) {
        String biztime = (String) params.get("biztime");
        String billno = (String) params.get("billno");



        QFilter qFilter=  new QFilter("billno",QCP.equals,billno);
        if (biztime!=null|| biztime.length()!=0) {
            qFilter.and("biztime",QCP.equals,biztime);
        }
            List<Map<String, String>> orgList = new ArrayList<>();
            DynamicObjectCollection orgCollection = QueryServiceHelper.query("im_purinbill", "billno", qFilter.toArray());
            for (DynamicObject dynamicObject : orgCollection) {
                Map<String, String> orgMap = new HashMap<>();
                orgMap.put("billno", dynamicObject.getString("billno"));

                orgList.add(orgMap);
            }





        return ApiResult.success(orgList);
    }

}

package rjon.webapi.plugin;

import kd.bos.bill.IBillWebApiPlugin;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.entity.api.ApiResult;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.QueryServiceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurInEntryQueryPlugin implements IBillWebApiPlugin {
    @Override
    public ApiResult doCustomService(Map<String, Object> params) {
        String billno = (String) params.get("billno");

        QFilter qFilter = new QFilter("billno", QCP.equals,billno);
        DynamicObjectCollection orgCollection = QueryServiceHelper.query
     ("im_purinbill",
      "billentry.material,billentry.material.masterid.name,billentry.auxpty,billentry.unit.name," +
              "billentry.qty,billentry.lotnumber,billentry.producedate,billentry.expirydate,billentry.receivedate," +
              "billentry.warehouse,billentry.location,billentry.price,billentry.amount,billentry.entrycomment,billentry.providersupplier.name,billentry.groupnumber",qFilter.toArray());

        DynamicObject purinbill = BusinessDataServiceHelper.loadSingle("im_purinbill", "billentry.material,billentry.material.masterid.name",qFilter.toArray());
        //String materialName = purinbill.getString("billentry.material.masterid.name");
        List<Map<String,String>> orgList = new ArrayList<>();
        for (DynamicObject dynamicObject : orgCollection) {
            Map<String, String> orgMap = new HashMap<>();
            orgMap.put("billentry.material",dynamicObject.getString("billentry.material"));
            orgMap.put("materialName", dynamicObject.getString("billentry.material.masterid.name"));
            orgMap.put("billentry.auxpty",dynamicObject.getString("billentry.auxpty"));
            //orgMap.put("suplotName",dynamicObject.getString("billentry.suplot"));
            orgMap.put("billentry.unit",dynamicObject.getString("billentry.unit.name"));
            orgMap.put("billentry.qty",dynamicObject.getString("billentry.qty"));
            orgMap.put("billentry.lotnumber",dynamicObject.getString("billentry.lotnumber"));
            orgMap.put("billentry.producedate",dynamicObject.getString("billentry.producedate"));
            orgMap.put("billentry.expirydate",dynamicObject.getString("billentry.expirydate"));
            orgMap.put("billentry.receivedate",dynamicObject.getString("billentry.receivedate"));
            orgMap.put("billentry.warehouse",dynamicObject.getString("billentry.warehouse"));
            orgMap.put("billentry.location",dynamicObject.getString("billentry.location"));
            orgMap.put("billentry.price",dynamicObject.getString("billentry.price"));
            orgMap.put("billentry.amount",dynamicObject.getString("billentry.amount"));
            orgMap.put("billentry.entrycomment",dynamicObject.getString("billentry.entrycomment"));
            orgMap.put("suplot",dynamicObject.getString("billentry.providersupplier.name"));
            orgMap.put("billentry.groupnumber",dynamicObject.getString("billentry.groupnumber"));






            orgList.add(orgMap);
        }


        return ApiResult.success(orgList);
    }
}

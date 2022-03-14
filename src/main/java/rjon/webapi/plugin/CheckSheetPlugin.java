package rjon.webapi.plugin;

import com.hankcs.hanlp.collection.dartsclone.DartMap;
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

public class CheckSheetPlugin implements  IBillWebApiPlugin {
    @Override
    public ApiResult doCustomService(Map<String, Object> params) {
        String  org = (String) params.get("org");

        QFilter qFilter = new QFilter("org.name", QCP.like,"%"+org+"%");

        DynamicObjectCollection imInvcountbill = QueryServiceHelper.query("im_invcountbill", "billentry.lossqty,billentry.gainqty,billentry.qty,billentry.unit.name,billentry.warehouse.name,schemename,schemenumber,billtype.name,billstatus,biztime,billno,org.name,billentry.material.masterid.name", qFilter.toArray());
          List<Map<String,String>> orgMap = new ArrayList<>();
        for (DynamicObject orgList : imInvcountbill) {
            Map<String,String> eit =new HashMap<>();
            eit.put("materialname",orgList.getString("billentry.material.masterid.name"));
            eit.put("org",orgList.getString("org.name"));
            eit.put("billno",orgList.getString("billno"));
            eit.put("biztime",orgList.getString("biztime"));
            eit.put("billstatus",orgList.getString("billstatus"));
            eit.put("billtype",orgList.getString("billtype.name"));
            eit.put("schemenumber",orgList.getString("schemenumber"));
            eit.put("schemename",orgList.getString("schemename"));
            eit.put("billentry.warehouse",orgList.getString("billentry.warehouse.name"));
            eit.put("unit",orgList.getString("billentry.unit.name"));
            eit.put("billentry.qty",orgList.getString("billentry.qty"));
            eit.put("billentry.gainqty",orgList.getString("billentry.gainqty"));
            eit.put("billentry.lossqty",orgList.getString("billentry.lossqty"));

            orgMap.add(eit);
        }
        return ApiResult.success(orgMap);
    }
}






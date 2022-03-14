package rjon.webapi.plugin;

import kd.bos.bill.IBillWebApiPlugin;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.entity.api.ApiResult;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.fi.bcm.spread.domain.view.builder.dynamic.DynaMembScopeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcurementPlugin implements IBillWebApiPlugin {
    @Override
    public ApiResult doCustomService(Map<String, Object> params) {
        //String billno = (String) params.get("billno");//单据编号
        String supplier = (String) params.get("supplier");//供应商
        String org = (String) params.get("org");//库存组织
      String billstatus = (String) params.get("billstatus");//单据状态
        // String billstatus = (String) params.get("billstatus");//单据状态


        QFilter qFilter = new QFilter("org.number", QCP.like,"%"+org+"%");
         if(supplier!=null||supplier.length()!=0){
             qFilter.and(new QFilter("supplier.number",QCP.like,"%"+supplier+"%"));
         }
         qFilter.and(new QFilter("billstatus",QCP.like,"%"+billstatus+"%"));
        DynamicObjectCollection orgCollection = QueryServiceHelper.query
     ("im_purinbill",
      "billno, org.name, org.number, bizdept.name, bizdept.number, bizoperator.operatornumber,bizoperator.operatorname,supplier.name,supplier.number,biztime,bizorg.name,billstatus", qFilter.toArray());

        List<Map<String,String>> orgList = new ArrayList<>();
        for (DynamicObject dynamicObject : orgCollection) {
            Map<String, String> orgMap = new HashMap<>();
            orgMap.put("billno", dynamicObject.getString("billno"));
            orgMap.put("orgName", dynamicObject.getString("org.name"));
            //orgMap.put("orgNumber", dynamicObject.getString("org.number"));
            orgMap.put("bizdept.name",dynamicObject.getString("bizdept.name"));
            orgMap.put("bizdept.number",dynamicObject.getString("bizdept.number"));
           // orgMap.put("bizoperator.operatorname",dynamicObject.getString("bizoperator.operatornamee"));
          //  orgMap.put("bizoperator.operatornumber",dynamicObject.getString("bizoperator.operatornumber"));
            orgMap.put("supplier.name",dynamicObject.getString("supplier.name"));
            orgMap.put("supplier.number",dynamicObject.getString("supplier.number"));


            orgMap.put("bizorgName", dynamicObject.getString("bizorg.name"));
            orgMap.put("biztime",dynamicObject.getString("biztime"));
            orgMap.put("billstatus",dynamicObject.getString("billstatus"));


            orgList.add(orgMap);
        }

        return ApiResult.success(orgList);
    }
}

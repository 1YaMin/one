package rjon.webapi.plugin;

import kd.bos.bill.IBillWebApiPlugin;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.entity.api.ApiResult;
import kd.bos.form.field.BasedataEdit;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.QueryServiceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织查询接口
 */

public class OrgQueryPlugin implements IBillWebApiPlugin {
    private String[] mustInputParams ={"flexpanelap","name"};

    @Override
    public ApiResult doCustomService(Map<String, Object> params) {

        String flexpanelap = (String) params.get("flexpanelap");    //组织职能类型
        String orgName = (String) params.get("name");       //组织名称

        QFilter qFilter = new QFilter(flexpanelap, QCP.equals, true);
        qFilter.and(new QFilter("name", QCP.like, "%"+orgName+"%"));
        DynamicObjectCollection orgCollection = QueryServiceHelper.query("bos_org", "name, number", qFilter.toArray());

        List<Map<String, String>> orgList = new ArrayList<>();
        for (DynamicObject dynamicObject : orgCollection) {
            Map<String, String> orgMap = new HashMap<>();
            orgMap.put("name", dynamicObject.getString("name"));
            orgMap.put("number", dynamicObject.getString("number"));
            orgList.add(orgMap);
        }

        return ApiResult.success(orgList);

    }

    private void checkParams(Map<String, Object> params){

    }

}

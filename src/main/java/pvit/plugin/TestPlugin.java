package pvit.plugin;

import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.form.MessageBoxOptions;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;

public class TestPlugin extends AbstractBillPlugIn {

    @Override
    public void itemClick(ItemClickEvent evt) {
        super.itemClick(evt);

        if("pvit_baritemap".equals(evt.getItemKey())){
            QFilter qFilter = new QFilter("billno", QCP.equals, "CGDD-220302-000001");
            DynamicObject dynamicObject = BusinessDataServiceHelper.loadSingle("pm_purorderbill", "billentry,billentry.auxpty", qFilter.toArray());
            DynamicObjectCollection billentry = dynamicObject.getDynamicObjectCollection("billentry");
            String skuName = getAuxpty(billentry.get(0));
            this.getView().showConfirm(skuName, MessageBoxOptions.OK);
        }
    }

    /**
     * 获取采购订单分录辅助属性(SKU)
     * @param entryInfo 采购订单分录
     * @return
     */
    private String getAuxpty(DynamicObject entryInfo){
        String skuName = null;
        DynamicObject auxptyInfo = entryInfo.getDynamicObject("auxpty");
        if (auxptyInfo == null) {
            return null;
        }

        //获取SKU ID
        QFilter qFilter = new QFilter("hg", QCP.equals, auxptyInfo.getPkValue());
        DynamicObject bd_flexauxprop_bd = BusinessDataServiceHelper.loadSingle("bd_flexauxprop_bd", "auxpropval", qFilter.toArray());
        if (bd_flexauxprop_bd == null) {
            return null;
        }
        Long skuId = bd_flexauxprop_bd.getLong("auxpropval");
        if (skuId == null) {
            return null;
        }
        //查询SKU
        DynamicObject SKU = BusinessDataServiceHelper.loadSingle(skuId, "bos_assistantdata_detail");
        if (SKU == null) {
            return null;
        }

        //SKU名称
        skuName = SKU.getString("name");
        return skuName;
    }



}

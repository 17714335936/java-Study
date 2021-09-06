package customer.Karma.Controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import cds.gen.z.sap.com.cap.odata.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cds.Result;
import com.sap.cds.Row;
import com.sap.cds.feature.xsuaa.XsuaaUserInfo;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.Upsert;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.ql.cqn.CqnUpsert;
import com.sap.cds.services.persistence.PersistenceService;

import customer.Karma.models.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest")
public class ExpenseController {
    @Autowired
    PersistenceService db;
    @Autowired
    XsuaaUserInfo xsuaaUserInfo;

    @GetMapping("/test")
    @ResponseBody
    public String SayHello() {
        return "Hello Rest";
    }

    @GetMapping("/xsuaaUserInfo")
    @ResponseBody
    public XsuaaUserInfo xsuaaUserInfo() {
        return xsuaaUserInfo;
    }

    @RequestMapping(value = "/getCostManagementMethod", method = RequestMethod.POST)
    public List<ZCostManagementMethod> getCostManagementMethod(@RequestBody Object obj) {

        JSONObject jsonObject = JSONObject.fromObject(obj);
        if (jsonObject.containsKey("BUKRS")) {

            List<ZCostManagementMethod> rtn = new ArrayList<ZCostManagementMethod>();
            CqnSelect sel = Select.from(CostManagementMethod_.class).where(costManagementMethod -> costManagementMethod.BUKRS().eq(jsonObject.getString("BUKRS")));

            Result rs = db.run(sel);
            for (Row row : rs) {
                CostManagementMethod tmp = row.as(CostManagementMethod.class);
                ZCostManagementMethod item = new ZCostManagementMethod();
                item.setBUKRS(tmp.getBukrs());
                item.setSegment_ID(tmp.getSegmentId());
                item.setSegment(tmp.getSegment());
                if (!rtn.contains(item)) {
                    rtn.add(item);
                }
            }
            return rtn;
        }else {
            return  null;
        }
    }

    @RequestMapping(value = "/getCostManagementMethodDetailItem",  method = RequestMethod.POST)
    public List<ZDetailItem> getCostManagementMethodDetailItem(@RequestBody Object obj) {

        JSONObject jsonObject = JSONObject.fromObject(obj);

        List<ZDetailItem> rtn = new ArrayList();
        CqnSelect sel = Select.from(CostManagementMethod_.class).where(costManagementMethod ->
                costManagementMethod.Segment_ID().eq(jsonObject.getString("Segment_ID"))
                        .and(costManagementMethod.BUKRS().eq(jsonObject.getString("BUKRS")))
        );

        Result rs = db.run(sel);
        for (Row row : rs) {
            CostManagementMethod tmp = row.as(CostManagementMethod.class);
            ZDetailItem item = new ZDetailItem();
            item.setBUKRS(tmp.getBukrs());
            item.setDetailItem_ID(tmp.getDetailItemId());
            item.setDetailItem(tmp.getDetailItem());
            item.setAdminCode(tmp.getAdminCode());
            item.setAccountCode(tmp.getAccountCode());
            rtn.add(item);
        }

        return rtn;
    }

    @RequestMapping(value = "/getApprovalAuthority", method = RequestMethod.POST)
    public List<ZApprovalAuthority> getApprovalAuthority(@RequestBody Object obj) {

        JSONObject jsonObject = JSONObject.fromObject(obj);

        List<ZApprovalAuthority> rtn = new ArrayList<ZApprovalAuthority>();
        CqnSelect sel = Select.from(ApprovalAuthority_.class).where(approvalAuthority ->
                approvalAuthority.BUKRS().eq(jsonObject.getString("BUKRS")));

        Result rs = db.run(sel);
        for (Row row : rs) {
            ApprovalAuthority tmp = row.as(ApprovalAuthority.class);
            ZApprovalAuthority item = new ZApprovalAuthority();
            BeanUtils.copyProperties(tmp, item);
            item.setCode_Text(tmp.getAdminCodeText());
            item.setBUKRS(tmp.getBukrs());
            rtn.add(item);
        }
        return rtn;
    }

    @RequestMapping(value = "/Insert_ZExpenseHeader_Z_Expense_ITEM", method = RequestMethod.POST)
    public void Insert_ZExpenseHeader_Z_Expense_ITEM(@RequestBody Object obj) {

        JSONObject jsonObject = JSONObject.fromObject(obj);
        Map<String, String> map1 = new HashMap<String, String>();

        if (jsonObject.containsKey("ApprovalList")) {
            jsonObject.remove("ApprovalList");
        }

        if (jsonObject.containsKey("FlowDisplayName")) {
            map1.put("FlowDisplayName", jsonObject.getString("FlowDisplayName"));
        }
        if (jsonObject.containsKey("FormNo")) {
            map1.put("FormNumber", jsonObject.getString("FormNo"));
        }
        if (jsonObject.containsKey("SubmitUser")) {
            JSONObject SubmitUser = jsonObject.getJSONObject("SubmitUser"); //.getString("SubmitUser");
            if(SubmitUser.containsKey("EmpId")){
                map1.put("SubmitEmpId", SubmitUser.getString("EmpId"));
            }
        }
        if (jsonObject.containsKey("EmpId")) {
            map1.put("ApplicantNo", jsonObject.getString("EmpId"));
        }
        if (jsonObject.containsKey("EmpName")) {
            map1.put("ApplicantName", jsonObject.getString("EmpName"));
        }
        if (jsonObject.containsKey("ApplyDate")) {
            map1.put("ApplyDate", jsonObject.getString("ApplyDate"));
        }
        if (jsonObject.containsKey("Currency")) {
            map1.put("Currency", jsonObject.getString("Currency"));
        }
        if (jsonObject.containsKey("Rate")) {
            map1.put("Rate", jsonObject.getString("Rate"));
        }
        if (jsonObject.containsKey("ApplicationAmount")) {
            map1.put("TotalAmount", jsonObject.getString("ApplicationAmount"));
        }
        if (jsonObject.containsKey("TotalOriginalCurrency")) {
            map1.put("TotalOriAmount", jsonObject.getString("TotalOriginalCurrency"));
        }
        if (jsonObject.containsKey("Supplier_NAME")) {
            map1.put("VendorName", jsonObject.getString("Supplier_NAME"));
        }
        if (jsonObject.containsKey("LIFNR")) {
            map1.put("VendorNo", jsonObject.getString("LIFNR"));
        }
        if (jsonObject.containsKey("EmpName")) {
            map1.put("MemberName", jsonObject.getString("EmpName"));
        }
        if (jsonObject.containsKey("GuiNumber")) {
            map1.put("GuiNumber", jsonObject.getString("GuiNumber"));
        }
        if (jsonObject.containsKey("SpPaymentTerms")) {
            map1.put("SpecPaymentTerms", jsonObject.getString("SpPaymentTerms"));
        }
        if (jsonObject.containsKey("DfPaymentTerms")) {
            map1.put("DefPaymentTerms", jsonObject.getString("DfPaymentTerms"));
        }
        if (jsonObject.containsKey("WERK_DESC")) {
            map1.put("Company", jsonObject.getString("WERK_DESC"));
        }
        if (jsonObject.containsKey("WERK_CODE")) {
            map1.put("CompanyCode", jsonObject.getString("WERK_CODE"));
        }
        if (jsonObject.containsKey("PostingDate")) {
            map1.put("PostingDate", jsonObject.getString("PostingDate"));
        }
        if (jsonObject.containsKey("PostingDate")) {
            map1.put("PostingDate", jsonObject.getString("PostingDate"));
        }
        if (jsonObject.containsKey("FormDesc")) {
            map1.put("Desc", jsonObject.getString("FormDesc"));
        }
        if (jsonObject.containsKey("FormMemo")) {
            map1.put("Memo", jsonObject.getString("FormMemo"));
        }

        if(jsonObject.toString().length()<=5000){
            map1.put("Context1", jsonObject.toString().substring(0,jsonObject.toString().length()));
            map1.put("Context2", "");
            map1.put("Context3", "");
            map1.put("Context4", "");
        }
        if((5000 < jsonObject.toString().length()) && (jsonObject.toString().length() <=10000)){
            map1.put("Context1", jsonObject.toString().substring(0,5000));
            map1.put("Context2", jsonObject.toString().substring(5000,jsonObject.toString().length()));
            map1.put("Context3", "");
            map1.put("Context4", "");
        }
        if((10000 < jsonObject.toString().length()) && (jsonObject.toString().length() <=15000)){
            map1.put("Context1", jsonObject.toString().substring(0,5000));
            map1.put("Context2", jsonObject.toString().substring(5000,10000));
            map1.put("Context3", jsonObject.toString().substring(10000,jsonObject.toString().length()));
            map1.put("Context4", "");
        }
        if((15000 < jsonObject.toString().length()) && (jsonObject.toString().length() <=20000)){
            map1.put("Context1", jsonObject.toString().substring(0,5000));
            map1.put("Context2", jsonObject.toString().substring(5000,10000));
            map1.put("Context3", jsonObject.toString().substring(10000,15000));
            map1.put("Context4", jsonObject.toString().substring(15000,jsonObject.toString().length()));
        }

        map1.put("Status", "InProcess");
        System.out.println(jsonObject.toString());
        System.out.println(jsonObject.toString().length());
//        System.out.println(123);
        CqnUpsert upsert = Upsert.into(ZExpenseHeader_.class).entry(map1);
        db.run(upsert);

        String FormNo = jsonObject.getString("FormNo");
        CqnSelect sel = Select.from(ZExpenseItem_.class).where(ExpenseItem -> ExpenseItem.FormNumber().eq(FormNo));
        Result rs = db.run(sel);
        int j = rs.list().size();
        if (jsonObject.containsKey("Table")) {
            JSONArray Table = jsonObject.getJSONArray("Table");
            for (int i = 0; i < Table.size(); i++) {
                Map<String, String> map2 = new HashMap<String, String>();
                JSONObject item = JSONObject.fromObject(Table.get(i));
//                System.out.println(item);
                map2.clear();
                j = j + 1;
                map2.put("ID", String.valueOf(j * 10));
                map2.put("ItemNo", String.valueOf(j * 10));
                if (jsonObject.containsKey("FormNo")) {
                    map2.put("FormNumber", jsonObject.getString("FormNo"));
                }
                if (item.containsKey("DocumentCategory_Value")) {
                    map2.put("InvoiceCategory", item.getString("DocumentCategory_Value"));
                }
                if (item.containsKey("DocumentCategory_Text")) {
                    map2.put("InvoiceCategory_Text", item.getString("DocumentCategory_Text"));
                }
                if (item.containsKey("VoucherNumber")) {
                    map2.put("InvoiceNo", item.getString("VoucherNumber"));
                }
                if (item.containsKey("InvoiceDate")) {
                    map2.put("InvoiceDate", item.getString("InvoiceDate"));
                }
                if (item.containsKey("VoucherAmount")) {
                    map2.put("InvoiceAmount", item.getString("VoucherAmount"));
                }
                if (item.containsKey("Tax")) {
                    map2.put("TaxAmount", item.getString("Tax"));
                }
                if (item.containsKey("Tax")) {
                    map2.put("TaxAmount", item.getString("Tax"));
                }
                if (item.containsKey("Amount")) {
                    map2.put("Amount", item.getString("Amount"));
                }
                if (item.containsKey("ApplicationAmount")) {
                    map2.put("ApplyAmount", item.getString("ApplicationAmount"));
                }
                if (item.containsKey("InternalOrder")) {
                    map2.put("InternalOrder", item.getString("InternalOrder"));
                }
                if (item.containsKey("CostCenter_Text")) {
                    map2.put("CostCenter", item.getString("CostCenter_Text"));
                }
                if (item.containsKey("CostCenter")) {
                    map2.put("CostCenterNo", item.getString("CostCenter"));
                }
                if (item.containsKey("DetailDesc")) {
                    map2.put("CostCenterNo", item.getString("DetailDesc"));
                }
                if (item.containsKey("Segment")) {
                    map2.put("Segment", item.getString("Segment"));
                }
                if (item.containsKey("Middle_Term")) {
                    map2.put("Middle_Term", item.getString("Middle_Term"));
                }
                if (item.containsKey("Editor")) {
                    map2.put("Editor", item.getString("Editor"));
                }
                map2.put("Status", "InProcess");
                if (item.containsKey("AccountCode")) {
                    map2.put("GL_ACCOUNT", item.getString("AccountCode"));
                }
                if (item.containsKey("TaxCode")) {
                    map2.put("TaxCode", item.getString("TaxCode"));
                }
                upsert = Upsert.into(ZExpenseItem_.class).entry(map2);
                db.run(upsert);
            }

        }
    }

    @RequestMapping(value = "/CheckInvoice", method = RequestMethod.POST)
    public String CheckInvoice(@RequestBody Object obj) {
        String msg = "";
        JSONObject jsonObject = JSONObject.fromObject(obj);

        if(jsonObject.containsKey("Type") && jsonObject.getString("Type").equals("2")){
            return "";
        }

        if(Float.parseFloat(jsonObject.getString("ApplicationAmount2")) < 0){
            return "";
        }

        String FormNo = "";
        if (jsonObject.containsKey("FormNo")) {
            FormNo =  jsonObject.getString("FormNo");
        }
        Map<String, String> map = new HashMap<String, String>();
        if (jsonObject.containsKey("Table")) {
            JSONArray Table = jsonObject.getJSONArray("Table");
            for (int i = 0; i < Table.size(); i++) {
                JSONObject item = JSONObject.fromObject(Table.get(i));
                if (item.containsKey("VoucherNumber") && !item.get("VoucherNumber").toString().trim().equals("")) {
                    CqnSelect sel = Select.from(ZExpenseItem_.class).where(ExpenseItem -> ExpenseItem.InvoiceNo().eq(item.get("VoucherNumber").toString()).and(ExpenseItem.InvoiceCategory().eq(item.get("DocumentCategory_Value").toString()).and(ExpenseItem.Status().in("InProcess","Finish"))));
                    Result rs = db.run(sel);
                    if(rs.list().size()>0){
                        Row item2 = rs.list().get(0);
                        String FormNumber = item2.get("FormNumber").toString();
                        if(!FormNumber.equals(FormNo)){
                            msg =  msg + "Invoice " + item.getString("VoucherNumber") + " exists in " + rs.list().get(0).get("FormNumber");
                        }
                    }
                }

            }

        }
        return msg;
    }

    private Map<String, String> payloadMap(String json) {
        try {
            Map<String, String> event = new ObjectMapper().readValue(json, new TypeReference<Map<String, String>>() {});
            return event;
        } catch (JsonProcessingException e) {
            return new HashMap<>();
        }
    }

    @RequestMapping(value = "/Udata_Invoice_Cancel", method = RequestMethod.POST)
    public void Udata_Invoice_Cancel(@RequestBody Object obj) {
        JSONObject jsonObject = JSONObject.fromObject(obj);
        String FormNo = jsonObject.getString("FormNo");

        CqnSelect sel1 = Select.from(ZExpenseHeader_.class).where(ZExpenseHeader -> ZExpenseHeader.FormNumber().eq(FormNo));
        Result rs1 = db.run(sel1);
        if (rs1.list().size()>0){
                Row item1 = rs1.list().get(0);
                Map<String, String> map1 = payloadMap(item1.toJson());
                map1.put("Status","Cancel");
                Upsert upsert = Upsert.into(ZExpenseHeader_.class).entry(map1);
                db.run(upsert);
                map1.clear();
        }

        CqnSelect sel2 = Select.from(ZExpenseItem_.class).where(ExpenseItem -> ExpenseItem.FormNumber().eq(FormNo).and(ExpenseItem.Status().eq("InProcess")));
        Result rs2 = db.run(sel2);
        for (int i = 0; i  < rs2.list().size(); i ++) {
            Row item2 = rs2.list().get(i);
            Map<String, String> map2 = payloadMap(item2.toJson());
            map2.put("Status","Cancel");
            Upsert upsert = Upsert.into(ZExpenseItem_.class).entry(map2);
            db.run(upsert);
            map2.clear();
        }
    }


    @RequestMapping(value = "/Udata_Invoice_Finish", method = RequestMethod.POST)
    public void Udata_Invoice_Finish(@RequestBody Object obj) {
        JSONObject jsonObject = JSONObject.fromObject(obj);
        String FormNo = jsonObject.getString("FormNo");

        CqnSelect sel1 = Select.from(ZExpenseHeader_.class).where(ZExpenseHeader -> ZExpenseHeader.FormNumber().eq(FormNo));
        Result rs1 = db.run(sel1);
        if (rs1.list().size()>0){
            Row item1 = rs1.list().get(0);
            Map<String, String> map1 = payloadMap(item1.toJson());
            map1.put("Status","Finish");
            Upsert upsert = Upsert.into(ZExpenseHeader_.class).entry(map1);
            db.run(upsert);
            map1.clear();
        }

        CqnSelect sel2 = Select.from(ZExpenseItem_.class).where(ExpenseItem -> ExpenseItem.FormNumber().eq(FormNo).and(ExpenseItem.Status().eq("InProcess")));
        Result rs2 = db.run(sel2);
        for (int i = 0; i < rs2.list().size(); i++) {
            Row item2 = rs2.list().get(i);
            Map<String, String> map2 = payloadMap(item2.toJson());
            map2.put("Status","Finish");
            Upsert upsert = Upsert.into(ZExpenseItem_.class).entry(map2);
            db.run(upsert);
            map2.clear();
        }
    }

    private static String formatData(String data) {
        data = data.trim();
        if(data.length() == 8){
            return data.substring(0, 4) + data.substring(4, 6) + data.substring(6, 8);
        }else{
            return data.substring(0, 4) + data.substring(5, 7) + data.substring(8, 10);
        }
    }

    @RequestMapping(value = "/Insert_ZFI_EXP_H_ZFI_EXP_D", method = RequestMethod.POST)
    public JSONObject Insert_ZFI_EXP_H_ZFI_EXP_D(@RequestBody Object obj) {
        JSONObject jsonObject = JSONObject.fromObject(obj);

        JSONObject returnjsonObject = new JSONObject();
        Map<String, String> map1 = new HashMap<String, String>();
        List list = new ArrayList();
        Upsert upsert;
        map1.clear();
        if (jsonObject.containsKey("Mandt")) {
            map1.put("MANDT", jsonObject.getString("Mandt"));
        }
        map1.put("DOCNO",jsonObject.getString("FormNo").split("-")[1]);
        if (jsonObject.containsKey("FormDesc")) {
            map1.put("HEADER_TXT", jsonObject.getString("FormDesc"));
        }
        if (jsonObject.containsKey("Table")) {
            JSONArray Table = jsonObject.getJSONArray("Table");
            JSONObject item = JSONObject.fromObject(Table.get(0));
            if (item.containsKey("InvoiceDate")) {
                map1.put("DOC_DATE", formatData(item.getString("InvoiceDate")));
            }if (item.containsKey("VoucherNumber")) {
                map1.put("REF_DOC_NO", item.getString("VoucherNumber"));
            }
        }
        if (jsonObject.containsKey("ApplyDate")) {
            map1.put("TRANS_DATE", formatData(jsonObject.getString("ApplyDate")));
        }
        if (jsonObject.containsKey("PostingDate")) {
            map1.put("PSTNG_DATE", formatData(jsonObject.getString("PostingDate")));
            map1.put("FISC_YEAR", formatData(jsonObject.getString("PostingDate")).substring(0, 4));
            map1.put("FIS_PERIOD", formatData(jsonObject.getString("PostingDate")).substring(4, 6));
        }
        if (jsonObject.containsKey("WERK_CODE")) {
            map1.put("COMP_CODE", jsonObject.getString("WERK_CODE"));
        }
        map1.put("DOC_TYPE", "ZM");
        map1.put("BUS_ACT", "RFBU");
        if (jsonObject.containsKey("LIFNR")) {
            map1.put("VENDOR_NO", jsonObject.getString("LIFNR"));
        }
        if (jsonObject.containsKey("SpPaymentTerms")) {
            map1.put("PMNTTRMS", jsonObject.getString("SpPaymentTerms"));
        }
        if (jsonObject.containsKey("PostingDate")) {
            map1.put("BLINE_DATE", formatData(jsonObject.getString("PostingDate")));
        }
        if (jsonObject.containsKey("Currency")) {
            map1.put("CURRENCY", jsonObject.getString("Currency"));
        }
        map1.put("BPM_RATE", "");
        if (jsonObject.containsKey("Rate")) {
            map1.put("EXCH_RATE", jsonObject.getString("Rate"));
        }

        Date date=new Date();//此时date为当前的时间
        SimpleDateFormat dateFormat1=new SimpleDateFormat("YYYYMMdd");
        SimpleDateFormat dateFormat2=new SimpleDateFormat("HH:mm:ss");
        map1.put("BPM_DATE", dateFormat1.format(date));
        map1.put("BPM_TIME", dateFormat2.format(date));
        map1.put("STATUS", "N");
        map1.put("SAPRETURN", "");
        if (jsonObject.containsKey("FormNo")) {
            map1.put("BPMID", jsonObject.getString("FormNo"));
        }

        returnjsonObject.put("SAP_ZFI_EXP_H",map1);

        upsert = Upsert.into(ZfiExpH_.class).entry(map1);
        db.run(upsert);

        if (jsonObject.containsKey("Table")) {
            JSONArray Table = jsonObject.getJSONArray("Table");
            for (int i = 0; i < Table.size(); i++) {
                Map<String, String> map2 = new HashMap<String, String>();
                JSONObject item = JSONObject.fromObject(Table.get(i));
                map2.clear();
                if (jsonObject.containsKey("Mandt")) {
                    map2.put("MANDT", jsonObject.getString("Mandt"));
                }
                map2.put("DOCNO",jsonObject.getString("FormNo").split("-")[1]);
                map2.put("ITEMNO", String.valueOf((i + 1) * 10));
                if (item.containsKey("AccountCode")) {
                    map2.put("GL_ACCOUNT", item.getString("AccountCode"));
                }
                if (item.containsKey("DetailedText")) {
                    map2.put("ITEM_TEXT", item.getString("DetailedText"));
                }
                if (item.containsKey("TaxCode")) {
                    map2.put("MWSKZ", item.getString("TaxCode"));
                }
                if (item.containsKey("Tax")) {
                    map2.put("TAX_DOCCUR", item.getString("Tax"));
                }
                if (item.containsKey("CostCenter")) {
                    map2.put("COSTCENTER", item.getString("CostCenter"));
                }
                if (item.containsKey("InternalOrder")) {
                    map2.put("ORDERID", item.getString("InternalOrder"));
                }
                if (jsonObject.containsKey("FormNo")) {
                    map2.put("BPMID", jsonObject.getString("FormNo"));
                }
                if (item.containsKey("VoucherNumber")) {
                    map2.put("REF_DOC_NO", item.getString("VoucherNumber"));
                }
//                if (jsonObject.containsKey("GuiNumber")) {
//                    map2.put("KIDNO", jsonObject.getString("GuiNumber"));
//                }
                if (item.containsKey("Editor")) {
                    map2.put("KIDNO", item.getString("Editor"));
                }
                if (item.containsKey("InvoiceDate")) {
                    map2.put("GUIDATE", formatData(item.getString("InvoiceDate")));
                }
                if (item.containsKey("Amount")) {
                    map2.put("AMT_DOCCUR", item.getString("Amount"));
                }
                map2.put("Segment", "");
                map2.put("DetailItem", "");
                map2.put("AdministrationRule", "");
                map2.put("AdminCode", "");
                map2.put("ItemDesc", "");
                if (item.containsKey("AccountCode")) {
                    map2.put("AccountCode", item.getString("AccountCode"));
                }

                list.add(map2);
                upsert = Upsert.into(ZfiExpD_.class).entry(map2);
                db.run(upsert);
            }
        }

        returnjsonObject.put("SAP_ZFI_EXP_D",list);
        return returnjsonObject;
    }

    @RequestMapping(value = "/getAllCostManagementMethod", method = RequestMethod.GET)
    public List<CostManagementMethod> getAllCostManagementMethod() {


            List<CostManagementMethod> rtn = new ArrayList<CostManagementMethod>();
            CqnSelect sel = Select.from(CostManagementMethod_.class);

            Result rs = db.run(sel);
            for (Row row : rs) {
                CostManagementMethod tmp = row.as(CostManagementMethod.class);
                rtn.add(tmp);
            }
            return rtn;
    }

    @RequestMapping(value = "/get_Context", method = RequestMethod.POST)
    public String get_Context(@RequestBody Object obj) {

        Map<String,JSONObject > map = new HashMap<String, JSONObject>();
        JSONObject jsonObject = JSONObject.fromObject(obj);
        String FormNumber = jsonObject.getString("FormNumber");
        CqnSelect sel = Select.from(ZExpenseHeader_.class).where(ZExpenseHeader -> ZExpenseHeader.FormNumber().eq(FormNumber).and(ZExpenseHeader.Status().eq("Finish")));
        Result rs = db.run(sel);
        if (rs.list().size()>0){
            ZExpenseHeader tmp = rs.list().get(0).as(ZExpenseHeader.class);
            String getContext = tmp.getContext1() + tmp.getContext2() + tmp.getContext3() + tmp.getContext4();
            JSONObject jsonResult = JSONObject.fromObject(getContext);
            jsonResult.put("FormNo",jsonObject.getString("FormNo"));
            jsonResult.put("Type","2");
            return jsonResult.toString();
        }
        return "";

    }

    @RequestMapping(value = "/Input", method = RequestMethod.POST)
    public void Input(@RequestBody String obj) {
        System.out.println(obj);
    }

}

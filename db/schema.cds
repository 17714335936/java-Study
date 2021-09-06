namespace z.sap.com.cap.odata;

using { Currency, managed, cuid, temporal } from '@sap/cds/common';

using { z.sap.com.cap.odata as common } from '../db/common';


entity CostManagementMethod {
    key BUKRS         : String;
    key AdminCode     : String;
    key Segment_ID    : String;
    key DetailItem_ID : String;
        Segment       : String;
        DetailItem    : String;
        ItemDesc      : String;
        AccountCode   : String;
}

entity ApprovalAuthority{
    key BUKRS                  : String;
    key AdminCode              : String;
    key Item                   : String;
        AdminCode_Text         : String;
        Content                : String;
        FromValue              : String;
        ToValue                : String;
        Grade0                 : String;
        Grade1                 : String;
        Grade2                 : String;
        Grade3                 : String;
        Grade4                 : String;
        Grade5                 : String;
        Grade6                 : String;
        Notify                 : String;
}

entity Z_Expense_Header {
    key FlowDisplayName    : String; //表單主旨
    key FormNumber         : String; //表單編號
    SubmitEmpId            : String; //SubmitEmpId
//        Segment            : String; //大項
//        DetailItem         : String; //小項
        ApplicantNo        : String; //申請人工號
        ApplicantName      : String; //申請人
        ApplyDate          : String; //申請日
        Currency           : String; //幣別
        Rate               : String; //匯率
        TotalAmount        : String; //總計台幣
        TotalOriAmount     : String; //總計原幣
        VendorName         : String; //供應商
        VendorNo           : String; //供應商代號
        MemberName         : String; //員工姓名
        GuiNumber          : String; //統一編號
        SpecPaymentTerms   : String; //特殊付款條件
        DefPaymentTerms    : String; //預設付款條件
        Company            : String; //公司別
        CompanyCode        : String; //公司別代號
        PostingDate        : String; //過帳日
        Desc               : String; //說明
        Memo               : String; //備註
        AdministrationRule : String; //管理辦法
        OtherDesc          : String; //其他說明
        SOP                : String; //SOP
        Context1           : String; //Context1
        Context2           : String; //Context2
        Context3           : String; //Context3
        Context4           : String; //Context4
        Status             : String; //表單状态
}

entity Z_Expense_ITEM {
    key ID              : String; //流水號
    key ItemNo          : String; //項目編號(10、20、30)
    key FormNumber      : String; //表單編號
        InvoiceCategory : String; //憑證種類
        InvoiceCategory_Text : String; //憑證種類
        InvoiceNo       : String; //憑證號碼
        InvoiceDate     : String; //發票日期
        InvoiceAmount   : String; //憑證金額
        TaxAmount       : String; //稅額
        Amount          : String; //金額
        ApplyAmount     : String; //申請金額
        InternalOrder   : String; //內部訂單
        CostCenter      : String; //成本中心
        CostCenterNo    : String; //成本中心代號
        DetailDesc      : String; //明細本文
        Editor      : String; //明細本文
  //      GuiNumber       : String; //統一編號
        Segment         : String; //費用大項
        Middle_Term      : String; //費用小項
        Status           : String; //发票状态
        GL_ACCOUNT       : String; //會計科目
        TaxCode          : String; //稅碼
}


entity ZFI_EXP_H {
    key MANDT      : String; //
    key DOCNO      : String; //
        HEADER_TXT : String; //
        DOC_DATE   : String; //
        PSTNG_DATE : String; //
        TRANS_DATE : String; //
        FISC_YEAR  : String; //
        FIS_PERIOD : String; //
        COMP_CODE  : String; //
        DOC_TYPE   : String; //
        REF_DOC_NO : String; //
        BUS_ACT    : String; //
        VENDOR_NO  : String; //
        PMNTTRMS   : String; //
        BLINE_DATE : String; //
        CURRENCY   : String; //
        BPM_RATE   : String; //
        EXCH_RATE  : String; //
        BPM_DATE   : String; //
        BPM_TIME   : String; //
        STATUS     : String; //
        SAPRETURN  : String; //
        BPMID      : String; //FormNumber表單編號同值
}

entity ZFI_EXP_D {
    key MANDT              : String; //
    key DOCNO              : String; //
    key ITEMNO             : String; //
        GL_ACCOUNT         : String; //
        ITEM_TEXT          : String; //
        MWSKZ              : String; //
        AMT_DOCCUR         : String; //
        TAX_DOCCUR         : String; //
        COSTCENTER         : String; //
        ORDERID            : String; //
        BPMID              : String; //FormNumber表單編號同值
        REF_DOC_NO         : String; //
        KIDNO              : String; //
        GUIDATE            : String; //
        Segment            : String; //大項
        DetailItem         : String; //小項
        AdministrationRule : String; //管理辦法
        AdminCode          : String; //簡碼
        ItemDesc           : String; //說明
        AccountCode        : String; //對應會科代號
}


entity Order : cuid {
        orderStatus  : String;
        deliveredBy  : String;
        buyerDetails : Composition of one Customer
                       on buyerDetails.PARENT_KEY = ID;
}

entity Customer :  cuid, managed {
    custName    : String;
    PARENT_KEY  : UUID;
    custEmail   : common.Email;
                         phone       : common.Phone;
                         custAddress : Composition of many Address
                                       on custAddress.PARENT_KEY = ID;
    A:  Association to one Editoriales ;
    clientesVendidos  : Association to many Clientes_libros
                           on clientesVendidos.libro = $self;

}

entity Address : cuid {
    PARENT_KEY  : UUID;
    state       : String;
    city        : String;
    pin         : String;
    address     : String;
    addressType : common.AddressType;
}

entity OrderItems : cuid {
     PARENT_KEY : UUID;
     products   : Composition of many Product
                  on products.PARENT_KEY = ID;
 }

entity Product : cuid {
    PARENT_KEY      : UUID;
    name            : String(30);
    imageUrl        : String;
    quantityLeft    : Decimal(3, 2);
    availability    : Boolean;
    pricePerUnit    : common.AmountP;
    CURRENCY_CODE   : Currency;
    discountPercent : Decimal(2, 2);
    quantityUnit    : String;
    description     : String;
    Category        : common.Category;
}

entity Editoriales : cuid, managed {
    nombre       : String;
    nacionalidad : String;
};

entity Clientes_libros : cuid {
       cliente : Association to Clientes;
       libro   : Association to Customer;
}

aspect logueo : {
              @mandatory nombreUsuario : String;
              @mandatory contrasenia   : String;
              }

aspect Usuarios : logueo {
                       @mandatory email : array of String;
                       puntos           : Integer;
                       estado           : String
                       }


entity Clientes : cuid, managed {
    @mandatory nombre          : String;
    @mandatory fechaNacimiento : String;
                                 @mandatory DNI             : String;
                                 librosComprados            : Association to many Clientes_libros
                                                              on librosComprados.cliente = $self;
    };





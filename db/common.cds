namespace z.sap.com.cap.odata;


type AmountP : Decimal(15, 2)@(
    Semantics.amount.currencyCode : 'CURRENCY_CODE',
    sap.unit                      : 'CURRENCY_CODE'
);


type Category : String enum {
    Vegetable;
    Electronics;
    Grocery;
    Health;
    Fruits;
    Fish;
    Meat;
    Beauty;
}

type orderStatus : String enum {
    Delivered;
    Pending;
    OnTheWay;
    Preparing;
    InProcess;
};


type AddressType : String enum {
    Home;
    Work;
};


type Email : String @assert.format : '^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$';
type Phone : String @assert.format : '((?:\+|00)[17](?: |\-)?|(?:\+|00)[1-9]\d{0,2}(?: |\-)?|(?:\+|00)1\-\d{3}(?: |\-)?)?(0\d|\([0-9]{3}\)|[1-9]{0,3})(?:((?: |\-)[0-9]{2}){4}|((?:[0-9]{2}){4})|((?: |\-)[0-9]{3}(?: |\-)[0-9]{4})|([0-9]{7}))';

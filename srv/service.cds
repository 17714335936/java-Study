using { z.sap.com.cap.odata as db } from '../db/schema';

@path:'admin'
service KarmaService{
   entity CostManagementMethod   as projection on db.CostManagementMethod;
   entity ApprovalAuthority   as projection on db.ApprovalAuthority;
   entity Z_Expense_Header as projection on db.Z_Expense_Header;
   entity Z_Expense_ITEM as projection on db.Z_Expense_ITEM;
   entity ZFI_EXP_H as projection on db.ZFI_EXP_H;
   entity ZFI_EXP_D as projection on db.ZFI_EXP_D;
   entity Customer as projection on db.Customer;
   entity Address as projection on db.Address;
   entity Order as projection on db.Order;
   entity Editoriales as projection on db.Editoriales;
   entity Clientes as projection on db.Clientes;
   entity Clientes_libros as projection on db.Clientes_libros;
}
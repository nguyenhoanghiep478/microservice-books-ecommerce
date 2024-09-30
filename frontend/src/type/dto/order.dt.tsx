import { OrderItem } from "./order-item.dt";
import { OrderType } from "../order-type.dt";
import { Status } from "../status.dt";

export class Order{
    orderNumber:number;
    orderType:OrderType;
    status:Status;
    customerId:number;
    customerName:string;
    totalPrice:number;
    orderItems:OrderItem[];
    paymentMethod:string;
    createdDate:string;
    token:string;

    constructor(orderNumber:number,orderType:OrderType,status:Status,customerId:number,customerName:string,totalPrice:number,orderItems:OrderItem[],paymentMethod:string,createdDate:string,token:string){
        this.orderNumber = orderNumber;
        this.orderType = orderType;
        this.status = status;
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
        this.paymentMethod = paymentMethod;
        this.createdDate = createdDate;
        this.token = token;
    }
    
}
export class OrderItem{
    bookId:number;
    name:string;
    price:number;
    totalQuantity:number = 1;

    constructor(id:number,name:string,price:number){
        this.bookId = id;
        this.price = price;
        this.name = name;
    }

    addTotalQuantity(){
        this.totalQuantity+=1;
    }
}
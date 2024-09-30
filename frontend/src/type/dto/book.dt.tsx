export class Book implements Product{
    id:number;
    categoryId:number;
    distributorId:number;
    availableQuantity:number;
    chapter:number;
    price:number;
    title:string;
    name:string;
    image:string;
    description:string;
    isInStock:Boolean;
    createdDate:string;
    lastModifiedDate:string;

    constructor(id,categoryId,distributorId,availableQuantity,chapter,price,title,name,image,description,isInStock,createdDate,lastModifiedDate){
        this.id = id;
        this.categoryId = categoryId;
        this.distributorId = distributorId;
        this.availableQuantity = availableQuantity;
        this.chapter = chapter;
        this.price = price;
        this.title = title;
        this.name = name;
        this.image = image;
        this.description = description;
        this.isInStock = isInStock;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
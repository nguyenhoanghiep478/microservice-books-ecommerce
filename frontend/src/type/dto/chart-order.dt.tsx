import { DateOfWeek } from "./date-of-week.enum";

export class ChartOrder{
    name:string;
    totalPrice:number;
    

    constructor(name:string,totalPrice:number){
        this.name = name;
        this.totalPrice =totalPrice;
    }


  
}
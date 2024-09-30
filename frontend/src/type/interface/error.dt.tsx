interface CustomError{
    errorDescription:string;
    code:number;
    error:string;
    validationErrors:Set<string>;
}
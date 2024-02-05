export class BookParam {
    category: string = '';
    publisher: string = '';
    language: any = '';
    keyword: string = '';
    pageSize: number = 10;
    pageNumber: number = 0;
    constructor() {
        this.category = 'all';
        this.publisher = 'all';
        this.language = 'all';
        this.keyword = '';
    }
}
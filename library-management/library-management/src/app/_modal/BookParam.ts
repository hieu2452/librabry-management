export class BookParam {
    category: string = '';
    publisher: string = '';
    language: any = '';
    keyword: string = '';
    constructor() {
        this.category = 'all';
        this.publisher = 'all';
        this.language = 'all';
        this.keyword = '';
    }
}
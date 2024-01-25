export class BookParam {
    category: string = '';
    publisher: string = '';
    language: any = '';
    constructor() {
        this.category = 'all';
        this.publisher = 'all';
        this.language = 'all'
    }
}
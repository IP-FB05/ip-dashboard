import { Pipe, PipeTransform } from "@angular/core";
import { Document } from './document';



@Pipe({
    name: 'textFilterDocuments'
})

export class filterDocumentsPipe implements PipeTransform {

    transform(documents: Document[], text: string): Document[] {

        if (text == null || text === "") return documents;
        return documents.filter(d => d.name.includes(text) || d.categoryname.includes(text) || d.name.toLowerCase().includes(text) || d.categoryname.toLowerCase().includes(text));
    }
}
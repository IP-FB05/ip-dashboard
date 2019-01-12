import { Pipe, PipeTransform } from "@angular/core";

// Import Models
import { Document } from '../document/document';

// Import Components
// Import Services



@Pipe({
    name: 'textFilterDocuments'
})

export class filterDocumentsPipe implements PipeTransform {

    transform(documents: Document[], text: string): Document[] {

        if (text == null || text === "") return documents;
        return documents.filter(d => d.name.includes(text) || d.name.toLowerCase().includes(text));
    }
}
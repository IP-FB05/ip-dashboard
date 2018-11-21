import { Pipe, PipeTransform } from "@angular/core";
import { Dokument } from './dokument';



@Pipe({
    name: 'textFilterDokumente'
})

export class filterDokumentePipe implements PipeTransform {

    transform(dokumente: Dokument[], text: string): Dokument[] {

        if (text == null || text === "") return dokumente;
        return dokumente.filter(d => d.name.includes(text) || d.kategoriename.includes(text));
    }
}
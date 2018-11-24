import { Pipe, PipeTransform } from "@angular/core";
import { System } from './system';



@Pipe({
    name: 'textFilterSystems'
})

export class filterSystemsPipe implements PipeTransform {

    transform(systems: System[], text: string): System[] {

        if (text == null || text === "") return systems;
        return systems.filter(s => s.name.toLowerCase().includes(text) || s.description.toLowerCase().includes(text));
    }
}
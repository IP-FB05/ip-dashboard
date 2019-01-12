import { Pipe, PipeTransform } from "@angular/core";

// Import Models
import { System } from '../system/system';

// Import Components
// Import Services




@Pipe({
    name: 'textFilterSystems'
})

export class filterSystemsPipe implements PipeTransform {

    transform(systems: System[], text: string): System[] {

        if (text == null || text === "") return systems;
        return systems.filter(s => s.name.includes(text) || s.description.includes(text) || s.name.toLowerCase().includes(text) || s.description.toLowerCase().includes(text));
    }
}
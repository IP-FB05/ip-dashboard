import { Pipe, PipeTransform } from "@angular/core";
import { Process } from './process';



@Pipe({
    name: 'textFilterProcesses'
})

export class filterProcessesPipe implements PipeTransform {

    transform(processes: Process[], text: string): Process[] {

        if (text == null || text === "") return processes;
        return processes.filter(p => p.name.includes(text) || p.description.includes(text) || p.name.toLowerCase().includes(text) || p.description.toLowerCase().includes(text));
    }
}
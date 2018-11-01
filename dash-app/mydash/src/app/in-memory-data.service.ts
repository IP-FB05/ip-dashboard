import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Process } from './process';

export class InMemoryDataService implements InMemoryDbService {
    createDb() {
        const processes = [
            { id: 11, name: 'Praxissemester', desc: 'Hier finden Sie Informationen zum Ablauf des Praxissemesters' },
            { id: 12, name: 'Einschreibung', desc: 'Hier finden Sie Informationen zum Ablauf des Praxissemesters' },
            { id: 13, name: 'Wahlmodulverfahren', desc: 'Hier finden Sie Informationen zu den Wahlen der Wahlmodule' },
            { id: 14, name: 'Prozess 04', desc: 'Test' },
            { id: 15, name: 'Prozess 05', desc: 'Test' },
            { id: 16, name: 'Prozess 06', desc: 'Test' },
            { id: 17, name: 'Prozess 07', desc: 'Test' },
            { id: 18, name: 'Prozess 08', desc: 'Test' },
            { id: 19, name: 'Prozess 09', desc: 'Test' }
        ];
        return{processes};
    }
    genId(processes: Process[]): number {
        return processes.length > 0 ? Math.max(...processes.map(process => process.id)) + 1 : 11;
      }
}
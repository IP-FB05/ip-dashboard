import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Process } from './process';
import { System } from './system';

export class InMemoryDataService implements InMemoryDbService {
    createDb() {
        let processes = [
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

        let systems = [
            { id: 1, name: 'CampusOffice', 
              desc: 'Der webbasierte Studienplaner CAMPUS-Office ist Teil des integrierten CAMPUS-Informationssystems der FH Aachen und erlaubt den Studierenden direkten Zugriff auf ihre persönlichen Vorlesungs- und Veranstaltungsdaten. Damit steht Ihnen ein Werkzeug zur Verfügung, das eine optimierte Planung und Verwaltung Ihres Studiums ermöglicht.',
              link: "https://www.campusoffice.fh-aachen.de" },

            { id: 2, name: 'Ilias',
              desc: 'ILIAS ist ein Open-Source Learning Management System (LMS). Dieser Dienst wird zur Unterstützung der Lehre an der FH Aachen eingesetzt. In Online-Kursen werden u.a. Lernmaterialien bereitgestellt oder Praktika betreut.',
              link: "https://ili.fh-aachen.de" },

            { id: 3, name: 'QIS',
              desc: 'Online-Studiumsverwaltung/-Prüfungsverwaltung/ Bewerbungsinformationen der FH Aachen',
              link: "https://qis.fh-aachen.de" }
        ];
        
        return { processes, systems };

       
    }
    genId<T extends Process | System>(myTable: T[]): number {
        return myTable.length > 0 ? Math.max(...myTable.map(t => t.id)) + 1 : 11;
    }


}
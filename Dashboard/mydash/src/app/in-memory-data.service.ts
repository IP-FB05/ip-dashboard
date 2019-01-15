 /*import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Process } from './process';
import { System } from './system';
import { Dokument } from './dokument';

export class InMemoryDataService implements InMemoryDbService {
    createDb() {
        let processes = [
            { id: 11, name: 'Praxissemester', desc: 'Hier finden Sie Informationen zum Ablauf des Praxissemesters' },
            { id: 12, name: 'Einschreibung', desc: 'Hier finden Sie Informationen zum Ablauf der Einschreibung' },
            { id: 13, name: 'Wahlmodulverfahren', desc: 'Hier finden Sie Informationen zu den Wahlen der Wahlmodule' },
            { id: 14, name: 'Prozess 04', desc: 'Hier finden Sie Informationen zum Ablauf des Prozesses' },
            { id: 15, name: 'Prozess 05', desc: 'Hier finden Sie Informationen zum Ablauf des Prozesses' },
            { id: 16, name: 'Prozess 06', desc: 'Hier finden Sie Informationen zum Ablauf des Prozesses' },
            { id: 17, name: 'Prozess 07', desc: 'Hier finden Sie Informationen zum Ablauf des Prozesses' },
            { id: 18, name: 'Prozess 08', desc: 'Hier finden Sie Informationen zum Ablauf des Prozesses' },
            { id: 19, name: 'Prozess 09', desc: 'Hier finden Sie Informationen zum Ablauf des Prozesses' }
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

        let dokumente = [
            {   id: 1, name: 'Studienbescheinigung',
                typ: 'Studienbescheinigung', date: '21/11/2018'
            },

            {   id: 2, name: 'Prüfungsordnung',
                typ: 'Studiengang Informatik', date: '01/09/2015'
            },

            {   id: 3, name: 'Dokument 3',
                typ: 'Studiengang Informatik', date: '08/04/2016'
            },

            {   id: 4, name: 'Prüfungsordnung',
                typ: 'Studiengang MCD', date: '28/08/2017'
            },
        ];
        
        return { processes, systems, dokumente };

       
    }
   
    genId<T extends Process | System | Dokument>(myTable: T[]): number {
        return myTable.length > 0 ? Math.max(...myTable.map(t => t.id)) + 1 : 11;
    }
    


}*/
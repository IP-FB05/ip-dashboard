
// Import Models
import { Process } from "../process/process";
// Import Components
// Import Services


export class processUserGroup {
    process: Process;
    usergroups: number[];

    constructor(process: Process, u: number[]) {
        this.usergroups = u;
        this.process = process;
    }

    
} 
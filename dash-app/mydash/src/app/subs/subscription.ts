export class Subscription {
    processID: number;
    username: string;

    constructor(pid: number, uname: string) {
        this.processID = pid;
        this.username = uname;
    }

    public toString(): string  {
        return "Benutzer " + this.username + " hat den Prozess mit der ProcessID: "+ this.processID + " abonniert.";
    }
}
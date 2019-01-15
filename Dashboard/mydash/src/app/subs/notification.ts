export class Notification {
    username: string;

    constructor(username: string) {
        this.username = username;
    }

    public getUsername(): string {
        return this.username;
    }

    public setUsername(username: string): void {
        this.username = username;
    }

    public toString(): string  {
        return "Benutzer: " + this.username;
    }
}
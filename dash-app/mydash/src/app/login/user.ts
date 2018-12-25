import {Group} from './group';

export class User {
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    groups: Group[];
    authenticated: boolean;
    role: string;
    token: string;
} 
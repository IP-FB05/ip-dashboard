import {Group} from './group';
import { group } from '@angular/animations';

export class User {
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    groups: Group[];
    authenticated: boolean;
    role: string;
    authdata: string;
} 
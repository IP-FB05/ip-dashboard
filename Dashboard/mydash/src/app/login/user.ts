import {Group} from './group';
import { Authorization } from './auth/authorization';
export class User {
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    groups: Group[];
    authenticated: boolean;
    role: string;
    authdata: Authorization;
} 
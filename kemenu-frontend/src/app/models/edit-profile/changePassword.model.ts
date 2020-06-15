export class ChangePassword {
    constructor(
        public password: string,
        public repeatedPassword: string,
        public email: string
    ) { }
}

import { Section } from './section.model'

export class Menu {
    constructor(
        public customerEmail: string,
        public sections: Section[],
        public id?: string
    ) {}
}
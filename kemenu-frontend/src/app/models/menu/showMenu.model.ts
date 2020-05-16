import { Section } from './section.model'

export class ShowMenu {
    constructor(
        public businessName: string,
        public sections: Section[],
        public id?: string
    ) {}
}
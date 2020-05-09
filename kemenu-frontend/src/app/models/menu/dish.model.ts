import { Allergen } from './allergen.model'

export class Dish {
    constructor(
        public name: string,
        public description: string,
        public price: number,
        public allergens: Allergen[]
    ) { }
}
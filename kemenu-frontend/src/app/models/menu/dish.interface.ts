import { Allergen } from './allergen.interface'

export interface Dish {
    name: string,
    description: string,
    price: number
    allergens: Allergen[]
}
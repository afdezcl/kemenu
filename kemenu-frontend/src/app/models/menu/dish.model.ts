import {AllergenRequestResponse} from './allergen.model';

export class Dish {
  constructor(
    public name: string,
    public description: string,
    public price: number,
    public allergens: AllergenRequestResponse[]
  ) {
  }
}

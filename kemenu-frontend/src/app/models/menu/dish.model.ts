import {Allergen} from './allergen.model';

export class Dish {
  constructor(
    public name: string,
    public description: string,
    public price: number,
    public formattedPrice: string,
    public allergens: Allergen[],
    public imageUrl?: string,
    public available?: boolean
  ) {
  }
}

export class SectionDish {
  constructor(
    public dish: Dish,
    public sectionIndex: number,
    public dishIndex: number
  ) {
  }
}

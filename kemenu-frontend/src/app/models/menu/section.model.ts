import {Dish} from './dish.model';

export class Section {
  constructor(
    public name: string,
    public dishes: Dish[]
  ) {
  }
}

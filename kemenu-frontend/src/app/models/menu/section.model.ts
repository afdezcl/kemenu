import {Dish} from './dish.model';

export class Section {
  constructor(
    public name: string,
    public dishes: Dish[]
  ) {
  }
}

export class SectionIndex {
  constructor(
    public section: Section,
    public sectionIndex: number
  ) {
  }
}

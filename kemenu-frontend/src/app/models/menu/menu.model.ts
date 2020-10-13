import { Section } from './section.model';

export class Menu {
  constructor(
    public sections: Section[],
    public name: string,
    public customerEmail?: string,
    public shortUrlId?: string,
    public imageUrl?: string,
    public id?: string,
    public currency?: string,
  ) {
  }
}

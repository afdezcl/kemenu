import {Section} from './section.model';

export class Menu {
  constructor(
    public customerEmail: string,
    public sections: Section[],
    public name?: string,
    public shortUrlId?: string,
    public imageUrl?: string,
    public id?: string,
    public currency?: string,
  ) {
  }
}

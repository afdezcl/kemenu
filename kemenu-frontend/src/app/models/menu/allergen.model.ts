export class Allergen {
  constructor(
    public id: string,
    public name: string,
    public imageName: string
  ) {
  }
}

export class AllergenRequestResponse {
  constructor(
    public id: string,
    public name: string
  ) {
  }
}

export const AllAllergens: Allergen[] = [
  {
    id: 'kemenu_allergen_1',
    name: 'Celery',
    imageName: 'celery.png'
  },
  {
    id: 'kemenu_allergen_2',
    name: 'Crustacena',
    imageName: 'crustacena.png'
  },
  {
    id: 'kemenu_allergen_3',
    name: 'Eggs',
    imageName: 'eggs.png'
  },
  {
    id: 'kemenu_allergen_4',
    name: 'Fish',
    imageName: 'fish.png'
  },
  {
    id: 'kemenu_allergen_5',
    name: 'Lupin',
    imageName: 'lupin.png'
  },
  {
    id: 'kemenu_allergen_6',
    name: 'Milk',
    imageName: 'milk.png'
  },
  {
    id: 'kemenu_allergen_7',
    name: 'Molluscs',
    imageName: 'molluscs.png'
  },
  {
    id: 'kemenu_allergen_8',
    name: 'Mustard',
    imageName: 'mustard.png'
  },
  {
    id: 'kemenu_allergen_9',
    name: 'Peanut',
    imageName: 'peanut.png'
  },
  {
    id: 'kemenu_allergen_10',
    name: 'Sesame',
    imageName: 'sesame.png'
  },
  {
    id: 'kemenu_allergen_11',
    name: 'Soya',
    imageName: 'soya.png'
  },
  {
    id: 'kemenu_allergen_12',
    name: 'Suphurdioxide',
    imageName: 'suphurdioxide.png'
  },
  {
    id: 'kemenu_allergen_13',
    name: 'Treenut',
    imageName: 'treenut.png'
  },
  {
    id: 'kemenu_allergen_14',
    name: 'Wheat',
    imageName: 'wheat.png'
  }
];

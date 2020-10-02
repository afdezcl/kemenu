import { ShowMenu } from '@models/menu/showMenu.model';

export const Demo: ShowMenu[] =
  [
    {
      businessName: 'Bar Demo',
      name: 'Carta principal',
      currency: 'EUR',
      sections: [{
        name: 'Para picar',
        dishes: [{
          name: 'Nachos con queso',
          description: 'Nachos con queso cheddar, jalapeños y aceitunas negras',
          price: 7.50,
          formattedPrice: '7.50€',
          allergens: [
            {
              id: 'kemenu_allergen_1',
              name: 'Celery',
              imageName: 'celery.png'
            },
            {
              id: 'kemenu_allergen_3',
              name: 'Eggs',
              imageName: 'eggs.png'
            },
            {
              id: 'kemenu_allergen_10',
              name: 'Sesame',
              imageName: 'sesame.png'
            }
          ],
          imageUrl: 'https://res.cloudinary.com/dcb7j4q7p/image/upload/v1591770336/Screenshot_from_2020-06-10_08-25-14_g3ad8b.png',
          available: true
        }, {
          name: 'Croquetas',
          description: 'Croquetas de jamón ibérico',
          price: 4.50,
          formattedPrice: '4.50€',
          allergens: [
            {
              id: 'kemenu_allergen_3',
              name: 'Eggs',
              imageName: 'eggs.png'
            },
            {
              id: 'kemenu_allergen_9',
              name: 'Peanut',
              imageName: 'peanut.png'
            },
            {
              id: 'kemenu_allergen_14',
              name: 'Wheat',
              imageName: 'wheat.png'
            }
          ],
          imageUrl: 'https://res.cloudinary.com/dcb7j4q7p/image/upload/v1591770401/Screenshot_from_2020-06-10_08-26-30_z3vtyp.png',
          available: true
        }]
      }, {
        name: 'Carnes',
        dishes: [{
          name: 'Solomillo a la brasa',
          description: 'Con mojo picón y sal de escamas',
          price: 10.20,
          formattedPrice: '10.20€',
          allergens: [
            {
              id: 'kemenu_allergen_12',
              name: 'Suphurdioxide',
              imageName: 'suphurdioxide.png'
            },
          ],
          available: true
        }]
      }, {
        name: 'Hamburguesas',
        dishes: [{
          name: 'César',
          description: 'Hamburguesa con queso, tomate y lechuga',
          price: 7.50,
          formattedPrice: '7.50€',
          allergens: [],
          available: true
        }, {
          name: 'Simple',
          description: 'Hamburguesa con mayonesa simple',
          price: 7.80,
          formattedPrice: '7.80€',
          allergens: [
            {
              id: 'kemenu_allergen_11',
              name: 'Soya',
              imageName: 'soya.png'
            },
            {
              id: 'kemenu_allergen_5',
              name: 'Lupin',
              imageName: 'lupin.png'
            }
          ],
          available: true
        }, {
          name: 'Big-Burguer',
          description: 'Hamburguesa doble con beacon',
          price: 9.50,
          formattedPrice: '9.50€',
          allergens: [],
          available: true
        }]
      }, {
        name: 'Bebidas',
        dishes: [{
          name: 'Coca-Cola',
          description: '',
          price: 1.80,
          formattedPrice: '1.80€',
          allergens: [],
          available: true
        }, {
          name: 'Cerveza - Caña',
          description: '',
          price: 1.20,
          formattedPrice: '1.20€',
          allergens: [],
          available: true
        }, {
          name: 'Agua',
          description: '',
          price: 1.20,
          formattedPrice: '1.20€',
          allergens: [],
          available: true
        }]
      }, {
        name: 'Postres',
        dishes: [{
          name: 'Tarta de queso',
          description: 'Tarta de queso casera',
          price: 3.50,
          formattedPrice: '3.50€',
          allergens: [],
          available: true
        }, {
          name: 'Natillas',
          description: 'Natillas caseras de la casa',
          price: 4.50,
          formattedPrice: '4.50€',
          allergens: [
            {
              id: 'kemenu_allergen_6',
              name: 'Milk',
              imageName: 'milk.png'
            },
            {
              id: 'kemenu_allergen_3',
              name: 'Eggs',
              imageName: 'eggs.png'
            },
            {
              id: 'kemenu_allergen_10',
              name: 'Sesame',
              imageName: 'sesame.png'
            }
          ],
          available: true
        }, {
          name: 'Variado de tartas',
          description: 'Un variado de tartas caseras de la casa',
          price: 7,
          formattedPrice: '7.00€',
          allergens: [],
          available: true
        }]
      }],
      shortUrlId: '',
      imageUrl: 'asd'
    },


    {
      businessName: 'Bar Demo',
      name: 'Menú del dia',
      currency: 'EUR',
      sections: [ {
        name: 'Hamburguesas',
        dishes: [{
          name: 'César',
          description: 'Hamburguesa con queso, tomate y lechuga',
          price: 7.50,
          formattedPrice: '7.50€',
          allergens: [],
          available: true
        }, {
          name: 'Simple',
          description: 'Hamburguesa con mayonesa simple',
          price: 7.80,
          formattedPrice: '7.80€',
          allergens: [
            {
              id: 'kemenu_allergen_11',
              name: 'Soya',
              imageName: 'soya.png'
            },
            {
              id: 'kemenu_allergen_5',
              name: 'Lupin',
              imageName: 'lupin.png'
            }
          ],
          available: true
        }, {
          name: 'Big-Burguer',
          description: 'Hamburguesa doble con beacon',
          price: 9.50,
          formattedPrice: '9.50€',
          allergens: [],
          available: true
        }]
      }, {
        name: 'Carnes',
        dishes: [{
          name: 'Solomillo a la brasa',
          description: 'Con mojo picón y sal de escamas',
          price: 10.20,
          formattedPrice: '10.20€',
          allergens: [
            {
              id: 'kemenu_allergen_12',
              name: 'Suphurdioxide',
              imageName: 'suphurdioxide.png'
            },
          ],
          available: true
        }]
      },{
        name: 'Bebidas',
        dishes: [{
          name: 'Coca-Cola',
          description: '',
          price: 1.80,
          formattedPrice: '1.80€',
          allergens: [],
          available: true
        }, {
          name: 'Cerveza - Caña',
          description: '',
          price: 1.20,
          formattedPrice: '1.20€',
          allergens: [],
          available: true
        }, {
          name: 'Agua',
          description: '',
          price: 1.20,
          formattedPrice: '1.20€',
          allergens: [],
          available: true
        }]
      }, {
        name: 'Postres',
        dishes: [{
          name: 'Tarta de queso',
          description: 'Tarta de queso casera',
          price: 3.50,
          formattedPrice: '3.50€',
          allergens: [],
          available: true
        }, {
          name: 'Natillas',
          description: 'Natillas caseras de la casa',
          price: 4.50,
          formattedPrice: '4.50€',
          allergens: [
            {
              id: 'kemenu_allergen_6',
              name: 'Milk',
              imageName: 'milk.png'
            },
            {
              id: 'kemenu_allergen_3',
              name: 'Eggs',
              imageName: 'eggs.png'
            },
            {
              id: 'kemenu_allergen_10',
              name: 'Sesame',
              imageName: 'sesame.png'
            }
          ],
          available: true
        }, {
          name: 'Variado de tartas',
          description: 'Un variado de tartas caseras de la casa',
          price: 7,
          formattedPrice: '7.00€',
          allergens: [],
          available: true
        }]
      }],
      shortUrlId: '',
      imageUrl: 'asd'
    }
  ];

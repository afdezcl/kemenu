import {ShowMenu} from '@models/menu/showMenu.model';

export const Demo: ShowMenu = {
  businessName: 'Bar Demo',
  sections: [{
    name: 'Para picar',
    dishes: [{
      name: 'Nachos con queso',
      description: 'Nachos con queso cheddar, jalapeños y aceitunas negras',
      price: 7.50,
      allergens: []
    }, {
      name: 'Croquetas',
      description: 'Croquetas de jamón ibérico',
      price: 4.50,
      allergens: []
    }]
  }, {
    name: 'Carnes',
    dishes: [{
      name: 'Solomillo a la brasa',
      description: 'Con mojo picón y sal de escamas',
      price: 10.20,
      allergens: []
    }]
  }, {
    name: 'Hamburguesas',
    dishes: [{
      name: 'César',
      description: 'Hamburguesa con queso, tomate y lechuga',
      price: 7.50,
      allergens: []
    }, {
      name: 'Simple',
      description: 'Hamburguesa con mayonesa simple',
      price: 7.80,
      allergens: []
    }, {
      name: 'Big-Burguer',
      description: 'Hamburguesa doble con beacon',
      price: 9.50,
      allergens: []
    }]
  }, {
    name: 'Bebidas',
    dishes: [{
      name: 'Coca-Cola',
      description: '',
      price: 1.80,
      allergens: []
    }, {
      name: 'Cerveza - Caña',
      description: '',
      price: 1.20,
      allergens: []
    }, {
      name: 'Agua',
      description: '',
      price: 1.20,
      allergens: []
    }]
  }, {
    name: 'Postres',
    dishes: [{
      name: 'Tarta de queso',
      description: 'Tarta de queso casera',
      price: 3.50,
      allergens: []
    }, {
      name: 'Natillas',
      description: 'Natillas caseras de la casa',
      price: 4.50,
      allergens: []
    }, {
      name: 'Variado de tartas',
      description: 'Un variado de tartas caseras de la casa',
      price: 7,
      allergens: []
    }]
  }],
  shortUrlId: ''
};

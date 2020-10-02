import { Component, OnInit } from '@angular/core';
import { Demo } from '@models/demo-mock/demo.mock';
import { ShowMenu } from '@models/menu/showMenu.model';
import { Router } from '@angular/router';
import { MenuService } from '@services/menu/menu.service';
import { Section } from '@models/menu/section.model';
import { Dish } from '@models/menu/dish.model';
import { AllAllergens, Allergen } from '@models/menu/allergen.model';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss']
})
export class CustomerComponent implements OnInit {

  public allergens: Allergen[] = AllAllergens;

  public menusSaved: ShowMenu[];
  cookieBASE64: string;
  shortUrlId: string;
  imageUrl: SafeResourceUrl;

  constructor(
    private router: Router,
    private menuService: MenuService,
    private sanitizer: DomSanitizer
  ) {
  }

  ngOnInit() {
    if (!Object.is(this.router.routerState.snapshot.url, '/demo')) {
      this.getDataToBuildMenu();
      this.menuService.getMenuById(this.shortUrlId)
        .subscribe((menusSaved: ShowMenu[]) => {
          const showMenu = this.matchAllergens(menusSaved);
          return showMenu;
        });
    } else {
      this.menusSaved = Demo;
    }
  }

  getDataToBuildMenu() {
    this.cookieBASE64 = localStorage.getItem('COOKIE-SHOW-MENU');
    const shortUrlId = atob(this.cookieBASE64);
    this.shortUrlId = shortUrlId;
    localStorage.setItem('shortUrlId', this.shortUrlId);
  }

  matchAllergens(menusSaved: ShowMenu[]): ShowMenu[] {
    menusSaved.map((menu: ShowMenu) => {
      menu.sections.map((section: Section) => {
        section.dishes.map((dish: Dish) => {
          dish.allergens.map((allergen: Allergen) => {
            allergen.imageName = this.allergens.find(item => item.id === allergen.id).imageName;
          });
        });
      });
    })
    return menusSaved;
  }

/*   loadImagesWhenOpen(isOpenEvent: boolean, sectionIdx: number) {
    if (isOpenEvent) {
      this.menu.subscribe(showMenu => {
        for (let i = 0; i < showMenu.sections.length; i++) {
          if (i === sectionIdx) {
            for (let j = 0; j < showMenu.sections[i].dishes.length; j++) {
              const img = document.querySelector<HTMLImageElement>('#dish-img-' + i + '-' + j);
              if (img && !img.src) {
                img.src = img.dataset.urlsrc;
              }
            }
          }
        }
      });
    }
  } */
}

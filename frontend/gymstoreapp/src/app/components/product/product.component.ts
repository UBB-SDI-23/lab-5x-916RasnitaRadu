import { Component } from '@angular/core';
import { Product } from 'src/app/model/product';
import { ProductService } from 'src/app/services/product.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent {
  public products : Product[];
  public editProduct : Product;
  public deleteProduct : Product;
  public stats :{id : number, name: string, likes  : number }[];
  public currentPage : number = 1;
  public totalPages : number = 0;


  constructor(private productService : ProductService, private router : Router, private activatedRoute : ActivatedRoute) { }

  ngOnInit() : void {
    
  }

}

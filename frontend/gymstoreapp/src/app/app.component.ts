import { Component, OnInit } from '@angular/core';
import { Product } from './model/product';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ProductService } from './services/product.service';
import { NgForm } from '@angular/forms';
import { Entry } from './model/Entry';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  public products : Product[];
  public editProduct : Product;
  public deleteProduct : Product;
  public stats :{id : number, name: string, likes  : number }[];
  public currentPage : number = 1;
  public totalPages : number = 0;

  constructor(private productService : ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  public getProducts() : void {
    this.productService.getAllProducts(this.currentPage, 10).subscribe(
      response => {
        this.products = response.content;
        this.totalPages = response.totalPages;
      },

    );
  }

  public onOpenModal(product: Product, mode : string) : void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addProductModal');
    }
    if (mode === 'delete') {
      this.deleteProduct = product;
      button.setAttribute('data-target', '#deleteProductModal');
    }
    if (mode === 'edit') {
      this.editProduct = product;
      button.setAttribute('data-target', '#updateProductModal');
    }
    if (mode === 'stats')
    {
      button.setAttribute('data-target', '#statisticalReportModal');
    }
    container.appendChild(button);
    button.click();
  }

  public onAddProduct(addForm : NgForm) : void {
    document.getElementById('add-product-form').click();
    this.productService.addProduct(addForm.value).subscribe(
      (response : Product) => {
        console.log(response);
        this.getProducts();
        addForm.reset();
      },

      );
  }

  public onUpdateProduct(product : Product) : void {
    this.productService.updateProduct(product).subscribe(
      (response : Product) => {
        console.log(response);
        this.getProducts();
      }
    );
  }

  public onDeleteProduct(productId : number) : void {
    this.productService.deleteProduct(productId).subscribe(
      (response : void) => {
        console.log(response);
        this.getProducts();
      }
    );
  }

  public filterProducts(price : number) : void {
    this.productService.filterProductsByPrice(price).subscribe(
      (response : Product[]) => {
        this.products = response;
        if (response.length === 0 || !price) {
          this.getProducts();
        }
      }
    );
  }

  public getStatisticalRep() : void {
    this.productService.getStatisticalReportProduct().subscribe(
      (response) => {
        this.stats = response.sort((a, b) => a.likes - b.likes);
        console.log('hasdas', this.stats)
      }
    );
  }

  public sortProductsByPrice() : void {
    this.products = this.products.sort((a, b) => a.price - b.price);
  }

  public changePage(pageNumber : number) : void {
    pageNumber = Math.max(pageNumber, 0);
    pageNumber = Math.min(pageNumber, this.totalPages - 1);
    this.currentPage = pageNumber;
    this.getProducts();
  }

}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Product } from './product';
import { Entry } from './Entry';

@Injectable({ providedIn: 'root'})
export class ProductService {
  private apiServerUrl = 'http://localhost:80';

  constructor(private http : HttpClient) { }

  public getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiServerUrl}/product/all`);
  }

  public getProduct(productId : number): Observable<Product> {
    return this.http.get<Product>(`${this.apiServerUrl}/product/${productId}`);
  }

  public addProduct(product : Product): Observable<Product> {
    return this.http.post<Product>(`${this.apiServerUrl}/product/`, product);
  }

  public updateProduct(product : Product ): Observable<Product> {
    return this.http.put<Product>(`${this.apiServerUrl}/product/`, product);
  }

  public deleteProduct(productId : number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/product/${productId}`);
  }

  public filterProductsByPrice(price : number) : Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiServerUrl}/product/filter/${price}`);
  }

  public getStatisticalReportProduct() : Observable<{id : number, name: string, likes  : number }[]> {
    return this.http.get<{id : number, name: string, likes  : number }[]>(`${this.apiServerUrl}/review/statProd`);
    
  }

}

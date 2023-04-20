import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Product } from '../model/product';
import { Entry } from '../model/Entry';
import { GenericPageDTO } from '../model/genericPageDTO';

@Injectable({ providedIn: 'root'})
export class ProductService {
  //private apiServerUrl = 'http://104.197.134.216:80';
  private apiServerUrl = 'http://localhost:80'

  constructor(private http : HttpClient) { }

  public getAllProducts(pageNumber: number, pageSize : number): Observable<GenericPageDTO<Product>> {
    return this.http.get<GenericPageDTO<Product>>(`${this.apiServerUrl}/product/all` + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
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

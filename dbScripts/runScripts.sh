#!/bin/bash

psql -h localhost -d GymStoreAPI -U postgres -p 5432 -f DropConstraints.sql
psql -h localhost -d GymStoreAPI -U postgres -p 5432 -f InsertProduct.sql
psql -h localhost -d GymStoreAPI -U postgres -p 5432 -f InsertCustomer.sql
psql -h localhost -d GymStoreAPI -U postgres -p 5432 -f InsertReview.sql
psql -h localhost -d GymStoreAPI -U postgres -p 5432 -f AddConstraints.sql

--ALTER TABLE "product" ADD CONSTRAINT "product_pkey" PRIMARY KEY("product_id");
-- ALTER TABLE "customer" ADD CONSTRAINT "customer_pkey" PRIMARY KEY("customer_id");
ALTER TABLE "review" ADD CONSTRAINT "review_pkey" PRIMARY KEY("id");
ALTER TABLE "review" ADD CONSTRAINT "fkgce54o0p6uugoc2tev4awewly" FOREIGN KEY("customer_id") REFERENCES "customer"("customer_id");
ALTER TABLE "review" ADD CONSTRAINT "fkiyof1sindb9qiqr9o8npj8klt" FOREIGN KEY("product_id") REFERENCES "product"("product_id");
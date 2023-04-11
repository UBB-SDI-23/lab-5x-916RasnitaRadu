from faker import Faker
from constants import *
from random import randint
from utilities import *
from model.Review import *
from model.Product import *
from model.Customer import *
import random
import datetime
import uuid
from colorama import Fore
import itertools

fake = Faker()
customer_ids = {}
product_ids = {}
review_ids = {}


def drop_constraints_from_all_tables():
    file = open("DropConstraints.sql", "w")
    print("1. START DROPPING CONSTRAINTS")
    file.write("ALTER TABLE \"Product\" DROP CONSTRAINT \"product_pkey\" CASCADE;\n")
    file.write("ALTER TABLE \"Customer\" DROP CONSTRAINT \"customer_pkey\" CASCADE;\n ")

    print("2. STOP DROPPING CONSTRAINTS")
    file.close()


def add_constraints_to_all_tables():
    file = open("AddConstraints.sql", "w")

    print("3. START ADDING CONSTRAINTS")

    file.write("ALTER TABLE \"product\" ADD CONSTRAINT \"product_pkey\" PRIMARY KEY(\"customer_id\");\n")
    file.write("ALTER TABLE \"customer\" ADD CONSTRAINT \"customer_pkey\" PRIMARY KEY(\"product_id\");\n")

    print("4. STOP ADDING CONSTRAINTS")
    file.close()


def insert_into_product():
    global product_ids
    file = open("InsertProduct.sql", "w")
    file.write("DELETE FROM \"product\";\n")
    batches = ""
    counter = itertools.count(start=1)
    print("STARTING INSERTING INTO CUSTOMER AT TIME: ", Fore.YELLOW + str(datetime.datetime.now()))
    for index in range(NR_GENERATIONS):
        pid = next(counter)

        product_ids[pid] = 1
        name = PRODUCTS[randint(0, len(PRODUCTS) - 1)]
        price = 1.2 * randint(20, 260)
        description = fake.paragraph(nb_sentences=3)
        color = fake.color_name()
        category = CATEGORIES[randint(0, len(CATEGORIES) - 1)]
        product = Product(pid, name, price, description, color, category)
        batches += str(product) + ","

        if (index + 1) % 10 == 0:
            file.write(
                str(f"INSERT INTO \"product\"(\"product_id\",\"name\",\"price\",\"description\",\"color\",\"category\") VALUES {batches[:-1]};\n"))
            batches = ""
    file.close()
    print(Fore.WHITE + "STOP INSERT IN PRODUCTS AT: ", Fore.YELLOW + str(datetime.datetime.now()), Fore.GREEN + "\u2713")


def insert_into_customer():
    global customer_ids
    file = open("InsertCustomer.sql", "w")
    file.write("DELETE FROM \"product\";\n")
    counter = itertools.count(start=1)
    batches = ""
    print("STARTING INSERTING INTO CUSTOMER AT TIME: ", Fore.YELLOW + str(datetime.datetime.now()))
    for index in range(NR_GENERATIONS):
        cid = next(counter)
        customer_ids[cid] = 1
        firstName = fake.first_name()
        lastName = fake.last_name()
        email = f"{firstName}.{lastName}@{fake.domain_name()}"
        address = fake.address()
        age = randint(18, 75)
        customer = Customer(cid, firstName, lastName, email, address, age)
        batches += str(customer) + ","
        if (index + 1) % 10 == 0:
            file.write(
                str(f"INSERT INTO \"customer\"(\"customer_id\",\"first_name\",\"last_name\",\"email\",\"address\",\"age\") VALUES {batches[:-1]};\n"))
            batches = ""
    file.close()
    print(Fore.WHITE + "STOP INSERT IN CUSTOMERS AT: ", Fore.YELLOW + str(datetime.datetime.now()), Fore.GREEN + "\u2713")

def insert_into_review():
    global customer_ids
    global review_ids
    global product_ids
    batches = ""
    customer_id_list = list(customer_ids.keys())
    product_id_list = list(product_ids.keys())
    file = open("InsertReview.sql", "w")

    def get_random_date():
        created_at = datetime.date(2022,1,1)
        created_at += datetime.timedelta(days=randint(0,364))
        return created_at

    print("STARTING INSERTING INTO REVIEWS AT TIME: ", Fore.YELLOW + str(datetime.datetime.now()))

    file.write("DELETE FROM \"review\";\n")
    file.write("")
    counter = itertools.count(start=1)
    for index in range(NR_GENERATIONS * 10):
        rid =next(counter)
        review_ids[rid] = 1
        customer_id = customer_id_list[randint(0, len(customer_id_list) -1)]
        product_id = product_id_list[randint(0, len(product_id_list) - 1)]
        reviewText = fake.paragraph(nb_sentences=3)
        created_at = str(get_random_date())
        numberLikes = randint(0, 1000)
        review = Review(rid,customer_id,product_id,reviewText,str(created_at),numberLikes)
        batches += str(review) + ","

        if (index + 1) % 10000 == 0:
            file.write(
                str(f"INSERT INTO \"review\"(\"id\",\"customer_id\",\"product_id\",\"review_text\",\"created_at\",\"number_likes\") VALUES {batches[:-1]};\n"))
            batches = ""

    print(Fore.WHITE + "STOP INSERT IN RENTS AT: ", Fore.YELLOW + str(datetime.datetime.now()), Fore.GREEN + "\u2713")

    file.close()


if __name__ == "__main__":
   # drop_constraints_from_all_tables()
    insert_into_customer()
    insert_into_product()
    insert_into_review()
    #add_constraints_to_all_tables()
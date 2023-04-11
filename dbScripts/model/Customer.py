from utilities import *


class Customer:
    def __init__(self, id, firstName, lastName, email, address, age):
        self._id = id
        self._firstName = firstName
        self._lastName = lastName
        self._email = email
        self._address = address
        self._age = age

    def __str__(self) -> str:
        return f"({self._id}," + \
               f"{get_string_in_single_quotes(self._firstName)}," + \
               f"{get_string_in_single_quotes(self._lastName)}," + \
               f"{get_string_in_single_quotes(self._email)}," + \
               f"{get_string_in_single_quotes(self._address)}," + \
               f"{self._age})"
from utilities import *


class Product:
    def __init__(self, id, name, price, description, color, category):
        self._id = id
        self._name = name
        self._price = price
        self._description = description
        self._color = color
        self._category = category

    def __str__(self):
        return f"({self._id}," + \
               f"{get_string_in_single_quotes(self._name)}," + \
               f"{self._price}," + \
               f"{get_string_in_single_quotes(self._description)}," + \
               f"{get_string_in_single_quotes(self._color)}," + \
               f"{get_string_in_single_quotes(self._category)})"

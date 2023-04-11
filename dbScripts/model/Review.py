from utilities import *


class Review:
    def __init__(self, id, customer_id, product_id, reviewText, createdAt, numberLikes):
        self._id = id
        self._customer_id = customer_id
        self._product_id = product_id
        self._reviewText = reviewText
        self._created_at = createdAt
        self._numberLikes = numberLikes

    def __str__(self) -> str:
        return f"({self._id}," + \
               f"{self._customer_id}," + \
               f"{self._product_id}," + \
               f"{get_string_in_single_quotes(self._reviewText)}," + \
               f"{get_string_in_single_quotes(self._created_at)}," + \
               f"{self._numberLikes})"

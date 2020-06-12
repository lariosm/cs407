from flask import Flask, jsonify, abort, url_for
from datetime import datetime
from mongoengine import connect, StringField, IntField, Document, \
    DateTimeField, queryset_manager, ValidationError
import os

app = Flask(__name__)

# MongoDB login credentials
mongo_host = os.getenv('DB_HOST')
mongo_db = os.getenv('DB_NAME')
mongo_user = os.getenv('DB_USER')
mongo_password = os.getenv('DB_PASSWORD')


# MongoDB connection settings will go here
connect(host=mongo_host,
        db=mongo_db,
        username=mongo_user,
        password=mongo_password)


class Listing(Document):
    user_id = StringField(required=False, max_length=64)
    timestamp = DateTimeField(default=datetime.utcnow)
    listing_title = StringField(required=True, max_length=128)
    description = StringField(required=True, max_length=256)
    asking_price = IntField(required=True)
    city = StringField(required=True, max_length=64)
    state = StringField(required=True, max_length=32)

    @queryset_manager
    def objects(self, queryset):
        # By default, it orders queries by timestamp (most recent)
        return queryset.order_by('timestamp')


def listing_helper(listing_object):
    # Turns document object to dictionary
    listing = listing_object.to_mongo().to_dict()
    listing["id"] = str(listing["_id"])  # Store Mongo-generated id to "id"
    listing.pop("_id")  # Remove to avoid raising TypeError exception
    # listing["location"] = url_for("activity", str_id=listing["id"])

    return listing


# Returns all posted listings
@app.route('/api/listings', methods=["GET"])
def listings():
    listings = Listing.objects  # Returns first 10 entries
    listing_list = [listing_helper(listing) for listing in listings]
    return jsonify({'listings': listing_list})


# Returns a single listing
@app.route('/api/listings/<string:str_id>', methods=["GET"])
def listing(str_id):
    try:
        # Queries database from string input and save it
        log_id = Listing.objects(id=str_id)
        if log_id.first() is None:  # Does the listing entry exist?
            abort(404)
        return jsonify(listing_helper(log_id.get()))
    except ValidationError:
        abort(400, f'\'{str_id}\' is not a valid ObjectId. It must be a'
              ' 12-byte input or a 24-character hex string.')


# For testing only...
'''
a = Listing(
    username='jean',
    listing_title='Home Theater System',
    description='5.1 sound system plus subwoofer. Comes with accessories',
    asking_price=1000000000,
    city='Vancouver',
    state='WA'
)

a.save()
'''

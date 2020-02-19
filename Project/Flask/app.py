from flask import Flask, jsonify, abort, request, url_for
from datetime import datetime
from mongoengine import connect, StringField, IntField, Document, \
    DateTimeField, queryset_manager, ValidationError
import os

app = Flask(__name__)

# MongoDB login credentials will go here

# MongoDB connection settings will go here


class Listing(Document):
    listing_id = IntField(required=True)
    username = StringField(required=True, max_length=64)
    timestamp = DateTimeField(default=datetime.utcnow)
    asking_price = IntField(required=True)
    city = StringField(required=True, max_length=64)
    state = StringField(required=True, max_length=32)

    @queryset_manager
    def objects(self, queryset):
        # By default, it orders queries by timestamp (most recent)
        return queryset.order_by('-timestamp')
